package com.example.demoproject;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import json.JsonFetchUser;
import models.user.User;


public class CreateUserActivity extends AppCompatActivity {

   private EditText firstNameText;
   private EditText lastNameText;
   private EditText userNameText;
   private EditText emailText;
   private EditText phoneNumberText;
   private EditText ageText;
   private ImageView addUser;
   private RadioButton femaleButton;
   private RadioButton maleButton;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.create_user);

      firstNameText = findViewById(R.id.FirstName);
      lastNameText = findViewById(R.id.LastName);
      userNameText = findViewById(R.id.Username);
      emailText = findViewById(R.id.Email);
      phoneNumberText = findViewById(R.id.PhoneNumber);
      ageText = findViewById(R.id.Age);
      addUser = findViewById(R.id.add_user_image);
      femaleButton = findViewById(R.id.Female);
      maleButton = findViewById(R.id.Male);

      addUser.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            String firstName = firstNameText.getText().toString();
            String lastName = lastNameText.getText().toString();
            int age = Integer.parseInt(ageText.getText().toString());
            String gender = "female";
            String email = emailText.getText().toString();
            String phone = phoneNumberText.getText().toString();
            String username = userNameText.getText().toString();


            User newUser = new User(firstName,lastName,age,gender,email,phone,username);

            JsonFetchUser jsonFetchUser = new JsonFetchUser();
            jsonFetchUser.execute(newUser);
            finish();
         }
      });

      femaleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
               maleButton.setChecked(false);
            }else {
               maleButton.setChecked(true);
            }
         }
      });

      maleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
               femaleButton.setChecked(false);
            } else {
               femaleButton.setChecked(true);
            }

         }
      });

   }
}
