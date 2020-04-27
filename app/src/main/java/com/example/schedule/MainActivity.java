package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.schedule.sleep.SleepActivity;

public class MainActivity extends AppCompatActivity {
    private Button mBtnToDo;
    private  Button mBtnSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnToDo = (Button) findViewById(R.id.btn_todo);
        mBtnSleep = findViewById(R.id.btn_sleep);
        setListeners();
    }

    private void setListeners() {
        OnClick onClick = new OnClick();
        mBtnToDo.setOnClickListener(onClick);
        mBtnSleep.setOnClickListener(onClick);
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
                default:
                    break;
            }
            startActivity(intent);
        }
    }
}
