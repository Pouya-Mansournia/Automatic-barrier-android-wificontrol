package ir.pasargad.pouyamansournia.barrier.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.pasargad.pouyamansournia.barrier.R;


public class SplashST extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashabout);
        Thread time_control = new Thread() {
            public void run() {

                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent open_activity = new Intent("android.intent.action.DSYYY");
                    startActivity(open_activity);

                }
            }


        };
        time_control.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
