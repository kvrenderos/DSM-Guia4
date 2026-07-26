package com.example.guia4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 201;

    private EditText edtFullName;
    private EditText edtEmail;
    private EditText edtPhone;
    private EditText edtBirthDate;
    private EditText edtAddress;
    private TextView txtCameraStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        edtAddress = findViewById(R.id.edtAddress);
        txtCameraStatus = findViewById(R.id.txtCameraStatus);
        TextView btnTakePhoto = findViewById(R.id.btnTakePhoto);
        TextView btnSave = findViewById(R.id.btnSave);

        btnTakePhoto.setOnClickListener(view -> requestCameraPermission());
        btnSave.setOnClickListener(view -> saveProfile());
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            showCameraGranted();
            return;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.camera_permission_title)
                    .setMessage(R.string.camera_permission_reason)
                    .setPositiveButton(R.string.accept, (dialog, which) -> launchCameraPermission())
                    .setNegativeButton(R.string.cancel, null)
                    .show();
        } else {
            launchCameraPermission();
        }
    }

    private void launchCameraPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA_PERMISSION
        );
    }

    private void saveProfile() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String birthDate = edtBirthDate.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        boolean valid = true;
        if (TextUtils.isEmpty(fullName)) {
            edtFullName.setError(getString(R.string.required_field));
            valid = false;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError(getString(R.string.invalid_email));
            valid = false;
        }
        if (!phone.matches("^[0-9]{8,15}$")) {
            edtPhone.setError(getString(R.string.invalid_phone));
            valid = false;
        }
        if (!isValidDate(birthDate)) {
            edtBirthDate.setError(getString(R.string.invalid_date));
            valid = false;
        }
        if (TextUtils.isEmpty(address)) {
            edtAddress.setError(getString(R.string.required_field));
            valid = false;
        }

        if (!valid) {
            Toast.makeText(this, R.string.correct_form_errors, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra(SummaryActivity.EXTRA_FULL_NAME, fullName);
        intent.putExtra(SummaryActivity.EXTRA_EMAIL, email);
        intent.putExtra(SummaryActivity.EXTRA_PHONE, phone);
        intent.putExtra(SummaryActivity.EXTRA_BIRTH_DATE, birthDate);
        intent.putExtra(SummaryActivity.EXTRA_ADDRESS, address);
        startActivity(intent);
    }

    private boolean isValidDate(String value) {
        if (!value.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        format.setLenient(false);
        try {
            format.parse(value);
            return true;
        } catch (ParseException exception) {
            return false;
        }
    }

    private void showCameraGranted() {
        txtCameraStatus.setText(R.string.camera_permission_granted);
        txtCameraStatus.setBackgroundResource(R.drawable.status_success);
        Toast.makeText(this, R.string.camera_permission_granted, Toast.LENGTH_SHORT).show();
    }

    private void showCameraDenied() {
        txtCameraStatus.setText(R.string.camera_permission_denied);
        txtCameraStatus.setBackgroundResource(R.drawable.status_error);
        Toast.makeText(this, R.string.camera_permission_denied, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCameraGranted();
            } else {
                showCameraDenied();
            }
        }
    }
}
