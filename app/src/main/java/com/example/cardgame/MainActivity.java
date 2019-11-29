package com.example.cardgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_levelEasy, button_levelMedium, button_instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeAll();

    }


    private void initializeAll(){

        button_levelEasy= findViewById(R.id.easy_button);
        button_levelMedium = findViewById(R.id.medium_button);
        button_instruction = findViewById(R.id.instruction_button);

        button_levelEasy.setOnClickListener(this);
        button_levelMedium.setOnClickListener(this);
        button_instruction.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case (R.id.easy_button):
                startActivity(new Intent(MainActivity.this, CardFlip_Activity.class));
                break;

            case(R.id.medium_button):
                startActivity(new Intent(MainActivity.this, MemeFlip_Activity.class));
                break;

            case(R.id.instruction_button):
                startActivity(new Intent(MainActivity.this, Instruction.class));
                break;

        }

    }
}
