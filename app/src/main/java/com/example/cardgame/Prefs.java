package com.example.cardgame;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Activity activity){
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighScore(int score){

        int val = preferences.getInt("hoho" , 0);
        int max = Math.max(val,score);
        preferences.edit().putInt("hoho" , max).apply();
    }

    public int getHighScore(){
        return preferences.getInt("hoho" , 0);
    }

    public void reset_high_score(){
        preferences.edit().putInt("hoho" , 0).apply();
    }

}
