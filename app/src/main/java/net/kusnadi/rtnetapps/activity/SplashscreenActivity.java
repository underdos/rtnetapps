package net.kusnadi.rtnetapps.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import net.kusnadi.rtnetapps.R;
import net.kusnadi.rtnetapps.config.AppConfig;
import net.kusnadi.rtnetapps.helper.SessionManagerHelper;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends BaseActivity {

    private SessionManagerHelper sessionManagerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        sessionManagerHelper = new SessionManagerHelper(SplashscreenActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!sessionManagerHelper.isLogin()){
                    Intent loginIntent = new Intent(SplashscreenActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Intent homeIntent = new Intent(SplashscreenActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, AppConfig.SPLASH_TIME_OUT);
    }
}
