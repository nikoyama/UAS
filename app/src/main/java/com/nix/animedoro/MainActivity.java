package com.nix.animedoro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/*
TODO:
 - timer buat sesi break
 - fitur tambah waktu study
 - ngejalanin timer di background
 - better UI
 - music?
 */
public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 60000 * 40; // 40 minutes
    private TextView tvTimer;
    private TextView tvTodo;
    private Button btnStart;
    private Button btnReset;
    private Button btnPause;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTimer = findViewById(R.id.tv_timer);
        tvTodo = findViewById(R.id.tv_todo);
        btnStart = findViewById(R.id.btn_start);
        btnReset = findViewById(R.id.btn_reset);
        btnPause = findViewById(R.id.btn_pause);
        btnStart.setOnClickListener(v -> {
            startTimer();
        });
        btnReset.setOnClickListener(v -> {
            if (mTimerRunning) {
                pauseTimer();
                resetTimer();
            } else {
                resetTimer();
            }
        });
        btnPause.setOnClickListener(v -> {
            if (mTimerRunning) {
                pauseTimer();
            }
        });
        tvTodo.setOnClickListener(v -> {
            // TODO: todo list pake room https://developer.android.com/codelabs/android-room-with-a-view
            Toast.makeText(getApplicationContext(), "COMING SOON", Toast.LENGTH_SHORT).show();
        });
        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                Toast.makeText(getApplicationContext(), "FINISH", Toast.LENGTH_SHORT).show();
                // TODO: popup lanjut atau break
            }
        }.start();
        mTimerRunning = true;
        btnStart.setVisibility(View.INVISIBLE);
        btnPause.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.VISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        btnStart.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimerRunning = false;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        btnStart.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.INVISIBLE);
        btnReset.setVisibility(View.INVISIBLE);
        updateCountDownText();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimer.setText(timeLeftFormatted);
    }
}