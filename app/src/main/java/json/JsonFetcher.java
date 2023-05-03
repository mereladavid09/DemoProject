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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFetcher extends AsyncTask<String, Void, List> {

   @Override
   protected List doInBackground(String... strings) {
      String url = strings[0];
      String type = strings[1];
      String jsonString = fetchDataFromUrl(url);
      switch (type) {
         case "Products":
            Log.d("FETCHING Products", jsonString);
            return parseProductsFromJson(jsonString);
         case "Users":
            Log.d("FETCHING USERS", jsonString);
            return parseUsersFromJson(jsonString);
         case "Carts":
            Log.d("FETCHING CHARTS", jsonString);
            return parseCartsFromJson(jsonString);
         case "Categories":
            Log.d("FETCHING CATEGORIES", jsonString.replace("[","").replace("]",""));
            return parseCategoriesFromJson(jsonString);
         case "Electronics":
            List<Product> electronics = new ArrayList<>();
            String urlSmartphones = url + "smartphones";
            String urlLaptops = url + "laptops";
            String jsonSmartphonesString = fetchDataFromUrl(urlSmartphones);
            String jsonLaptopsString = fetchDataFromUrl(urlLaptops);

            electronics.addAll(parseProductsFromJson(jsonSmartphonesString));
            electronics.addAll(parseProductsFromJson(jsonLaptopsString));

            return electronics;

         case "Groceries":
            String urlGroceries = url +"groceries";
            return parseProductsFromJson(fetchDataFromUrl(urlGroceries));

         case "Beauty":
            List<Product> beauty = new ArrayList<>();
            String urlSkincare = url + "skincare";
            String urlFragrances = url + "fragrances";

            beauty.addAll(parseProductsFromJson(fetchDataFromUrl(urlSkincare)));
            beauty.addAll(parseProductsFromJson(fetchDataFromUrl(urlFragrances)));

            return beauty;

         case "Home":
            List<Product> home = new ArrayList<>();

            String urlDecorations = url + "home-decoration";
            String urlFurniture = url + "furniture";
            String urlLighting = url + "lighting";

            home.addAll(parseProductsFromJson(fetchDataFromUrl(urlDecorations)));
            home.addAll(parseProductsFromJson(fetchDataFromUrl(urlFurniture)));
            home.addAll(parseProductsFromJson(fetchDataFromUrl(urlLighting)));

            return home;

         case "Clothing":
            List<Product> clothing = new ArrayList<>();
            String[] clothingArray = new String []{"tops","womens-dresses","womens-shoes","mens-shirts","mens-shoes","mens-watches","womens-watches","womens-bags","womens-jewellery","sunglasses"};

            for (String category: clothingArray) {
               clothing.addAll(parseProductsFromJson(fetchDataFromUrl(url+category)));
            }

            return clothing;

         case "AutoMoto":
            List<Product> autoMoto = new ArrayList<>();
            String urlMotorcycle = url+"motorcycle";
            String urlAutomotive = url+"automotive";

            autoMoto.addAll(parseProductsFromJson(fetchDataFromUrl(urlMotorcycle)));
            autoMoto.addAll(parseProductsFromJson(fetchDataFromUrl(urlAutomotive)));

            return autoMoto;
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

   private List<String> parseCategoriesFromJson(String json){
      List<String> categories = new ArrayList<>();
      categories.addAll(Arrays.asList(json.split(",")));
      return categories;
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
