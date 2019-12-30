package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText sign_in_email,sign_in_password;
    public Button sign_in_button;
    public TextView sign_up_text;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign in");
        mAuth = FirebaseAuth.getInstance();

        sign_in_email = findViewById(R.id.signInEmail);
        sign_in_password = findViewById(R.id.signInPassword);
        sign_in_button = findViewById(R.id.signInButton);
        sign_up_text = findViewById(R.id.signUpText);

        sign_in_button.setOnClickListener(this);
        sign_up_text.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInButton:
                userlogin();

            break;

            case R.id.signUpText:
                Intent intent= new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userlogin() {
        String email = sign_in_email.getText().toString().trim();
        String password = sign_in_password.getText().toString().trim();

        if(email.isEmpty())
        {
            sign_in_email.setError("Enter an email address");
            sign_in_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            sign_in_email.setError("Enter a valid email address");
            sign_in_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            sign_in_password.setError("Enter a password");
            sign_in_password.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            sign_in_password.setError("Minimum length of a password Should bl 6");
            sign_in_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),"Sign in unsuccessful",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }
}
