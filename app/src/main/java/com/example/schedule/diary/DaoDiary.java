package com.example.schedule.diary;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.schedule.diary.Diary;

import java.util.ArrayList;
import java.util.List;

public class DaoDiary {
    private static DaoDiary mtask;
    private SQLiteDatabase mDb;
    private Context mContext;
    private DaoDiary() {}
    //    获取数据库实例
    public static DaoDiary instance() {
        if (mtask == null) {
            mtask = new DaoDiary();
        }
        return mtask;
    }
    /**
     * 初始化数据库
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        DbOpenHelper helper;
        helper = new DbOpenHelper(context, "diary", null, 1);
        mDb = helper.getReadableDatabase();//创建数据库
    }
    /**
     * 插入一列数据
     * @param who
     * @return
     */
    public boolean insert(Diary who) {
        if (mDb == null) {
            //数据库为空返回
            return false;
        }
//        要执行的sql语句
        String insterSql = "INSERT OR REPLACE INTO Diary (id,date,content,week,weather)\n" +
                "  VALUES(?,?,?,?,?)";
        Object[] values = {who.getId(), who.getDate(), who.getContent(), who.getWeek(), who.getWeather()};
        boolean result;
        try {
            if (mDb != null) {
                mDb.execSQL(insterSql, values);
            }
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "插入或更新失败", Toast.LENGTH_SHORT).show();
            result = false;
        }
        return result;
    }

    /**
     *删除数据
     */
    public boolean delet(int id) {
        String deleteSql="DELETE FROM Diary WHERE id = ?";
        if (mDb == null) {
            return false;
        }
        boolean result;
        try {
            mDb.execSQL(deleteSql,new Object[]{id});
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
            result=false;
        }
        return result;
    }

    /**
     * 查询整个表
     * @return
     */
    public List<Diary> selectAll() {
        if (mDb == null) {
            return null;
        }
        //数据库游标
        Cursor cursor = null;
        List<Diary> per = null;
        try {
            cursor = mDb.rawQuery("SELECT * FROM Diary", new String[]{});
            if (cursor != null) { //非null判断
                per = new ArrayList<>();
                int index = 0;
                //如果cursor可以移动到下一条数据
                while (cursor.moveToNext()) {
                    Diary person = new Diary();
                    person.setId(cursor.getInt(index));
                    person.setDate(cursor.getString(index + 1));
                    person.setContent(cursor.getString(index + 2));
                    person.setWeek(cursor.getString(index + 3));
                    person.setWeather(cursor.getString(index+4));
                    per.add(person);
                }
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return per;
    }

}