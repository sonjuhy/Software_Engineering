package com.example.software_engineering;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alram);




        출처: https://yeolco.tistory.com/90 [열코의 프로그래밍 일기]

        // 알람음 재생
       // this.mediaPlayer = MediaPlayer.create(this, R.raw.TAEYEON_Spark);/////////// 여기에 음악넣으면 됩니다!
        this.mediaPlayer.start();
        long[] pattern = {1000,3000}; // 2000 = x , 1000 = y
        vibrator.vibrate(pattern,0);
        findViewById(R.id.btnClose).setOnClickListener(mClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // MediaPlayer release
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    /* 알람 종료 */
    private void close() {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
        else {
            vibrator.cancel();
        }

        finish();
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnClose:
                    // 알람 종료
                    close();

                    break;
            }
        }
    };
}
