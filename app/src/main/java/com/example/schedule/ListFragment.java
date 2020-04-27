package com.example.schedule;


import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.schedule.data.Constant;
import com.example.schedule.data.DatabaseManager;
import com.example.schedule.data.DateHandler;
import com.example.schedule.data.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private ListView listView;
    private ToDoAdapter toDoAdapter;
    private List<Event> eventList;

    private int labelId;
    private int sortBy;
    DatabaseManager databaseManager;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        listView = (ListView) view.findViewById(R.id.lists);
        registerForContextMenu(listView);
        init(labelId);

        eventList = getListData(labelId, sortBy);
        toDoAdapter = new ToDoAdapter(eventList, R.layout.todo_item, labelId,
                getContext(), databaseManager, sortBy);
        listView.setAdapter(toDoAdapter);


        return view;
    }

    public void setListFragment(DatabaseManager databaseManager, int labelId, int sortBy) {
        this.databaseManager = databaseManager;
        this.labelId = labelId;
        this.sortBy = sortBy;
    }

    /**
     * 做动态新建列表集的时候，把对列表集的操作，单独拿出
     *
     * @param labelId
     */
    public void init(int labelId) {
        switch (labelId) {
            case Constant.DEFAULT_LABEL_ID:
                getActivity().setTitle("默认集");
                break;
            case Constant.LABEL_ID_1:
                getActivity().setTitle("集合1");
                break;
            case Constant.LABEL_ID_2:
                getActivity().setTitle("集合2");
                break;
            case Constant.LABEL_ID_HAVE_DONE:
                getActivity().setTitle("已完成");
                break;
            default:
                break;
        }
    }

    /**
     * 获取制定列表集的事项列表
     *
     * @param labelId 指定的列表集
     * @param sortBy  按照什么规则排序
     * @return 获取的事项列表
     */
    private List<Event> getListData(int labelId, int sortBy) {

        List<Event> eventList1 = new ArrayList<Event>();
        if (labelId == Constant.LABEL_ID_HAVE_DONE) {
            eventList1 = databaseManager.queryDoneItems();
        } else {
            eventList1 = databaseManager.queryLabelItems(labelId);
        }
        switch (sortBy) {
            case Constant.SORT_BY_DEFAULT:
                break;
            case Constant.SORT_BY_DATE:
                Collections.sort(eventList1);
                break;
            case Constant.SORT_BY_SIGNIFICANCE:
                break;
        }
        return eventList1;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderIcon(R.mipmap.ic_launcher);
        menu.setHeaderTitle("长按选项");
        MenuInflater menuInflater = new MenuInflater(getActivity());
        menuInflater.inflate(R.menu.list_view_context_menu, menu);
    }

    /**
     * 列表项长按的菜单项处理
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit_item:
                Event event = eventList.get(menuInfo.position);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constant.NEW_OR_EDIT, Constant.EDIT);
                bundle.putInt(Constant.ID, event.getEventId());
                bundle.putBoolean(Constant.IS_DONE, event.isDone());
                bundle.putString(Constant.TITLE, event.getEventTitle());
                bundle.putString(Constant.PS, event.getEventPs());
                bundle.putInt(Constant.LABEL_ID, event.getLabelId());
                bundle.putString(Constant.DATE_LINE, DateHandler.parseDatetoString(event.getDateLine()));
                Intent intent = new Intent(getActivity(), NewItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.delete_item:
                Event event1 = eventList.get(menuInfo.position);
                databaseManager.deleteEventItem(event1);
                ((ToDoMainActivity) getActivity()).startListFragment(event1.getLabelId(), sortBy);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}