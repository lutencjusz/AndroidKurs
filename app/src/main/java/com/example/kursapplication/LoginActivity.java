package com.example.kursapplication;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edMail)
    EditText edMail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edPassword)
    EditText edPassword;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        userManager = ((App) getApplication()).getUserManager(); // rzutowanie na naszą App w celu wykorzystania zapisanego tam userManagera
        Log.d(TAG, "Obecny UserManager" + userManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        userManager.onStop();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnRegister)
    public void registerClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnLogin)
    public void loginClick(View view) {
        boolean hasErr = false;
        String email = edMail.getText().toString();
        String password = edPassword.getText().toString();
        if (email.isEmpty()) {
            edMail.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edMail.setError(getString(R.string.err_is_not_email));
            hasErr = true;
        }
        if (password.isEmpty()) {
            edPassword.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        }
        if (!hasErr) {
            userManager.login(email, password);
        }

    }


    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
    }
}