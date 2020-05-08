package com.example.schedule;

import android.app.Activity;

import com.example.schedule.R;



public class ThemeChangeUtil {
    public static boolean isChange = false;
    public static int size=2;
    public static void changeTheme(Activity activity){
        if(isChange && size == 1){
            activity.setTheme(R.style.NightTheme1);
        }
        if(isChange && size == 2){
            activity.setTheme(R.style.NightTheme2);
        }
        if(isChange && size == 3){
            activity.setTheme(R.style.NightTheme3);
        }
        if(!isChange && size == 1){
            activity.setTheme(R.style.DayTheme1);
        }
        if(!isChange && size == 2){
            activity.setTheme(R.style.DayTheme2);
        }
        if(!isChange && size == 3){
            activity.setTheme(R.style.DayTheme3);
        }

    }
}
