package com.example.doubledruids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.doubledruids.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mAuth = FirebaseAuth.getInstance();

        final EditText emailEditText= findViewById(R.id.emailEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(emailEditText.getText().toString(),passwordEditText.getText().toString());
            }
        });

    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("Success", "signInWithEmail:success");
                            openMainScreen();
                            //openHomeScreen();
                        }
                        else{
                            Log.d("Failure", "signInWithEmail:Failure",task.getException());
                            Toast.makeText(loginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void openMainScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void openHomeScreen() {
        FirebaseUser user = mAuth.getCurrentUser();
        final String key=user.getUid();

        final DatabaseReference athleteDatabase = FirebaseDatabase.getInstance().getReference("Athletes");

        athleteDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot athleteSnapshot : dataSnapshot.getChildren()) {
                    User temp = athleteSnapshot.getValue(User.class);
                    Log.d("USER UNIQUE ID", temp.getUserUniqueID());
                    Log.d("KEY", key);
                    if (temp.getUserUniqueID().equalsIgnoreCase(key)) {
                        Intent intent = new Intent(loginActivity.this, MainActivity.class);
                        //intent.putExtra("athlete", temp);
                        Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



    }
}
