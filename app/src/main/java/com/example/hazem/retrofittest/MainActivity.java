package com.example.hazem.retrofittest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText nameET;
    private EditText emailET;
    private EditText passwordET;
    private Button sinUpBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameET = (EditText) findViewById(R.id.nameEt);
        emailET = (EditText) findViewById(R.id.emailEt);
        passwordET = (EditText) findViewById(R.id.passwordEt);
        sinUpBTN = (Button) findViewById(R.id.signUpBTN);

        sinUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameET.getText().toString().isEmpty() && !emailET.getText().toString().isEmpty() && !passwordET.getText().toString().isEmpty()){
                    registerUser(new RegisterRequest(nameET.getText().toString(),emailET.getText().toString(),passwordET.getText().toString()));
                }else
                    Toast.makeText(getApplicationContext(),"Filed are empty",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void registerUser(RegisterRequest registerRequest) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing Up");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final AlertDialog alartDialog = new AlertDialog.Builder(this).create();
        alartDialog.setTitle("Signing Up");
        alartDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });

        Call<RegisterNewUser> registerNewCall = new RestClient().getApi_service().RegisterUser(registerRequest);
        registerNewCall.enqueue(new Callback<RegisterNewUser>() {
            @Override
            public void onResponse(Call<RegisterNewUser> call, Response<RegisterNewUser> response) {
                Log.d("result","2");
//                Log.d("result",response.body().getMessage());
                if (response.body() != null) {
                   if (response.body().getError()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                   }
               }
            }
            @Override
            public void onFailure(Call<RegisterNewUser> call, Throwable t) {
                progressDialog.setMessage("Request Failed");
                progressDialog.show();
            }
        });


    }

    public class RegisterRequest {

        private String name;
        private String emailAddress;
        private String password;

        public RegisterRequest(String name,String emailAddress, String password) {
            this.name = name ;
            this.emailAddress = emailAddress;
            this.password = password;
        }
    }
}
