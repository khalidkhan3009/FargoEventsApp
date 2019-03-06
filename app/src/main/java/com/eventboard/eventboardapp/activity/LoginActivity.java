package com.eventboard.eventboardapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eventboard.eventboardapp.R;
import com.eventboard.eventboardapp.pojo.Token;
import com.eventboard.eventboardapp.utils.Constant;
import com.eventboard.eventboardapp.utils.DataPreference;
import com.eventboard.eventboardapp.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText edit_usrname, edit_pass;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_usrname = (EditText) findViewById(R.id.edittext_username);
        edit_pass = (EditText) findViewById(R.id.edittext_password);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btn_login){
            login();
        }
    }

    private void login() {
        Utils.initLoading(this);

        String usrname = edit_usrname.getText().toString().trim();
        String pass = edit_pass.getText().toString().trim();

        usrname = usrname.toLowerCase();

        if(TextUtils.isEmpty(usrname)){
            Utils.showToast(this, "Please enter Username");
        }else if(TextUtils.isEmpty(pass)){
            Utils.showToast(this, "Please enter password");
        }else {
            Utils.showLoading("Logging in.....");

            /*Create handle for the RetrofitInstance interface*/
          //  APIEndpoints service = EventsAPI.getRetrofitInstance().create(APIEndpoints.class);

            Map<String, String> params = new HashMap<String, String>();
            params.put("Username", usrname);
            params.put("Password", pass);
            Call<Token> call = service.getToken(params);

            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    Utils.hideLoading();
                    Token token = response.body();
                    Log.d(TAG, token.getToken());
                    DataPreference.setPref(context, Constant.LOGIN_TOKEN, token.getToken());
                    DataPreference.setPref(context, Constant.LOGIN_FLAG, true);
                    startActivity(new Intent(LoginActivity.this, EventsActivity.class));
                    finish();
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Utils.hideLoading();
                    Utils.showToast(LoginActivity.this, "Something went wrong...Please try later!");
                }});

        }
    }
}
