package com.alphawallet.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alphawallet.app.R;

public class LargeTitleView extends FrameLayout {

    // Removed globalnetworkMainnet and globalnetworkIcon references
    // public final TextView globalnetworkMainnet;
    // public final ImageView globalnetworkIcon;
    public final TextView currentBalanceLabel;
    public final TextView title;
    public final TextView subtitle;

    public LargeTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.layout_large_title_view, this, true);

        // Removed the line initializing globalnetworkMainnet and globalnetworkIcon
        // globalnetworkMainnet = findViewById(R.id.globalnetwork_mainnet);
        // globalnetworkIcon = findViewById(R.id.globalnetwork_icon);
        currentBalanceLabel = findViewById(R.id.current_balance_label);
        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
    }
}
