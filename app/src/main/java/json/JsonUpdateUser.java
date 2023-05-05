package json;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import models.user.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JsonUpdateUser extends AsyncTask<User,Void,String> {
    @Override
    protected String doInBackground(User... users) {
        User user = users[0];
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("email",user.getEmail());
            jsonBody.put("phone",user.getPhone());
            jsonBody.put("address", user.getAddress());

            RequestBody body = RequestBody.create(mediaType, jsonBody.toString());

            Request request = new Request.Builder()
                    .url("https://dummyjson.com/users/"+user.getId())
                    .put(body) // or .patch(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Log.d("SUCCESSFUL RESPOND", responseBody);
            } else {
                Log.d("UNSUCCESSFUL RESPONSE CODE",String.valueOf(response.code()) );
            }
        }catch (Exception e){
            Log.d("EXCEPTION", e.getMessage());
        }

        return null;
    }
}
