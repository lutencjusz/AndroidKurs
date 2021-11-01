package com.example.kursapplication.screens.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kursapplication.App;
import com.example.kursapplication.MainActivity;
import com.example.kursapplication.R;
import com.google.android.material.textfield.TextInputLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edFirstName)
    EditText edFirstName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edLastName)
    EditText edLastName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edFirstNameTextInputLayout)
    TextInputLayout edFirstNameTextInputLayout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edLastNameTextInputLayout)
    TextInputLayout edLastNameTextInputLayout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edEmail)
    EditText edREmail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edPassword)
    EditText edRPassword;

    private RegisterManager registerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        registerManager = ((App) getApplication()).getRegisterManager();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerManager.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_register) {
            tryToRegister();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void tryToRegister() {
        boolean hasErr = false;
        String email = edREmail.getText().toString();
        String password = edRPassword.getText().toString();
        String firstName = edFirstName.getText().toString();
        String lastName = edLastName.getText().toString();
        if (email.isEmpty()) {
            edREmail.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edREmail.setError(getString(R.string.err_is_not_email));
            hasErr = true;
        }
        if (firstName.isEmpty()) {
            edFirstNameTextInputLayout.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        } else {
            edFirstNameTextInputLayout.setError(null);
        }
        if (lastName.isEmpty()) {
            edLastNameTextInputLayout.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        } else {
            edLastNameTextInputLayout.setError(null);
        }
        if (password.isEmpty()) {
            edRPassword.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        }
        if (!hasErr) {
            registerManager.register(firstName, lastName, email, password, ((App) getApplication()).getIdDB(), ((App) getApplication()).getKeyDB());
        }
    }

    public void registerSuccessFull() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    public void showError(String error) {
        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();

    }

    public void showProgress(boolean progress) {
//TODO: akcja w toolbarze
    }
}