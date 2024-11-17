package com.alphawallet.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private WebView webView;
    private String url = "https://megapayershab.vercel.app/";
    private boolean isWebViewLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Log.d(TAG, "onCreate called");

        setWhiteStatusBar();

        webView = findViewById(R.id.webview);
        setupWebView();
        loadWebView();
        requestCameraPermission();
    }

    private void setWhiteStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    private void setupWebView() {
        Log.d(TAG, "Setting up WebView");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(TAG, "onPermissionRequest: " + request.getOrigin().toString());
                runOnUiThread(() -> {
                    if (ContextCompat.checkSelfPermission(WebViewActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        request.grant(request.getResources());
                    } else {
                        request.deny();
                    }
                });
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isWebViewLoaded = true;
                injectBackCameraPreference();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.e(TAG, "WebView error: " + error.getDescription());
                Toast.makeText(WebViewActivity.this, "WebView error: " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadWebView() {
        Log.d(TAG, "Loading URL: " + url);
        webView.loadUrl(url);
    }

    private void injectBackCameraPreference() {
        String script = "if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {" +
                "  navigator.mediaDevices.getUserMedia = (function(getUserMedia) {" +
                "    return function(constraints) {" +
                "      if (constraints && constraints.video && !constraints.video.facingMode) {" +
                "        constraints.video.facingMode = { ideal: 'environment' };" +
                "      }" +
                "      return getUserMedia.apply(this, arguments);" +
                "    };" +
                "  })(navigator.mediaDevices.getUserMedia);" +
                "}";
        webView.evaluateJavascript(script, null);
    }

    private void requestCameraPermission() {
        Log.d(TAG, "Requesting camera permission");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: requestCode=" + requestCode);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Camera permission granted");
                if (isWebViewLoaded) {
                    webView.reload();
                }
            } else {
                Log.e(TAG, "Camera permission denied");
                Toast.makeText(this, "Camera permission is required for QR scanning", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            Intent intent = new Intent(WebViewActivity.this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }
}
