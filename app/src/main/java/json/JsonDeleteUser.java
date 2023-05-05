package json;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import models.user.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonDeleteUser extends AsyncTask<User,Void,String> {
   @Override
   protected String doInBackground(User... users) {
      User user = users[0];

      OkHttpClient client = new OkHttpClient();
      Request request = new Request.Builder()
              .url("https://dummyjson.com/products/"+user.getId())
              .delete()
              .build();

      client.newCall(request).enqueue(new Callback() {
         @Override
         public void onFailure(Call call, IOException e) {
            e.printStackTrace();
         }

         @Override
         public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
               String responseBody = response.body().string();
               Log.d("DELETE Response", "DELETE RESPONSE :"+ responseBody);
            } else {
               Log.e("DELETE Response", "Error: " + response.code() + " " + response.message());
            }
         }
      });
      return null;
   }
}
