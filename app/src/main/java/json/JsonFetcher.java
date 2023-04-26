package json;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import models.Cart;
import models.Product;
import models.user.User;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class JsonFetcher extends AsyncTask<String, Void, List> {

   @Override
   protected List doInBackground(String... strings) {
      String url = strings[0];
      String type = strings[1];
      switch (type) {
         case "Products":
            String jsonProduct = fetchDataFromUrl(url);
            Log.d("FETCHING Products", jsonProduct);
            return parseProductsFromJson(jsonProduct);
         case "Users":
            String jsonUser = fetchDataFromUrl(url);
            Log.d("FETCHING USERS", jsonUser);
            return parseUsersFromJson(jsonUser);
         case "Carts":
            String jsonChart = fetchDataFromUrl(url);
            Log.d("FETCHING CHARTS", jsonChart);
            return parseCartsFromJson(jsonChart);

      }
      return null;
   }

   private String fetchDataFromUrl(String urlString) {
      String getJsonText = "";
      try {
         URL url = new URL(urlString);
         InputStream inputStream = url.openStream();

         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
         String line;

         while ((line = bufferedReader.readLine()) != null) {
            getJsonText += line + "\n";
         }
         inputStream.close();
         bufferedReader.close();
      } catch (Exception e) {
         Log.d("JSON PARSE ERROR", e.getMessage());
      }
      return getJsonText;
   }

   private List<Product> parseProductsFromJson(String json) {
      try {
         Gson gson = new Gson();
         JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

         JsonArray jsonArray = jsonObject.getAsJsonArray("products");
         
         Type productListType = new TypeToken<List<Product>>() {
         }.getType();
         List<Product> productList = gson.fromJson(jsonArray, productListType);
         return productList;
      } catch (JsonParseException e) {
         Log.d("JSON PARSE ERROR", e.getMessage());
      }
      return null;
   }

   private List<User> parseUsersFromJson(String json) {
      try {
         Gson gson = new Gson();
         JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

         JsonArray jsonArray = jsonObject.getAsJsonArray("users");

         Type productListType = new TypeToken<List<User>>() {
         }.getType();
         List<User> userList = gson.fromJson(jsonArray, productListType);
         return userList;
      } catch (JsonParseException e) {
         Log.d("JSON PARSE ERROR", e.getMessage());
      }
      return null;
   }

   private List<Cart> parseCartsFromJson(String json) {
      try {
         Gson gson = new Gson();
         JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

         JsonArray jsonArray = jsonObject.getAsJsonArray("carts");

         Type productListType = new TypeToken<List<Cart>>() {
         }.getType();
         List<Cart> cartList = gson.fromJson(jsonArray, productListType);
         return cartList;
      } catch (JsonParseException e) {
         Log.d("JSON PARSE ERROR", e.getMessage());
      }
      return null;
   }
}
