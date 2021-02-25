package com.example.tipcal2;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WebLookUp extends AppCompatActivity {
    public static final int requestCode_48 = 48;
    private WebView Web_URL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_screen);

        EditText WebURL = (EditText) findViewById(R.id.WebURL);
        WebURL.setHint("Enter URL");
        Web_URL = (WebView) findViewById(R.id.Web_URL);
        Web_URL.setWebViewClient(new WebViewClient());

        Button setWebsite = (Button) findViewById(R.id.setWebsite);
        setWebsite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String S_WebURL = WebURL.getText().toString(); //Get the website input from user
                Web_URL.getSettings().setJavaScriptEnabled(true);
                Web_URL.loadUrl(S_WebURL);
            }
        });

    }


    //Use "back" button to go back to the previous web page. When finishes, it leads back to the main Activity
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (Web_URL.canGoBack()) {
                    Web_URL.goBack();
                }
                else {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivityForResult(intent, requestCode_48);
                    finish();
                    Toast.makeText(this, "WebLookUp is finished. Back to the main Activity",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
