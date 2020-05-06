package com.example.schedule.set;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.MainActivity;
import com.example.schedule.R;
import com.example.schedule.ThemeChangeUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SetActivity extends AppCompatActivity {

    private Button mBtn2,mBtn4;
    private RadioGroup mBtn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChangeUtil.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        mBtn2 = findViewById(R.id.btn_night);
        mBtn4 = findViewById(R.id.btn_help);
        mBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this,helpActivity.class);
                startActivity(intent);
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThemeChangeUtil.isChange) {
                    ThemeChangeUtil.isChange = false;
                } else {
                    ThemeChangeUtil.isChange = true;
                }
                SetActivity.this.recreate();//重新创建当前Activity实例
            }
        });
        mBtn3 = findViewById(R.id.btn_size);
        mBtn3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                String Resault = radioButton.getText().toString();
                switch (Resault){
                    case "小":{
                        ThemeChangeUtil.size = 1;
                        Log.d("size:", String.valueOf(ThemeChangeUtil.size));
                        break;}
                    case "中":{
                        ThemeChangeUtil.size = 2;
                        Log.d("size:", String.valueOf(ThemeChangeUtil.size));
                        break;}
                    case "大":{
                        ThemeChangeUtil.size = 3;
                        Log.d("size:", String.valueOf(ThemeChangeUtil.size));
                        break;}
                }
            }
        });
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        finish();
    }
}

