package com.example.cardgame;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.HashMap;

/*******************

1 - 9 ---> 1
2 - 5 ---> 2
3 - 8 ---> 3
4 - 14 ---> 4
12 - 15 ---> 5
6 - 16 ---> 6
7 - 11 ---> 7
10 - 13 ---> 8

******************/


public class CardFlip_Activity extends AppCompatActivity implements View.OnClickListener {

    ImageSwitcher[] imgswitcher;
    TextView high_score, current_score;
    boolean showBack[];
    final Context context = this;
    Button backbutton,reset_high_score;
    int count = 0;
    volatile int anyopen = 0;
    int penalty = 0, currscore = 0;
    int highscore = 0;
    private Prefs prefs;

    int[] touched = new int[16];

    HashMap<Integer,Integer> hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip_);
        prefs = new Prefs(this);
        instantiate();
    }

    @SuppressLint("HandlerLeak")
    private Handler h = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != 0) {
                //updateUI();
                int id = msg.what;

                if (id < 20) {
                    imgswitcher[id - 1].setInAnimation(context, R.anim.slide_in_right); // added
                    imgswitcher[id - 1].setOutAnimation(context, R.anim.slide_out_right); // added
                    Drawable d = ContextCompat.getDrawable(context, hm.get(id));
                    imgswitcher[id - 1].setImageDrawable(d);

                }
                else if (id < 200) {
                    final int ii = id - 100;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgswitcher[ii - 1].setInAnimation(context, R.anim.slide_in_right); // added
                            imgswitcher[ii - 1].setOutAnimation(context, R.anim.slide_out_right); // added
                            Drawable d = ContextCompat.getDrawable(context, hm.get(ii));
                            imgswitcher[ii - 1].setImageDrawable(d);
                        }
                    }, 2000);
                }
                else if (id < 300) {
                    final int ii = id - 200;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgswitcher[ii - 1].setInAnimation(context, R.anim.slide_in_left); // added
                            imgswitcher[ii - 1].setOutAnimation(context, R.anim.slide_out_left); // added
                            //Drawable d = getResources().getDrawable(R.drawable.back_side);
                            Drawable d = ContextCompat.getDrawable(context, R.drawable.back_side);
                            imgswitcher[ii - 1].setImageDrawable(d);
                        }
                    }, 2000);
                }
                else if (id < 400) {
                    final int ii = id - 300;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgswitcher[ii - 1].setInAnimation(context, R.anim.slide_in_right); // added
                            imgswitcher[ii - 1].setOutAnimation(context, R.anim.slide_out_right); // added
                            //Drawable d = getResources().getDrawable(R.drawable.joker);
                            //Drawable d = context.getResources().getDrawable(R.drawable.joker);
                            Drawable d = ContextCompat.getDrawable(context, R.drawable.joker);
                            imgswitcher[ii - 1].setImageDrawable(d);
                            imgswitcher[ii - 1].setOnClickListener(null);
                        }
                    }, 2000);
                }
                else if(id<2000){
                    id = id-1000;
                    String cs = "Current Score: "+id;
                    //Log.d("tester", "handleMessage: "+cs);
                    current_score.setText(cs);
                }
                else{
                    id = id-2000;
                    String hs = "High Score: "+id;
                    //Log.d("tester", "handleMessage: "+hs);
                    high_score.setText(hs);
                    backbutton.setText("Your Score: " + currscore);
                }
            }
        }
    };

    private void instantiate() {

        showBack = new boolean[16];
        imgswitcher = new ImageSwitcher[16];
        high_score = findViewById(R.id.high_score);
        current_score = findViewById(R.id.current_score);

        backbutton = findViewById(R.id.backbutton);
        reset_high_score = findViewById(R.id.backbutton2);
        backbutton.setOnClickListener(this);
        reset_high_score.setOnClickListener(this);

        String hs = "High Score: "+prefs.getHighScore();
        highscore = prefs.getHighScore();
        String cs = "Current Score: 0";
        high_score.setText(hs);
        current_score.setText(cs);

        hm = new HashMap<>();
        namehelper();

        imgswitcher[0] = findViewById(R.id.card1);
        imgswitcher[1] = findViewById(R.id.card2);
        imgswitcher[2] = findViewById(R.id.card3);
        imgswitcher[3] = findViewById(R.id.card4);
        imgswitcher[4] = findViewById(R.id.card5);
        imgswitcher[5] = findViewById(R.id.card6);
        imgswitcher[6] = findViewById(R.id.card7);
        imgswitcher[7] = findViewById(R.id.card8);
        imgswitcher[8] = findViewById(R.id.card9);
        imgswitcher[9] = findViewById(R.id.card10);
        imgswitcher[10] = findViewById(R.id.card11);
        imgswitcher[11] = findViewById(R.id.card12);
        imgswitcher[12] = findViewById(R.id.card13);
        imgswitcher[13] = findViewById(R.id.card14);
        imgswitcher[14] = findViewById(R.id.card15);
        imgswitcher[15] = findViewById(R.id.card16);

        for (int i = 0; i < 16; i++) {
            imgswitcher[i].setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView switcherImageView = new ImageView(getApplicationContext());
                    switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                            ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT
                    ));
                    switcherImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    switcherImageView.setImageResource(R.drawable.back_side);
                    //switcherImageView.setMaxHeight(100);
                    return switcherImageView;
                }
            });
            imgswitcher[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.backbutton):
                prefs.saveHighScore(highscore);
                finish();
                break;
            case(R.id.backbutton2):
                reset_scores();
                break;
            case (R.id.card1):
                cardFlip(1);
                break;
            case (R.id.card2):
                cardFlip(2);
                break;
            case (R.id.card3):
                cardFlip(3);
                break;
            case (R.id.card4):
                cardFlip(4);
                break;
            case (R.id.card5):
                cardFlip(5);
                break;
            case (R.id.card6):
                cardFlip(6);
                break;
            case (R.id.card7):
                cardFlip(7);
                break;
            case (R.id.card8):
                cardFlip(8);
                break;
            case (R.id.card9):
                cardFlip(9);
                break;
            case (R.id.card10):
                cardFlip(10);
                break;
            case (R.id.card11):
                cardFlip(11);
                break;
            case (R.id.card12):
                cardFlip(12);
                break;
            case (R.id.card13):
                cardFlip(13);
                break;
            case (R.id.card14):
                cardFlip(14);
                break;
            case (R.id.card15):
                cardFlip(15);
                break;
            case (R.id.card16):
                cardFlip(16);
                break;
        }

    }

    private void cardFlip(int ii) {
        //Log.d("tester", "cardFlip: id = "+ii);

        touched[ii-1]++;
        final int id = ii;

        Thread thread = new Thread(){
            @Override
            public void run() {
                // Do stuff…

                //Log.d("tester", "cardFlip: we're in run:  anyopen = " + anyopen + " id =  " + id);
                if (anyopen != 0 && anyopen != id) { //so we know there is a different open card


                    //Log.d("tester", "cardFlip: we're in case of anyopen = " + anyopen + " id =  " + id);

                    if (match(id, anyopen)) {

                        int x = anyopen;
                        anyopen = 0;
                        count = count + 2;
                        currscore +=20;
                        if (count == 16) {
                            highscore = currscore;
                            prefs.saveHighScore(highscore);
                            int hs = highscore+2000;
                            h.sendEmptyMessage(hs);
                            //backbutton.setText("Your Score: " + currscore);
                        }

                        if(currscore>highscore){
                            highscore = currscore;
                            int hs = highscore+2000;
                            h.sendEmptyMessage(hs);
                        }
                        int cs = currscore+1000;
                        h.sendEmptyMessage(cs);
                        h.sendEmptyMessage(id);
                        h.sendEmptyMessage(id+300);
                        h.sendEmptyMessage(x+300);

                    }

                    else {
                        int x = anyopen;
                        showBack[id-1] = false;
                        showBack[anyopen-1] = false;
                        anyopen = 0;

                        if(touched[id-1] >1 || touched[x-1]>1){
                            currscore = currscore - 10;
                        }

                        int cs = currscore+1000;
                        h.sendEmptyMessage(cs);

                        h.sendEmptyMessage(id);
                        h.sendEmptyMessage((id+200));
                        h.sendEmptyMessage((x+200));
                        //Log.d("tester", "cardFlip: we're in the else case in here, anyopen = " + anyopen + " id =  " + id);
                    }
                }
                else if (showBack[id - 1]) {
                        runOnUiThread(new Runnable() //run on ui thread
                        {
                            public void run() {
                                //Log.d("tester", "cardFlip: jus checking if");
                                imgswitcher[id - 1].setInAnimation(context, R.anim.slide_in_left); // added
                                imgswitcher[id - 1].setOutAnimation(context, R.anim.slide_out_left); // added
                                //Drawable d = getResources().getDrawable(R.drawable.back_side);
                                Drawable d = ContextCompat.getDrawable(context, R.drawable.back_side);
                                imgswitcher[id - 1].setImageDrawable(d);
                                showBack[id - 1] = false;
                                anyopen = 0;
                            }
                        });
                }
                else {
                    runOnUiThread(new Runnable() //run on ui thread
                    {
                        public void run() {
                            //Log.d("tester", "cardFlip: jus checking else");
                            anyopen = id;
                            showBack[id - 1] = true;
                            imgswitcher[id - 1].setInAnimation(context, R.anim.slide_in_right); // added
                            imgswitcher[id - 1].setOutAnimation(context, R.anim.slide_out_right); // added
                            //Drawable d = getResources().getDrawable(imageResource);
                            Drawable d = ContextCompat.getDrawable(context, hm.get(id));
                            imgswitcher[id - 1].setImageDrawable(d);

                        }
                    });
                }
            }
            private boolean match(int open , int i){
                if((open == 1 && i ==9) || (open == 9 && i ==1)){
                    return true;
                }
                if((open == 2 && i ==5) || (open == 5 && i ==2)){
                    return true;
                }
                if((open == 3 && i ==8) || (open == 8 && i ==3)){
                    return true;
                }
                if((open == 4 && i ==14) || (open == 14 && i ==4)){
                    return true;
                }
                if((open == 12 && i ==15) || (open == 15 && i ==12)){
                    return true;
                }
                if((open == 6 && i ==16) || (open == 16 && i ==6)){
                    return true;
                }
                if((open == 7 && i ==11) || (open == 11 && i ==7)){
                    return true;
                }

                if((open == 10 && i ==13) || (open == 13 && i ==10)){
                    return true;
                }
                return false;
            }
        };
        thread.start();

    }

    private void namehelper(){

        hm.put(1 , R.drawable.faces1);
        hm.put(9, R.drawable.faces1);

        hm.put(2 , R.drawable.faces2);
        hm.put(5, R.drawable.faces2);

        hm.put(3 , R.drawable.faces3);
        hm.put(8, R.drawable.faces3);

        hm.put(4 , R.drawable.faces4);
        hm.put(14, R.drawable.faces4);

        hm.put(15 , R.drawable.faces5);
        hm.put(12, R.drawable.faces5);

        hm.put(6, R.drawable.faces6);
        hm.put(16, R.drawable.faces6);

        hm.put(7, R.drawable.faces7);
        hm.put(11, R.drawable.faces7);

        hm.put(10 , R.drawable.faces8);
        hm.put(13, R.drawable.faces8);
    }

    private void reset_scores(){
        prefs.reset_high_score();
        highscore = 0;
        h.sendEmptyMessage(2000);
    }

}

