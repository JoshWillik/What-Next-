package com.appish.whatnext.todo;

import java.util.ArrayList;

/**
 * Created by josh on 5/3/14.
 */
public interface TodoManager {

    public TodoList fetchList( int listID );

    public ArrayList<TodoList> fetchLists();

    public Todo fetchTodo( int todoId ) throws Exception;

    public ArrayList<Todo> fetchTodos( TodoList list );

    public ArrayList<String> fetchTodosAsStrings( TodoList list );

    public void saveTodo( Todo todo );

    public void removeTodo( int todoId );

    public void saveList( TodoList todoList);
}
