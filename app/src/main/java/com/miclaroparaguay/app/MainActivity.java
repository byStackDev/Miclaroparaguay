package com.miclaroparaguay.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

   private  WebView webView;
   private boolean buttonsVisible = false;


    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = findViewById(R.id.webView);

        webView.setVisibility(View.INVISIBLE);
        webView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setLoadsImagesAutomatically(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);



        ImageButton floatingButton = findViewById(R.id.floatingActionButton);
        final LinearLayout buttonsLayout = findViewById(R.id.buttonsLayout);
        FrameLayout mainLayout = findViewById(R.id.mainLayout);
        WebView webView = findViewById(R.id.webView);


        webView.setOnTouchListener((v, event) -> {
            if (buttonsLayout.getVisibility() == View.VISIBLE) {
                buttonsLayout.setVisibility(View.INVISIBLE);
                buttonsVisible = false;
            }
            return false;
        });

        mainLayout.setOnClickListener(v -> {
            if (buttonsLayout.getVisibility() == View.VISIBLE) {
                buttonsLayout.setVisibility(View.INVISIBLE);
                buttonsVisible = false;
            }
        });
        floatingButton.setOnClickListener(v -> {
            if (buttonsVisible) {
                buttonsLayout.setVisibility(View.INVISIBLE);
            } else {
                buttonsLayout.setVisibility(View.VISIBLE);
            }
            buttonsVisible = !buttonsVisible;
        });

        ImageButton button1 = findViewById(R.id.button1);
        ImageButton button2 = findViewById(R.id.button2);
        ImageButton button3 = findViewById(R.id.button3);
        ImageButton button4 = findViewById(R.id.button4);

        button1.setOnClickListener(v -> {
            String url = "https://m-miclaro.claro.com.py/OTk0MzUxMzUz/home";
            webView.loadUrl(url);
            if (buttonsLayout.getVisibility() == View.VISIBLE) {
                buttonsLayout.setVisibility(View.INVISIBLE);
                buttonsVisible = false;
            }
        });
        button2.setOnClickListener(v -> {
            String url = "https://simple.claro.com.py/inicio/";
            webView.loadUrl(url);
            if (buttonsLayout.getVisibility() == View.VISIBLE) {
                buttonsLayout.setVisibility(View.INVISIBLE);
                buttonsVisible = false;
            }

        });


        button3.setOnClickListener(v -> {
            String url = "https://tienda.claro.com.py/";
            webView.loadUrl(url);
            if (buttonsLayout.getVisibility() == View.VISIBLE) {
                buttonsLayout.setVisibility(View.INVISIBLE);
                buttonsVisible = false;
            }
        });
        button4.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, me.class);
                startActivity(intent);
            if (buttonsLayout.getVisibility() == View.VISIBLE) {
                buttonsLayout.setVisibility(View.INVISIBLE);
                buttonsVisible = false;
            }

        });






        webView.loadUrl("https://m-miclaro.claro.com.py/OTk0MzUxMzUz/home");

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
         if (webView.canGoBack()){
             webView.goBack();}
        else {
            super.onBackPressed();
        }
    }


    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ProgressBar loadingProgressBar = findViewById(R.id.loadingProgressBar);
            loadingProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            findViewById(R.id.webView).setVisibility(View.VISIBLE);
            ProgressBar loadingProgressBar = findViewById(R.id.loadingProgressBar);
            loadingProgressBar.setVisibility(View.GONE);

            if (url.equals("https://m-miclaro.claro.com.py/")) {
                String mainServiceWorkerUrl = "https://m-miclaro.claro.com.py/service-worker.js";
                view.evaluateJavascript("navigator.serviceWorker.register('" + mainServiceWorkerUrl + "');", null);
            }

            if (url.equals("https://simple.claro.com.py/inicio/")) {
                String secondaryServiceWorkerUrl = "https://simple.claro.com.py/inicio/assets/scripts/Packs.js";
                view.evaluateJavascript("navigator.serviceWorker.register('" + secondaryServiceWorkerUrl + "');", null);
            }
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.contains("https://m-miclaro.claro.com.py/factura/")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }




    }





    }













