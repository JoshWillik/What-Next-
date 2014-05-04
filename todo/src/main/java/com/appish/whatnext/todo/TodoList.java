package com.appish.whatnext.todo;

/**
 * Created by josh on 5/2/14.
 */
public class TodoList {
    private int _id;
    private String _name;
    private int _position;

    public void setID( int id ){
        this._id = id;
    }
    public int getID(){
        return this._id;
    }

    public void setName( String name ){
        this._name = name;
    }
    public String getName(){
        return this._name;
    }

    public void setPosition( int position ){
        this._position = position;
    }

    public int getPosition(){
        return this._position;
    }

    TodoList(){
        this._id = 0;
    }

    TodoList( int id ){
        this._id = id;
    }
}
