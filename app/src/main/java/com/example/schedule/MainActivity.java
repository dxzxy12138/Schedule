package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mBtnToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnToDo = (Button) findViewById(R.id.btn_todo);
        setListeners();
    }

    private void setListeners() {
        OnClick onClick = new OnClick();
        mBtnToDo.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_todo:
                    intent = new Intent(MainActivity.this, ToDoMainActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
