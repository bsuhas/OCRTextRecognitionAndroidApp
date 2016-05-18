package com.ocrtextrecognitionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by suhasbachewar on 10/5/16.
 */

public class MainActivity extends AppCompatActivity implements IOCRCallBack {

    private String mAPiKey = "helloworld"; //TODO Add your own Registered API key
    private boolean isOverlayRequired;
    private String mImageUrl;
    private String mLanguage;
    private TextView mTxtResult;
    private IOCRCallBack mIOCRCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIOCRCallBack = this;
        mImageUrl = "http://dl.a9t9.com/blog/ocr-online/screenshot.jpg"; // Image url to apply OCR API
        mLanguage = "eng"; //Language
        isOverlayRequired = true;
        init();

    }

    private void init() {
        mTxtResult = (TextView) findViewById(R.id.actual_result);
        TextView btnCallAPI = (TextView) findViewById(R.id.btn_call_api);

        if (btnCallAPI != null) {
            btnCallAPI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OCRAsyncTask oCRAsyncTask = new OCRAsyncTask(MainActivity.this, mAPiKey, isOverlayRequired, mImageUrl, mLanguage,mIOCRCallBack);
                    oCRAsyncTask.execute();

                }
            });
        }
    }

    @Override
    public void getOCRCallBackResult(String response) {
        mTxtResult.setText(response);
    }
}
