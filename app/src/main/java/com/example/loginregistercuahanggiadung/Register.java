package com.example.loginregistercuahanggiadung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregistercuahanggiadung.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    //tao doi tuong
    private EditText signupUsername, signupName, signupEmail, sigunpPhone, signupPassword;
    private TextView loginRedirectText;
    private Button signupButton;
    //tao doi tuong kiem tra
    private Pattern pattern;
    private Matcher matcher;
    //cau lenh kiem tra email
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";// cấu trúc 1 email
       private static final String PHONE_PATTERN = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";// cấu trúc 1 phone
    //tao doi tuong filebase
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //goi dui tuong theo id
        signupUsername = findViewById(R.id.signup_username);
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        sigunpPhone = findViewById(R.id.signup_phone);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        //su ly su kien tao tai khoan
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String username = signupUsername.getText().toString();
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String phone = sigunpPhone.getText().toString();
                String password = signupPassword.getText().toString();
                //kiem tra email co hop le hay khong
                if (!validateEmail(email)){
                    //thien lenh thong bao loi o truong email
                    signupEmail.setError("Email Invald");
                }
                //kiem tra phone co hop le hay khong
                else if (!validatePhone(phone)){
                    //thien lenh thong bao loi o truong phone
                    sigunpPhone.setError("Phone Invald");
                }else{
                    User helperClass = new User(username, name, email, phone, password);
                    reference.child(username).setValue(helperClass);

                    Toast.makeText(Register.this, "You have Register successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
            }
        });
        //su ly su kiem chuyen mang hinh qua mang hinh login
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

    // ham kiem tra email
    public boolean validateEmail(final String hex) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
    // ham kiem tra so dien thoai
    public boolean validatePhone(final String hex) {
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

}