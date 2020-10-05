package com.nurbk.ps.projectquestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        String result = getResources().getString(R.string.counterQuestion,
                getIntent().getIntExtra(MainActivity.KEY_RESULT, 0), 5);

        Button btn = findViewById(R.id.btnStart);
        btn.setText(result);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}