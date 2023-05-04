package json;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import models.user.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JsonFetchUser extends AsyncTask<User,Void,String> {
   @Override
   protected String doInBackground(User... users) {
      User newUser = users[0];

      try {
         Gson gson = new Gson();
         String json = gson.toJson(newUser);

         // set up the request
         OkHttpClient client = new OkHttpClient();
         MediaType mediaType = MediaType.parse("application/json");
         RequestBody body = RequestBody.create(mediaType, json);
         Request request = new Request.Builder()
                 .url("https://dummyjson.com/users/add")
                 .post(body)
                 .addHeader("Content-Type", "application/json")
                 .build();

         // send the request and print the response
         Response response = client.newCall(request).execute();
         Log.d("RESPONSE ", "RESPONSE : "+response.body().string());
         return response.body().string();
      }catch (Exception e){
         Log.d("EXCEPTION ADDING NEW USER", e.getMessage());
      }
      return null;
   }
}
