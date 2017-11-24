package com.eagora.echosoft.eagora;

import android.os.Bundle;
import android.app.Activity;

public class FAQActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
