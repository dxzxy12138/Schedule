package com.example.schedule.statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.schedule.R;
import com.example.schedule.ThemeChangeUtil;

public class StatisticActivity extends AppCompatActivity {

    SuperCircleView mSuperCircleView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChangeUtil.changeTheme(this);         /*fhw改*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        textView = findViewById(R.id.tv);
        mSuperCircleView = findViewById(R.id.superview);
        mSuperCircleView.setValue(textView);

    }
}
