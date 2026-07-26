package com.example.guia4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    public static final String EXTRA_FULL_NAME = "extra_full_name";
    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_PHONE = "extra_phone";
    public static final String EXTRA_BIRTH_DATE = "extra_birth_date";
    public static final String EXTRA_ADDRESS = "extra_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        setText(R.id.txtSummaryName, getIntent().getStringExtra(EXTRA_FULL_NAME));
        setText(R.id.txtSummaryEmail, getIntent().getStringExtra(EXTRA_EMAIL));
        setText(R.id.txtSummaryPhone, getIntent().getStringExtra(EXTRA_PHONE));
        setText(R.id.txtSummaryBirthDate, getIntent().getStringExtra(EXTRA_BIRTH_DATE));
        setText(R.id.txtSummaryAddress, getIntent().getStringExtra(EXTRA_ADDRESS));

        TextView btnHome = findViewById(R.id.btnHome);
        TextView btnNewProfile = findViewById(R.id.btnNewProfile);

        btnHome.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnNewProfile.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value == null ? "" : value);
    }
}
