package com.alphawallet.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.alphawallet.app.ui.HomeActivity;
import com.alphawallet.app.WebViewActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start); // Ensure this XML file name matches your layout file

        // Change the status bar color
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xFFdaf9f8);// Replace with your color resource or color value

        // Set up the tvmWallet button
        Button tvmWalletButton = findViewById(R.id.tvmWallet);
        tvmWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Ensure the StartActivity is finished after starting HomeActivity
            }
        });

        // Uncomment this block if you want the EVM Wallet button to navigate to WebViewActivity
        
        Button evmWalletButton = findViewById(R.id.evmWallet);
        evmWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, WebViewActivity.class);
                startActivity(intent);
                finish(); 
            }
        });
        
    }
}
