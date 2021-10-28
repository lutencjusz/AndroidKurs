package com.example.kursapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;
import com.example.kursapplication.api.LoginResponse;
import com.example.kursapplication.api.PodcastApi;
import java.io.IOException;
import java.lang.annotation.Annotation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edMail)
    EditText edMail;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edPassword)
    EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
            login(email, password);
        }

    }

    private void login(String email, String password) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://parseapi.back4app.com/");
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);
        Retrofit retrofit = builder.build();
        PodcastApi podcastApi = retrofit.create(PodcastApi.class);
        Call<LoginResponse> call = podcastApi.getLogin(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse body = response.body();
                    Log.d(LoginActivity.class.getSimpleName(), "Odpowiedź: " + body);
                    Toast.makeText(LoginActivity.this, "Udało Ci się zalogować!!!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        ResponseBody responseBody = response.errorBody();
                        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                        ErrorResponse errorResponse = converter.convert(responseBody);
                        Toast.makeText(LoginActivity.this, "Błąd: " + errorResponse.error, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

}