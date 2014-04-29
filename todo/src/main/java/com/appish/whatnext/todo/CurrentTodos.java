package com.appish.whatnext.todo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CurrentTodos extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_todos);
        TextView header = (TextView) findViewById( R.id.todoTitle );
        header.setText( "Brush the cat" );

        TextView body = (TextView) findViewById( R.id.todoBody );
        body.setText( "I can have fun!" );
    }
}
