package com.appish.whatnext.todo;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by josh on 5/3/14.
 */
public abstract class FilesystemTodoManager implements TodoManager {

    private FileOutputStream output;

    public static final String TODO_FILE = "todolistv1";

    public FilesystemTodoManager() throws Exception {
        throw new Exception("This not implimented");
    }

    @Override
    public void saveTodo(Todo todo) {

    }

    @Override
    public void saveList(TodoList todoList) {

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
    public Todo fetchTodo(int todoId) {
        return null;
    }

    public ArrayList<Todo> fetchTodos() {
        return null;
    }

    public ArrayList<String> fetchTodosAsStrings() {
        return null;
    }
}
