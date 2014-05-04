package com.appish.whatnext.todo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private final String TODO_FILE = "todofile.txt";
    private final String FILE_ERROR = "FILE ERROR";

    private TodoManager manager = (TodoManager) new DatabaseTodoManager(this);

    private String[] todos;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return true;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.updateNavigationDrawer();
    }

    protected String[] readTodos(){
        String[] todos = new String[4];
        for( int i = 0; i < 4; i++ ){
            todos[i] = "Foobar";
        }
        return todos;
    }

    public boolean addTodo( MenuItem item ){
        Todo todo = new Todo();
        todo.setPosition( 1 );
        todo.setText( "Hello there, I am a new todo" );
        todo.setListID( 1 );
        this.manager.saveTodo( todo );
        this.updateNavigationDrawer();
        return true;
    }

    public boolean clearTodos( MenuItem item ){
        ArrayList<Todo> todos = this.manager.fetchTodos( new TodoList( 1 ) );
        for( Todo todo: todos ){
            this.manager.removeTodo( todo.getID() );
        }
        this.updateNavigationDrawer();
        return true;
    }

    private void pullTodos(){
        this.todos = this.manager.fetchTodosAsStrings( new TodoList( 1 ) ).toArray( new String[0] );
    }

    private void updateNavigationDrawer(){
        this.pullTodos();
        ListView drawer = (ListView) findViewById( R.id.left_drawer );
        drawer.setAdapter( new ArrayAdapter<String>(
                this, R.layout.drawer_todo_item, this.todos
        ));
    }
}
