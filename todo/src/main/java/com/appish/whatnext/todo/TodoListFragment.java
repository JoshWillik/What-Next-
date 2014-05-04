package com.appish.whatnext.todo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by josh on 5/4/14.
 */
public class TodoListFragment extends Fragment {

    private TodoList currentList;
    private TodoManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        this.manager = (TodoManager) new DatabaseTodoManager( getActivity() );
        this.currentList = this.manager.fetchList(1);

        View todoFragment = inflater.inflate(R.layout.todo_list, container, false);
        ListView todoList = (ListView) todoFragment.findViewById( R.id.todo_items );
        todoList.setAdapter( new ArrayAdapter<String>(
                getActivity(), R.layout.drawer_todo_item, manager.fetchTodosAsStrings( new TodoList( 1 ) )
        ));
        return todoFragment;
    }
}
