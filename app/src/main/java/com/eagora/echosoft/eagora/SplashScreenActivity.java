package com.eagora.echosoft.eagora;

/**
 * Created by rosangela on 21/11/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.os.Handler;
public class SplashScreenActivity extends Activity{
    Intent myintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreenmaker);
        myintent = new Intent(this, SplashScreenActivity.class);


        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.antirotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.zoom);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        imageView.startAnimation(animation_2);


        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        startActivity(myintent);
                        finish();
                        Intent i = new Intent(getBaseContext(),InicioActivity.class);
                        startActivity(i);
                    }
                }, 3000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
