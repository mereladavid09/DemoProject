package com.example.demoproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import json.JsonUpdateProduct;
import models.Product;


public class UpdateProductActivity extends AppCompatActivity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.update_product_layout);

      EditText priceEditText = findViewById(R.id.priceEditText);
      EditText stockEditText = findViewById(R.id.stockEditText);
      EditText descriptionEditText = findViewById(R.id.descriptionEditText);
      TextView updateTextView = findViewById(R.id.updateText);

      Product product = (Product) getIntent().getSerializableExtra("PRODUCT_KEY");

      priceEditText.setText(Double.toString(product.getPrice()));
      stockEditText.setText(Integer.toString(product.getStock()));
      descriptionEditText.setText(product.getDescription());

      updateTextView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            product.setPrice(Double.parseDouble(priceEditText.getText().toString()));
            product.setStock(Integer.parseInt(stockEditText.getText().toString()));
            product.setDescription(descriptionEditText.getText().toString());

            JsonUpdateProduct jsonUpdateProduct = new JsonUpdateProduct();
            jsonUpdateProduct.execute(product);
            finish();
         }
      });

   }
}
