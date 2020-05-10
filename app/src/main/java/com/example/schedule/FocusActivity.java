package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.os.SystemClock;
import android.widget.Chronometer;

public class FocusActivity extends AppCompatActivity  {
    private Button btEnd = null;
    private Button btoStart = null;
    private Chronometer chronometer = null;
    private EditText edtime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        initView();
    }

    //初始化时钟和按钮
    private void initView(){
        btEnd = (Button)super.findViewById(R.id.endbutton);
        btEnd.setOnClickListener(new endlistener());
        chronometer = (Chronometer)super.findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new endlistener());
        btoStart = (Button)super.findViewById(R.id.button2);
        btoStart.setOnClickListener(new endlistener());
        edtime = (EditText)super.findViewById(R.id.time);

    }


    public class endlistener implements OnClickListener, Chronometer.OnChronometerTickListener {
        //@RequiresApi(api = Build.VERSION_CODES.N)
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button2:
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    int len_edtime = edtime.length();
                    if(len_edtime!=0){
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        String st = "学习开始!";
                        Toast t = Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"请输入时长",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.endbutton:
                    chronometer.stop();
                    String end = "下次请加油!";
                    Toast toast = Toast.makeText(getApplicationContext(), end, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    //chronometer.setBase(SystemClock.elapsedRealtime()-30);
                    //Toast.makeText(getApplicationContext(), end, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FocusActivity.this, MainActivity.class);
                    FocusActivity.this.startActivity(intent);
                    break;
            }
        }

        @Override
        public void onChronometerTick(Chronometer c) {
            String time1 = c.getText().toString();
            String time = edtime.getText().toString();
            if (time1.equals(time)) {
                Toast.makeText(FocusActivity.this, "恭喜你，可以休息了！", Toast.LENGTH_SHORT).show();
                chronometer.stop();
            }
        }
    }
}