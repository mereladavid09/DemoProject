package com.example.demoproject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import json.JsonDeleteProduct;
import models.Product;



public class ProductViewActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private ImageView productImageView;
    private GestureDetector gestureDetector;
    private int currentImageIndex;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        this.productImageView = findViewById(R.id.productImageView);
        TextView productTitleText = findViewById(R.id.productTitleText);
        TextView productDescriptionText = findViewById(R.id.descriptionText);
        TextView productRatingText = findViewById(R.id.ratingText);
        TextView productStockText = findViewById(R.id.stockText);
        TextView priceText = findViewById(R.id.priceText);
        ImageView updateImageView= findViewById(R.id.updateImageView);
        ImageView deleteImageView = findViewById(R.id.deleteImageView);

        this.gestureDetector = new GestureDetector(this, this);
        this.product = (Product) getIntent().getSerializableExtra("PRODUCT_KEY");


        currentImageIndex = 0;

        productTitleText.setText(product.getBrand() + " "+ product.getTitle());
        productDescriptionText.setText(product.getDescription());
        priceText.setText(product.getPrice()+"€");


        if (product.getStock() > 0){
            productStockText.setText(product.getStock() + " are still in stock ");
        }
        else {
            productStockText.setText("Out of stock");
        }

        productRatingText.setText(String.format("Rating : %.1f",product.getRating()));

        Picasso.get().load(product.getImages().get(0)).into(productImageView);

        updateImageView.setOnClickListener(view -> {
            Intent intent = new Intent(this,UpdateProductActivity.class);
            intent.putExtra("PRODUCT_KEY",product);
            startActivity(intent);
            Toast.makeText(view.getContext(), "UPDATE PRESS", Toast.LENGTH_SHORT).show();
        });

        deleteImageView.setOnClickListener(view -> {
            JsonDeleteProduct jsonDeleteProduct = new JsonDeleteProduct();
            jsonDeleteProduct.execute(product);
            Toast.makeText(view.getContext(), "DELETE PRESS", Toast.LENGTH_SHORT).show();
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() < e2.getX()) {
            // Swiped right, load previous image
            currentImageIndex--;
            if (currentImageIndex < 0) {
                currentImageIndex = product.getImages().size() - 1;
            }
        } else if (e1.getX() > e2.getX()) {
            // Swiped left, load next image
            currentImageIndex++;
            if (currentImageIndex >= product.getImages().size()) {
                currentImageIndex = 0;
            }
        }

        // Load the new image
        Picasso.get().load(product.getImages().get(currentImageIndex)).into(productImageView);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to save the image to your gallery?")
                .setTitle("Save Image")
                .setPositiveButton("Yes", (dialog, id) -> {

                    productImageView.setDrawingCacheEnabled(true);
                    productImageView.buildDrawingCache();
                    Bitmap bitmap = productImageView.getDrawingCache();


                    if (Build.VERSION.SDK_INT >= 30){

                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.DISPLAY_NAME, "my_image.jpg");
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

                        ContentResolver resolver = getApplicationContext().getContentResolver();
                        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                        try {
                            OutputStream os = resolver.openOutputStream(uri);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                            os.flush();
                            os.close();
                            Log.d("SUCCESSFUL IMAGE SAVE ","IMAGE SAVED SUCCESSFULLY" );
                        } catch (IOException exception) {
                            Log.d("UNSUCCESSFUL IMAGE SAVE ","IMAGE SAVED UNSUCCESSFULLY" );
                            exception.printStackTrace();
                        }
                    } else {

                        try {

                            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
                            if (!directory.exists()) {
                                directory.mkdirs();
                            }

                            File imageFile = new File(directory, "product_image");
                            FileOutputStream fos = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                            fos.flush();
                            fos.close();

                            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{imageFile.toString()}, null, null);
                            Log.d("SUCCESSFUL IMAGE SAVE ","IMAGE SAVED SUCCESSFULLY" );
                        } catch (Exception exception) {
                            Log.d("UNSUCCESSFUL IMAGE SAVE ","IMAGE SAVED UNSUCCESSFULLY" );
                            exception.printStackTrace();
                        }
                    }
                    productImageView.setDrawingCacheEnabled(false);
                    bitmap.recycle();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}