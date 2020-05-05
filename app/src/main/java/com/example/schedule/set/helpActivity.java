package com.example.schedule.set;

import androidx.appcompat.app.AppCompatActivity;
import com.example.schedule.R;
import com.example.schedule.ThemeChangeUtil;

import android.os.Bundle;

public class helpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChangeUtil.changeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
}
