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
import java.util.Random;

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


public class MemeFlip_Activity extends AppCompatActivity implements View.OnClickListener {

    ImageSwitcher[] imgswitcher;
    TextView high_score, current_score;
    boolean showBack[];
    final Context context = this;
    Button backbutton, reset_high_score;
    int count = 0;
    volatile int anyopen = 0;
    int currscore = 0;
    int highscore = 0;
    private Prefs prefs;

    HashMap<Integer,Integer> hm;

    HashMap<Integer,Integer> memeMap;

    int[] touched = new int[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_flip_);
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

                if (id < 100) {
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
                    }, 1500);
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
                            Drawable d = ContextCompat.getDrawable(context, R.drawable.harambe);
                            imgswitcher[ii - 1].setImageDrawable(d);
                        }
                    }, 1500);
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
                            Drawable d = ContextCompat.getDrawable(context, R.drawable.grumpycat);
                            imgswitcher[ii - 1].setImageDrawable(d);
                            imgswitcher[ii - 1].setOnClickListener(null);
                        }
                    }, 1500);
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
        high_score = findViewById(R.id.high_score_meme);
        current_score = findViewById(R.id.current_score_meme);

        hm = new HashMap<>();
        memeMap = new HashMap<>();
        namehelper(hm);

        backbutton = findViewById(R.id.backbuttonmeme);
        reset_high_score = findViewById(R.id.backbuttonmeme2);
        backbutton.setOnClickListener(this);
        reset_high_score.setOnClickListener(this);

        String hs = "High Score: "+prefs.getHighScore();
        highscore = prefs.getHighScore();
        String cs = "Current Score: 0";
        high_score.setText(hs);
        current_score.setText(cs);

        imgswitcher[0] = findViewById(R.id.card1meme);
        imgswitcher[1] = findViewById(R.id.card2meme);
        imgswitcher[2] = findViewById(R.id.card3meme);
        imgswitcher[3] = findViewById(R.id.card4meme);
        imgswitcher[4] = findViewById(R.id.card5meme);
        imgswitcher[5] = findViewById(R.id.card6meme);
        imgswitcher[6] = findViewById(R.id.card7meme);
        imgswitcher[7] = findViewById(R.id.card8meme);
        imgswitcher[8] = findViewById(R.id.card9meme);
        imgswitcher[9] = findViewById(R.id.card10meme);
        imgswitcher[10] = findViewById(R.id.card11meme);
        imgswitcher[11] = findViewById(R.id.card12meme);
        imgswitcher[12] = findViewById(R.id.card13meme);
        imgswitcher[13] = findViewById(R.id.card14meme);
        imgswitcher[14] = findViewById(R.id.card15meme);
        imgswitcher[15] = findViewById(R.id.card16meme);

        for (int i = 0; i < 16; i++) {
            imgswitcher[i].setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView switcherImageView = new ImageView(getApplicationContext());
                    switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                            ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT
                    ));
                    switcherImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    switcherImageView.setImageResource(R.drawable.harambe);
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
            case (R.id.backbuttonmeme):
                reset_scores();
                finish();
                break;
            case(R.id.backbuttonmeme2):
                reset_scores();
                break;
            case (R.id.card1meme):
                cardFlip(1);
                break;
            case (R.id.card2meme):
                cardFlip(2);
                break;
            case (R.id.card3meme):
                cardFlip(3);
                break;
            case (R.id.card4meme):
                cardFlip(4);
                break;
            case (R.id.card5meme):
                cardFlip(5);
                break;
            case (R.id.card6meme):
                cardFlip(6);
                break;
            case (R.id.card7meme):
                cardFlip(7);
                break;
            case (R.id.card8meme):
                cardFlip(8);
                break;
            case (R.id.card9meme):
                cardFlip(9);
                break;
            case (R.id.card10meme):
                cardFlip(10);
                break;
            case (R.id.card11meme):
                cardFlip(11);
                break;
            case (R.id.card12meme):
                cardFlip(12);
                break;
            case (R.id.card13meme):
                cardFlip(13);
                break;
            case (R.id.card14meme):
                cardFlip(14);
                break;
            case (R.id.card15meme):
                cardFlip(15);
                break;
            case (R.id.card16meme):
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
                            prefs.saveHighScore(currscore);
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
                            Drawable d = ContextCompat.getDrawable(context, R.drawable.harambe);
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
                int x = hm.get(open);
                int y = hm.get(i);

                if(memeMap.get(x) == y){
                    return true;
                }
                return false;

            }
        };
        thread.start();
    }

    private void namehelper(HashMap<Integer,Integer> hm){

        int[] a = new int[16]; //a[i] will contain the image switcher no.
        int[] b = new int[16]; //b[i] R.id.memexx.

        b[0] = R.drawable.meme10;
        b[1] = R.drawable.meme11;
        b[2] = R.drawable.meme20;
        b[3] = R.drawable.meme21;
        b[4] = R.drawable.meme30;
        b[5] = R.drawable.meme31;
        b[6] = R.drawable.meme40;
        b[7] = R.drawable.meme41;
        b[8] = R.drawable.meme50;
        b[9] = R.drawable.meme51;
        b[10] = R.drawable.meme60;
        b[11] = R.drawable.meme61;
        b[12] = R.drawable.meme70;
        b[13] = R.drawable.meme71;
        b[14] = R.drawable.meme80;
        b[15] = R.drawable.meme81;

        for(int i = 0 ; i<16 ; i++){   //chose an image switcher and apply an image
            a[i] = i+1;
        }

        randomize(a);

        memeMap.put(R.drawable.meme10 , R.drawable.meme11);
        memeMap.put(R.drawable.meme11 , R.drawable.meme10);

        memeMap.put(R.drawable.meme20 , R.drawable.meme21);
        memeMap.put(R.drawable.meme21 , R.drawable.meme20);

        memeMap.put(R.drawable.meme30 , R.drawable.meme31);
        memeMap.put(R.drawable.meme31 , R.drawable.meme30);

        memeMap.put(R.drawable.meme40 , R.drawable.meme41);
        memeMap.put(R.drawable.meme41 , R.drawable.meme40);

        memeMap.put(R.drawable.meme50 , R.drawable.meme51);
        memeMap.put(R.drawable.meme51 , R.drawable.meme50);

        memeMap.put(R.drawable.meme60 , R.drawable.meme61);
        memeMap.put(R.drawable.meme61 , R.drawable.meme60);

        memeMap.put(R.drawable.meme70 , R.drawable.meme71);
        memeMap.put(R.drawable.meme71 , R.drawable.meme70);

        memeMap.put(R.drawable.meme80 , R.drawable.meme81);
        memeMap.put(R.drawable.meme81 , R.drawable.meme80);

        for(int i=0 ; i<16; i++){
            hm.put(a[i] , b[i]);
        }

    }

    private void randomize(int[] a){
        int n =a.length;
        Random rand = new Random();
        for(int i=n-1; i>=0;i--){
            int al = rand.nextInt(i+1);
            int temp = a[i];
            a[i] = a[al];
            a[al] = temp;
        }
    }

    private void reset_scores(){
        prefs.reset_high_score();
        highscore = 0;
        h.sendEmptyMessage(2000);
    }

}

