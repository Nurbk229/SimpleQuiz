package com.nurbk.ps.projectquestion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String KEY_COUNTER = "counter";
    static String KEY_RESULT = "result";


    TextView countQuestion, txtQuestion;
    RadioGroup rgAns;
    Button btnNext;
    RadioButton rBC1, rBC2, rBC3;

    ArrayList<Question> questions = new ArrayList();
    Question question;
    ObjectAnimator animatorTrue, animatorFalse;

    int counter = 1;
    int result;
    int idCheck;

    boolean isMove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();


        rgAns = findViewById(R.id.rgAns);
        btnNext = findViewById(R.id.btnNext);
        rBC1 = findViewById(R.id.rBC1);
        rBC2 = findViewById(R.id.rBC2);
        rBC3 = findViewById(R.id.rBC3);
        countQuestion = findViewById(R.id.countQuestion);
        txtQuestion = findViewById(R.id.txtQuestion);


        counter = getIntent().getIntExtra(KEY_COUNTER, 1);


        result = getIntent().getIntExtra(KEY_RESULT, 5);


        Log.e("ttttt", result + "");



        animatorTrue = (ObjectAnimator) AnimatorInflater.loadAnimator(getBaseContext(), R.animator.bg_ans_true);
        animatorFalse = (ObjectAnimator) AnimatorInflater.loadAnimator(getBaseContext(), R.animator.bg_ans_false);


        question = questions.get(counter - 1);

        txtQuestion.setText(question.getQuestion());
        rBC1.setText(question.getChoice1());
        rBC2.setText(question.getChoice2());
        rBC3.setText(question.getChoice3());



        String counterQuestion = getResources().getString(R.string.counterQuestion,
                savedInstanceState != null ?
                        savedInstanceState.getInt(KEY_COUNTER) : getIntent().getIntExtra(KEY_COUNTER, 1)
                ,
                questions.size());


        countQuestion.setText(counterQuestion);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chickAns();
            }
        });

        rgAns.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                btnNext.setVisibility(View.VISIBLE);

                idCheck = radioGroup.getCheckedRadioButtonId();

            }
        });

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNTER, counter);
    }

    private void chickAns() {

        RadioButton ans = findViewById(idCheck);
        GradientDrawable d = (GradientDrawable) ans.getBackground();

        rBC1.setEnabled(false);
        rBC2.setEnabled(false);
        rBC3.setEnabled(false);

        if (btnNext.getText() == getString(R.string.Confirm)) {
            if (ans.getText().toString().equals(question.getAnswer())) {
                    animatorTrue.setTarget(d);
                    ans.setBackground(d);
                    animatorTrue.start();
            } else {
                animatorFalse.setTarget(d);
                ans.setBackground(d);
                animatorFalse.start();
                result -= 1;
            }
        }
        d.setColor(ResourcesCompat.getColor(getResources(), android.R.color.white, null));

        if (counter == questions.size()) {

            if (btnNext.getText() == getString(R.string.finish)) {
                finish();
                Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                intent.putExtra(KEY_RESULT, result);
                startActivity(intent);

            }
            btnNext.setText(getString(R.string.finish));
            return;
        }

        if (btnNext.getText() == getString(R.string.next) && isMove) {
            counter++;
            finish();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra(KEY_COUNTER, counter);
            intent.putExtra(KEY_RESULT, result);
            startActivity(intent);

        }
        isMove = !isMove;
        btnNext.setText(getString(R.string.next));
    }

    private void getData() {
        questions.add(new Question(getString(R.string.q1)
                , getString(R.string.q1_choice1)
                , getString(R.string.q1_choice2)
                , getString(R.string.q1_choice3)
                , getString(R.string.q1_choice2)));

        questions.add(new Question(getString(R.string.q2)
                , getString(R.string.q2_choice1)
                , getString(R.string.q2_choice2)
                , getString(R.string.q2_choice3)
                , getString(R.string.q2_choice3)));

        questions.add(new Question(getString(R.string.q3)
                , getString(R.string.q3_choice1)
                , getString(R.string.q3_choice2)
                , getString(R.string.q3_choice3)
                , getString(R.string.q3_choice1)));

        questions.add(new Question(getString(R.string.q4)
                , getString(R.string.q4_choice1)
                , getString(R.string.q4_choice2)
                , getString(R.string.q4_choice3)
                , getString(R.string.q4_choice2)));

        questions.add(new Question(getString(R.string.q5)
                , getString(R.string.q5_choice1)
                , getString(R.string.q5_choice2)
                , getString(R.string.q5_choice3)
                , getString(R.string.q5_choice1)));
    }
}