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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {



    public EditText sign_up_email,sign_up_password;
    public Button sign_up_button;
    public TextView sign_in_text;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign up");

        sign_up_email = findViewById(R.id.signUpEmail);
        sign_up_password = findViewById(R.id.signUpPassword);
        sign_up_button = findViewById(R.id.signUpButton);
        sign_in_text = findViewById(R.id.signInText);
        mAuth = FirebaseAuth.getInstance();

        sign_up_button.setOnClickListener(this);
        sign_in_text.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpButton:
                userRegister();
                break;

            case R.id.signInText:
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userRegister() {
        String email = sign_up_email.getText().toString().trim();
        String password = sign_up_password.getText().toString().trim();

        if(email.isEmpty())
        {
            sign_up_email.setError("Enter an email address");
            sign_up_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            sign_up_email.setError("Enter a valid email address");
            sign_up_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            sign_up_password.setError("Enter a password");
            sign_up_password.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            sign_up_password.setError("Minimum length of a password Should bl 6");
            sign_up_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Successful Register",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Unsuccessful Register",Toast.LENGTH_SHORT).show();
                        }
                    /*        if(task.getException()instanceof FirebaseAuthUserCollisionException)
                            {

                                Toast.makeText(getApplicationContext(),"already Register",Toast.LENGTH_SHORT).show();

                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(),"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
*/
                        // ...
                    }
                });







    }
}
