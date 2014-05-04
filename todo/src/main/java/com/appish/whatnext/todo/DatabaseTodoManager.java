package com.appish.whatnext.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by josh on 5/2/14.
 */
public class DatabaseTodoManager extends SQLiteOpenHelper implements TodoManager {

    private SQLiteDatabase database;

    public static final int DB_VERSION = 2;

    public static final String TABLE_TODOS = "Todos";
    public static final String TODO_ID = "id";
    public static final String TODO_TEXT = "body";
    public static final String TODO_POSITION = "position";
    public static final String TODO_LIST_FK = "list";

    public static final String TABLE_LISTS = "Lists";
    public static final String LIST_ID = "id";
    public static final String LIST_NAME = "name";
    public static final String LIST_POSITION = "position";

    @Override
    public void onCreate( SQLiteDatabase db ){
        String CREATE_TODO_TABLE = "CREATE TABLE " +
                this.TABLE_TODOS + "("
                + this.TODO_ID + " INTEGER PRIMARY KEY,"
                + this.TODO_TEXT + " TEXT,"
                + this.TODO_POSITION + " INTEGER,"
                + this.TODO_LIST_FK + " INTEGER"
                + ")";
        String CREATE_LIST_TABLE = "CREATE TABLE " +
                this.TABLE_LISTS + "("
                + this.LIST_ID + " INTEGER PRIMARY KEY,"
                + this.LIST_NAME + " TEXT,"
                + this.LIST_POSITION + " INTEGER"
                + ")";
        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ){
        if( newVersion > oldVersion ){
            Log.d( "SQL", "Upgrading database" );
            db.execSQL("ALTER TABLE " + this.TABLE_TODOS + " ADD COLUMN " + this.TODO_LIST_FK
                    + " INTEGER");
        } else {
            Log.d( "SQL", "Database is un-upgraded" );
        }
    }

    public DatabaseTodoManager(Context context){
        //DB_VERSION instead of this.DB_VERSION because android studio
        super( context, "WhatNext", null, DB_VERSION );
    }

    @Override
    public TodoList fetchList(int listID) {
        return null;
    }

    @Override
    public ArrayList<TodoList> fetchLists() {
        return null;
    }

    @Override
    public Todo fetchTodo(int todoId) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select "
            + this.TODO_ID + ", "
            + this.TODO_POSITION + ", "
            + this.TODO_TEXT
            + " FROM " + this.TABLE_TODOS
            + " WHERE " + this.TODO_ID + " = "+ todoId;
        Cursor cursor = db.rawQuery( query, null );
        Todo todo;
        if( cursor.moveToFirst() ){
            todo = this.loadTodoFromRow( cursor );
        } else {
            throw new Exception("Could not load todos");
        }
        return todo;
    }

    @Override
    public ArrayList<Todo> fetchTodos( TodoList list ) {
        int listID = list.getID();
        ArrayList<Todo> todos = new ArrayList<Todo>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "
                + this.TODO_ID + ", "
                + this.TODO_POSITION + ", "
                + this.TODO_TEXT
                + " FROM " + this.TABLE_TODOS
                + " WHERE " + this.TODO_LIST_FK + " = " + listID;
        Cursor cursor = db.rawQuery( query, null );
        if( !cursor.moveToFirst() ) return todos;

        do {
            todos.add( this.loadTodoFromRow( cursor ) );
        } while( cursor.moveToNext() );
        return todos;
    }

    @Override
    public ArrayList<String> fetchTodosAsStrings( TodoList list ){
        ArrayList<String> strings = new ArrayList<String>();
        for( Todo todo: this.fetchTodos( list ) ){
            strings.add( todo.getText() );
        }
        return strings;
    }

    @Override
    public void saveTodo(Todo todo) {
        if( todo.getID() == 0 ){
            this.createTodo(todo);
        } else {
            this.writeTodo(todo);
        }
    }

    @Override
    public void removeTodo(int todoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id = " + todoId;
        db.delete( this.TABLE_TODOS, whereClause, new String[0] );
        db.close();
    }

    @Override
    public void saveList(TodoList todoList) {
        if( todoList.getID() == 0 ){
            this.createList(todoList);
        } else {
            this.writeList(todoList);
        }
    }

    public TodoList createList( TodoList todoList ){
        ContentValues newTodo = new ContentValues();
        newTodo.put(this.LIST_POSITION, todoList.getPosition());
        newTodo.put(this.LIST_NAME, todoList.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        int newListID = (int) db.insert( this.TABLE_LISTS, null, newTodo );
        todoList.setID(newListID);
        db.close();
        return todoList;
    }

    public TodoList writeList( TodoList todoList ){
        ContentValues changedList = new ContentValues();
        changedList.put(this.LIST_NAME, todoList.getName());
        changedList.put(this.LIST_POSITION, todoList.getPosition());

        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = this.LIST_ID + " = ? ";
        String[] boundConditions = new String[1];
        boundConditions[0] = String.valueOf( todoList.getID() );
        db.update( this.TABLE_LISTS, changedList, whereClause, boundConditions );
        db.close();
        return todoList;
    }

    //Save the state of an existing to-do into the database
    private Todo writeTodo( Todo todo ){
        ContentValues changedTodo = new ContentValues();
        changedTodo.put( this.TODO_TEXT, todo.getText() );
        changedTodo.put( this.TODO_POSITION, todo.getPosition() );

        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = this.TODO_ID + " = ? ";
        String[] boundConditions = new String[1];
        boundConditions[0] = String.valueOf( todo.getID() );
        db.update( this.TABLE_TODOS, changedTodo, whereClause, boundConditions );
        db.close();
        return todo;
    }

    //write a new to-do into the database
    private Todo createTodo( Todo todo ){
        ContentValues newTodo = new ContentValues();
        newTodo.put( this.TODO_POSITION, todo.getPosition() );
        newTodo.put( this.TODO_TEXT, todo.getText() );
        newTodo.put( this.TODO_LIST_FK, todo.getListID() );

        SQLiteDatabase db = this.getWritableDatabase();
        int newTodoId = (int) db.insert( TABLE_TODOS, null, newTodo );
        db.close();
        todo.setID( newTodoId );
        return todo;
    }

    private Todo loadTodoFromRow( Cursor cursor ){
        Todo todo = new Todo();
        todo.setID( Integer.parseInt( cursor.getString( 0 ) ) );
        todo.setPosition( Integer.parseInt( cursor.getString( 1 ) ) );
        todo.setText( cursor.getString( 2 ) );
        return todo;
    }
}

