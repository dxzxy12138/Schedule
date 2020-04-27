package com.example.schedule.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Database管理类
 */
public class DatabaseManager implements Serializable {

    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase database;

    /**
     * 创建数据库，并得到SQLiteDatabase对象
     *
     * @param context 传入上下文对象
     */
    public DatabaseManager(Context context) {
        sqLiteOpenHelper = new MyDatabaseHelper(context, "ToDoList.db", null, 1);
        database = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * 向数据库中插入一个新的待办项Item
     *
     * @param event 要新建的Event
     */
    public void insertEventItem(Event event) {
        ContentValues values = new ContentValues();
        values.put(Constant.IS_DONE, event.isDone() ? 1 : 0);
        values.put(Constant.TITLE, event.getEventTitle());
        values.put(Constant.PS, event.getEventPs());
        values.put(Constant.DATE_LINE, DateHandler.parseDatetoString(event.getDateLine()));
        values.put(Constant.LABEL_ID, event.getLabelId());
        database.insert(Constant.TODO_LIST, null, values);
    }

    /**
     * 从数据库中删除一个待办事项Item
     *
     * @param event 要删除的Event
     */
    public void deleteEventItem(Event event) {
        database.delete(Constant.TODO_LIST, "id = ?", new String[]{"" + event.getEventId()});
    }

    /**
     * 从数据库中更新一个待办事项Item
     *
     * @param event 要更新的Event
     */
    public void updateEventItem(Event event) {
        ContentValues values = new ContentValues();
        values.put(Constant.IS_DONE, event.isDone() ? 1 : 0);
        values.put(Constant.TITLE, event.getEventTitle());
        values.put(Constant.PS, event.getEventPs());
        values.put(Constant.DATE_LINE, DateHandler.parseDatetoString(event.getDateLine()));
        values.put(Constant.LABEL_ID, event.getLabelId());
        database.update(Constant.TODO_LIST, values, "id = ?", new String[]{"" + event.getEventId()});
    }


    /**
     * 查询数据库中的待办项
     *
     * @param labelId 想要查询的待办项的集合ID
     * @return 返回要查询的待办项列表
     */
    public List<Event> queryLabelItems(int labelId) {
        List<Event> eventList = new ArrayList<Event>();
        Cursor cursor = database.query(Constant.TODO_LIST, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Event event;
            if ((cursor.getInt(cursor.getColumnIndex(Constant.LABEL_ID)) == labelId &&
                    cursor.getInt(cursor.getColumnIndex(Constant.IS_DONE)) == 0)) {
                event = new Event(cursor.getInt(cursor.getColumnIndex(Constant.ID)),
                        cursor.getInt(cursor.getColumnIndex(Constant.IS_DONE)) == 1,
                        cursor.getString(cursor.getColumnIndex(Constant.TITLE)),
                        cursor.getString(cursor.getColumnIndex(Constant.PS)),
                        DateHandler.parseStringtoDate(cursor.getString(cursor.getColumnIndex(Constant.DATE_LINE))),
                        cursor.getInt(cursor.getColumnIndex(Constant.LABEL_ID)));
                eventList.add(event);
            }
        }
        cursor.close();
        return eventList;
    }

    /**
     * 查询已经做过的事件Item
     *
     * @return 返回查询结果事件列表
     */
    public List<Event> queryDoneItems() {
        List<Event> eventList = new ArrayList<Event>();
        Cursor cursor = database.query(Constant.TODO_LIST, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Event event;
            if (cursor.getInt(cursor.getColumnIndex(Constant.IS_DONE)) == 1) {
                event = new Event(cursor.getInt(cursor.getColumnIndex(Constant.ID)),
                        cursor.getInt(cursor.getColumnIndex(Constant.IS_DONE)) == 1,
                        cursor.getString(cursor.getColumnIndex(Constant.TITLE)),
                        cursor.getString(cursor.getColumnIndex(Constant.PS)),
                        DateHandler.parseStringtoDate(cursor.getString(cursor.getColumnIndex(Constant.DATE_LINE))),
                        cursor.getInt(cursor.getColumnIndex(Constant.LABEL_ID)));
                eventList.add(event);
            }
        }
        cursor.close();
        return eventList;
    }

    /**
     * 关闭数据库
     */
    public void closeDatabase() {
        database.close();
    }


}

