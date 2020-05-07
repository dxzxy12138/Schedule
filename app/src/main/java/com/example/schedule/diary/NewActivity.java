package com.example.schedule.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedule.R;

public class NewActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton;
    private EditText mDate,mWeek,mWeather, mContent;
    private String date,week,weather, content;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        mButton = (Button) findViewById(R.id.save);
        mDate = (EditText) findViewById(R.id.date);
        mWeek = (EditText) findViewById(R.id.week);
        mWeather = (EditText) findViewById(R.id.weather);
        mContent = (EditText) findViewById(R.id.content);
        mButton.setOnClickListener(this);
        id = getIntent().getIntExtra("id",1);
        date = getIntent().getStringExtra("date");
        weather = getIntent().getStringExtra("weather");
        week = getIntent().getStringExtra("week");
        content = getIntent().getStringExtra("content");
        if (date!=null) {
            mDate.setText(date);
            mWeek.setText(week);
            mContent.setText(content);
            mWeather.setText(weather);
        }
        if (!getIntent().getBooleanExtra("isshow", true)) {
            mButton.setVisibility(View.GONE);
        }
    }



    @Override
    public void onClick(View v) {
        //保存
        if (mDate.getText().toString().trim().equals("")) {
            Toast.makeText(this, "日期不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mWeek.getText().toString().trim().equals("")) {
            Toast.makeText(this, "星期不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mWeather.getText().toString().trim().equals("")) {
            Toast.makeText(this, "天气不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mContent.getText().toString().trim().equals("")) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (DaoDiary.instance().insert(new Diary(id,
                mDate.getText().toString(), mWeek.getText().toString(),
                mWeather.getText().toString(), mContent.getText().toString()))) {

            Toast.makeText(this, "插入或修改成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "插入或修改失败", Toast.LENGTH_SHORT).show();
        }
    }



}

