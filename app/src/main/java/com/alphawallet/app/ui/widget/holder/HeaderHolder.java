package com.alphawallet.app.ui.widget.holder;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alphawallet.app.R;
import com.alphawallet.app.entity.tokendata.TokenGroup;

public class HeaderHolder extends BinderViewHolder<TokenGroup> {
    public static final int VIEW_TYPE = 2022;

    private final TextView title;

    @Override
    public void bind(@Nullable TokenGroup data, @NonNull Bundle addition) {
        String headerText = groupToHeader(data);
        if (headerText.isEmpty()) {
            title.setVisibility(View.GONE); // Hide the TextView if the header text is empty
        } else {
            title.setVisibility(View.VISIBLE); // Show the TextView if there is header text
            title.setText(headerText);
        }
    }

    private String groupToHeader(TokenGroup data)
    {
        if (data == null) return ""; // Return empty if data is null
        
        switch (data)
        {
            case DEFI:
                return getString(R.string.defi_header);
            case GOVERNANCE:
                return getString(R.string.governance_header);
            case SPAM:
                return getString(R.string.spam_header);
            case ATTESTATION:
                return getString(R.string.attestations_header);
            default:
                return ""; // Return empty for any unhandled cases
        }
    }

    public HeaderHolder(int res_id, ViewGroup parent) {
        super(res_id, parent);
        title = findViewById(R.id.title);
    }
}
