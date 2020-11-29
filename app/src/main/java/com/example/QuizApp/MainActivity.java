package com.example.QuizApp;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_QUIZ=1;

    public static final String SHARED_PREF="sharedPrefs";
    public static final String KEY_HIGHSCORE="keyHighScore";
    private TextView textViewHighScore;
    private int highScore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonStartQuiz=findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
        textViewHighScore=findViewById(R.id.textView_highscore);
        loadHighScore();



    }
    private void startQuiz()
    {
        Intent intent=new Intent(this,QuizActivity.class);
        startActivityForResult(intent,REQUEST_CODE_QUIZ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_QUIZ)
        {
            if(resultCode==RESULT_OK)
            {
                int score=data.getIntExtra(QuizActivity.EXTRA_SCORE,0);
                if(score >highScore)
                {
                    updateHighScore(score);
                }
            }
        }

    }
    private void loadHighScore()
    {
        SharedPreferences preferences=getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        highScore=preferences.getInt(KEY_HIGHSCORE,0);
        textViewHighScore.setText("HighScore: "+highScore);
    }

    private void updateHighScore(int score) {
        highScore=score;
        textViewHighScore.setText("HighScore: "+highScore);
        SharedPreferences preferences=getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(KEY_HIGHSCORE,highScore);
        editor.apply();


    }





}