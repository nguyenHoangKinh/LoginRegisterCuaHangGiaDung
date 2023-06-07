package com.example.loginregistercuahanggiadung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //tao doi tuong
    private TextView showUsername, showName, showEmail, showPhone,  showPassword;
    private TextView titleName;
    private Button editProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //goi dui tuong theo id
        showUsername = findViewById(R.id.userName);
        showName = findViewById(R.id.name);
        showEmail = findViewById(R.id.email);
        showPhone = findViewById(R.id.phone);
        showPassword = findViewById(R.id.password);
        titleName = findViewById(R.id.titleName);
        editProfile = findViewById(R.id.editButton);
        showUserData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });
    }
    public void showUserData(){

        Intent intent = getIntent();

        String usernameUser = intent.getStringExtra("username");
        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        String phonelUser = intent.getStringExtra("phone");
        String passwordUser = intent.getStringExtra("password");

        titleName.setText(nameUser);
        showUsername.setText(usernameUser);
        showName.setText(nameUser);
        showEmail.setText(emailUser);
        showUsername.setText(phonelUser);
        showPassword.setText(passwordUser);
    }

    public void passUserData() {
        String userUsername = showUsername.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                    String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                    String phonelFromDB = snapshot.child(userUsername).child("phone").getValue(String.class);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    Intent intent = new Intent(MainActivity.this, EditUser.class);

                    intent.putExtra("username", usernameFromDB);
                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("phone", phonelFromDB);
                    intent.putExtra("password", passwordFromDB);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}