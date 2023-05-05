package com.example.demoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import json.JsonDeleteUser;
import models.user.User;


public class UserViewActivity extends AppCompatActivity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_user_view);

      ImageView userImageView = findViewById(R.id.userImageView);
      ImageView userRemoveView = findViewById(R.id.removeUserImageView);
      TextView userNameTextView = findViewById(R.id.userNameView);
      TextView userEmailTextView = findViewById(R.id.userEmailView);
      TextView userPhoneNumberTextView = findViewById(R.id.userPhoneNumberView);
      TextView userCompanyTextView = findViewById(R.id.userCompanyTextView);
      TextView userAddressTextView = findViewById(R.id.userAddressTextView);
      TextView userUpdateTextView = findViewById(R.id.updateUserTextView);

      User user =  (User) getIntent().getSerializableExtra("USER_KEY");

      Picasso.get().load(user.getImage()).into(userImageView);
      userNameTextView.setText(user.getFirstName() +" "+user.getLastName());
      userEmailTextView.setText(user.getEmail());
      userPhoneNumberTextView.setText(user.getPhone());
      userCompanyTextView.setText(user.getCompany().getName());
      userAddressTextView.setText(user.getAddress().getAddress());

      userRemoveView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            JsonDeleteUser jsonDeleteUser = new JsonDeleteUser();
            jsonDeleteUser.execute(user);
            finish();
         }
      });

      userUpdateTextView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),UpdateUserActivity.class);
            intent.putExtra("USER_KEY",user);
            startActivity(intent);
            finish();
         }
      });

   }
}
