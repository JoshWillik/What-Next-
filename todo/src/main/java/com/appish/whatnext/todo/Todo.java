package com.appish.whatnext.todo;

public class Todo {
    private int _id;
    private int _position;
    private String _text;
    private int _listID;

    public int getListID(){
        return this._listID;
    }

    public void setListID(int listID){
        this._listID = listID;
    }

    public int getID(){
        return this._id;
    }

    public void setID( int id ){
        this._id = id;
    }

    public String getText(){
        return this._text;
    }

    public void setText(String text){
        this._text = text;
    }

    public int getPosition(){
        return this._position;
    }

    public void setPosition( int position ){
        this._position = position;
    }

    public Todo( int id ){
        this._id = id;
    }

    public Todo(){
        this._id = 0;
    }

}
