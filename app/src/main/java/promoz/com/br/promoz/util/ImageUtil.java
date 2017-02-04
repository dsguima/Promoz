package promoz.com.br.promoz.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.io.ByteArrayOutputStream;

/**
 * Created by vallux on 04/02/17.
 */

public class ImageUtil {

    public static byte[] drawableToByteArray(Context context, int resourceId){

        try {
            Drawable drawable = ContextCompat.getDrawable(context,resourceId);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }
}
