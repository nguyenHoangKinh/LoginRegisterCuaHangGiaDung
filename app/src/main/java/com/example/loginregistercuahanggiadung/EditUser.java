package com.example.loginregistercuahanggiadung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUser extends AppCompatActivity {
    //tao doi tuong
    EditText  editUsername, editName, editEmail, editPhone, editPassword;
    Button saveButton;
    String usernameUser, nameUser, emailUser, userPhone,  passwordUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        //goi filebase de lay doi tuong user
        reference = FirebaseDatabase.getInstance().getReference("users");

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);
        showData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isEmailChanged() || isPasswordChanged()) {
                    Toast.makeText(EditUser.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditUser.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isNameChanged(){
        if (!nameUser.equals(editName.getText().toString())){
            reference.child(usernameUser).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isEmailChanged(){
        if (!emailUser.equals(editName.getText().toString())){
            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else{
            return false;
        }
    }
    public boolean isPhonelChanged(){
        if (!emailUser.equals(editName.getText().toString())){
            reference.child(usernameUser).child("phone").setValue(editPhone.getText().toString());
            userPhone = editPhone.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isPasswordChanged(){
        if (!passwordUser.equals(editPassword.getText().toString())){
            reference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public void showData(){
        Intent intent = getIntent();

        usernameUser = intent.getStringExtra("username");
        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        userPhone = intent.getStringExtra("phone");
        passwordUser = intent.getStringExtra("password");

        editUsername.setText(usernameUser);
        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editPhone.setText(userPhone);
        editPassword.setText(passwordUser);
    }
}