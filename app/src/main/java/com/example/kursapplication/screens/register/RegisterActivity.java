package com.example.kursapplication.screens.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.example.kursapplication.R;
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
    @BindView(R.id.edEmail)
    EditText edREmail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edPassword)
    EditText edRPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


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
            edFirstName.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        }
        if (lastName.isEmpty()) {
            edLastName.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        }
        if (password.isEmpty()) {
            edRPassword.setError(getString(R.string.err_empty_filed));
            hasErr = true;
        }
        if (!hasErr) {

        }
    }

    public void registerSuccessFull() {

    }
}