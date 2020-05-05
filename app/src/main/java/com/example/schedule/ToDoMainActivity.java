package com.example.schedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.example.schedule.data.Constant;
import com.example.schedule.data.DatabaseManager;


/**
 * @author xinyu
 */
public class ToDoMainActivity extends AppCompatActivity
{

    private int labelId;
    private int sortBy;
    DatabaseManager databaseManager;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChangeUtil.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_main);
        init();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewItemActivity(ToDoMainActivity.this);
            }
        });

    }

    /**
     * 初始化设置
     */
    public void init() {
        sharedPreferences = getSharedPreferences(Constant.SHARED_SETTING, Context.MODE_APPEND);
        editor = sharedPreferences.edit();
        //第一次启动时设置默认新建的是想属于哪个集合
        if (sharedPreferences.getInt(Constant.DEFAULT_LABEL_ID_SETTING, -1) < 0) {
            editor.putInt(Constant.DEFAULT_LABEL_ID_SETTING, Constant.DEFAULT_LABEL_ID).commit();
        }

        databaseManager = new DatabaseManager(this);
        labelId = sharedPreferences.getInt(Constant.DEFAULT_LABEL_ID_SETTING, Constant.DEFAULT_LABEL_ID);
        sortBy = Constant.SORT_BY_DEFAULT;
        startListFragment(labelId, sortBy);
        //默认打开ListFragment
    }

    /**
     * 新建待办项完成后刷新列表
     */
    @Override
    protected void onResume() {
        super.onResume();
        startListFragment(labelId, sortBy);
    }

    /**
     * 刷新列表
     *
     * @param labelId 显示的列表集ID
     * @param sortBy  排序方式
     */
    public void startListFragment(int labelId, int sortBy) {
        ListFragment listFragment = new ListFragment();
        listFragment.setListFragment(databaseManager, labelId, sortBy);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, listFragment).commit();
    }

    /**
     * 新建一个事件，打开新的Activity
     *
     * @param context 上下文
     */
    public void startNewItemActivity(Context context) {
        Intent intent = new Intent(context, NewItemActivity.class);
        intent.putExtra(Constant.NEW_OR_EDIT, Constant.NEW);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 最后一个Activity销毁前，关闭databaseManager
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseManager.closeDatabase();
    }
}
