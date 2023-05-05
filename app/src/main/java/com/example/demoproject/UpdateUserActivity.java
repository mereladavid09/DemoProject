package com.example.demoproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import json.JsonUpdateUser;
import models.user.User;

public class UpdateUserActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.update_user_layout);

      ImageView userImageView = findViewById(R.id.userImageView);
      TextView updateUserTextView = findViewById(R.id.updateUserTextView);
      EditText emailEditText = findViewById(R.id.emailEditText);
      EditText phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
      EditText addressEditText = findViewById(R.id.addressEditText);

      User user =  (User) getIntent().getSerializableExtra("USER_KEY");

      emailEditText.setText(user.getEmail());
      phoneNumberEditText.setText(user.getPhone());
      addressEditText.setText(user.getAddress().getAddress());

      Picasso.get().load(user.getImage()).into(userImageView);

      updateUserTextView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            user.setEmail(emailEditText.getText().toString());
            user.setPhone(phoneNumberEditText.getText().toString());
            user.getAddress().setAddress(addressEditText.getText().toString());

            JsonUpdateUser jsonUpdateUser = new JsonUpdateUser();
            jsonUpdateUser.execute(user);
            finish();
         }
      });


   }
}
