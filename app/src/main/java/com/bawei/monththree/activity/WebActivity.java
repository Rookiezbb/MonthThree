package com.bawei.monththree.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bawei.monththree.R;

public class WebActivity extends AppCompatActivity {

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView wb = (WebView) findViewById(R.id.wb);
        pb = (ProgressBar) findViewById(R.id.pb);
        String url = getIntent().getStringExtra("url");
        wb.loadUrl(url);
        WebSettings settings = wb.getSettings();
        settings.setJavaScriptEnabled(true);
        wb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    pb.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pb.setProgress(newProgress);//设置进度值
                }
            }
        });
    }
}
