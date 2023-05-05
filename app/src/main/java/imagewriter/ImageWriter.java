package imagewriter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
public class ImageWriter extends AsyncTask<Context,Void,String> {
   private final Bitmap bitmap;

   public ImageWriter(Bitmap bitmap) {
      this.bitmap = bitmap;
   }

   @Override
   protected String doInBackground(Context... contexts) {
      Context context = contexts[0];

      if (Build.VERSION.SDK_INT >= 30){

         ContentValues values = new ContentValues();
         values.put(MediaStore.Images.Media.DISPLAY_NAME, "my_image.jpg");
         values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
         values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

         ContentResolver resolver = context.getContentResolver();
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

            MediaScannerConnection.scanFile(context, new String[]{imageFile.toString()}, null, null);
            Log.d("SUCCESSFUL IMAGE SAVE ","IMAGE SAVED SUCCESSFULLY" );
         } catch (Exception exception) {
            Log.d("UNSUCCESSFUL IMAGE SAVE ","IMAGE SAVED UNSUCCESSFULLY" );
            exception.printStackTrace();
         }
      }
      return null;
   }
}
