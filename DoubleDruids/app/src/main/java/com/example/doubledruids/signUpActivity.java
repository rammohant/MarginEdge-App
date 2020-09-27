package com.example.doubledruids;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase;
import java.io.IOException;


public class signUpActivity extends AppCompatActivity  {
  //  implements AdapterView.OnItemSelectedListener
    //greg
    private FirebaseAuth mAuth;


    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth= FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_up);


        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final EditText emailEditText= findViewById(R.id.emailEditText);
        Button signUp=findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw=passwordEditText.getText().toString();
                String email=emailEditText.getText().toString();

                if(checkValidPassword(pw)) {
                    createAccount(email,pw);
                }
                else{
                    Toast.makeText(signUpActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
//            imageUri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                profilePhoto.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String accountType = parent.getItemAtPosition(position).toString();
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        String text = "You must select an account type.";
//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
//    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("Success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            DatabaseReference databaseAthletes = FirebaseDatabase.getInstance().getReference("Athletes");
                            EditText firstName=findViewById(R.id.firstNameEditText);
                            EditText lastName=findViewById(R.id.lastNameEditText);
                            EditText email=findViewById(R.id.emailEditText);
                            EditText pw = findViewById(R.id.passwordEditText);
                            User me=new User(firstName.getText().toString()+" "+lastName.getText().toString(), email.getText().toString(), pw.getText().toString(), user.getUid(), databaseAthletes.push().getKey());
                            databaseAthletes.child(me.getKey()).setValue(me);

                            //Intent intent=new Intent(signUpActivity.this, HomeActivity.class);
//                            intent.putExtra("User", user);
//                            intent.putExtra("user", me);
                            //startActivity(intent);
                        }
                        else{
                            Log.w("Failure", "createUserWithEmail:Failure",task.getException());
                            Toast.makeText(signUpActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //jasmin
//    public void createAccountCoach(String email, String password){
//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Log.d("Success", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            DatabaseReference databaseCoach = FirebaseDatabase.getInstance().getReference("Coach");
//                            EditText firstName=findViewById(R.id.firstNameEditText);
//                            EditText lastName=findViewById(R.id.lastNameEditText);
//                            EditText email=findViewById(R.id.emailEditText);
//                            EditText pw = findViewById(R.id.passwordEditText);
//                            Coach coa =new Coach(firstName.getText().toString()+" "+lastName.getText().toString(), email.getText().toString(), pw.getText().toString(), user.getUid(), databaseCoach.push().getKey());
//                            databaseCoach.child(coa.getKey()).setValue(coa);
//
//                            Intent intent=new Intent(SignUpScreen.this, CoachHomeActivity.class);
//                            intent.putExtra("User", user);
//                            intent.putExtra("coach", coa);
//                            startActivity(intent);
//                        }
//                        else{
//                            Log.w("Failure", "createUserWithEmail:Failure",task.getException());
//                            Toast.makeText(SignUpScreen.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    public boolean checkValidPassword(String s){
        if(s.length()<6){
            return false;
        }
        else{
            boolean letter=false;
            boolean number=false;
            for(int i=0;i<s.length();i++){
                int c=s.charAt(i);
                if(c>47&&c<58){
                    number=true;
                }
                else if(c>64&&c<91){
                    letter=true;
                }
                else if(c>96&&c<123){
                    letter=true;
                }
                else{
                    return false;
                }
            }
            if(letter&number)
                return true;
            else
                return false;
        }
    }


}