package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.schedule.set.SetActivity;
import com.example.schedule.sleep.SleepActivity;
import com.example.schedule.statistic.StatisticActivity;

public class MainActivity extends AppCompatActivity {
    private Button mBtnToDo;
    private  Button mBtnSleep;
    private  Button mBtnSet;
    private Button mBtnStatistic;
    private Button mBtnDiary;
    private Button mBtnFocus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChangeUtil.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnToDo = (Button) findViewById(R.id.btn_todo);
        mBtnSleep = findViewById(R.id.btn_sleep);
        mBtnSet = findViewById(R.id.btn_set);
        mBtnStatistic=findViewById(R.id.btn_statistic);
        mBtnDiary=findViewById(R.id.btn_diary);
        mBtnFocus=findViewById(R.id.btn_focus);
        setListeners();
    }

    private void setListeners() {
        OnClick onClick = new OnClick();
        mBtnToDo.setOnClickListener(onClick);
        mBtnSleep.setOnClickListener(onClick);
        mBtnSet.setOnClickListener(onClick);
        mBtnStatistic.setOnClickListener(onClick);
        mBtnDiary.setOnClickListener(onClick);
        mBtnFocus.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_todo:
                    intent = new Intent(MainActivity.this, ToDoMainActivity.class);
                    break;
                case R.id.btn_sleep:
                    intent = new Intent(MainActivity.this, SleepActivity.class);
                    break;
                case R.id.btn_set:
                    intent = new Intent(MainActivity.this, SetActivity.class);
                    break;
                case R.id.btn_statistic:
                    intent = new Intent(MainActivity.this, StatisticActivity.class);
                    break;
                case R.id.btn_diary:
                    intent = new Intent(MainActivity.this, DiaryActivity.class);
                    break;
                case R.id.btn_focus:
                    intent = new Intent(MainActivity.this, FocusActivity.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    }
}
