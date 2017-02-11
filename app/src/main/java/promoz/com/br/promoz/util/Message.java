package promoz.com.br.promoz.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by vallux on 03/02/17.
 */

public class Message {


    public static void msgInfo(Activity activity, String title, String msg, int iconId){
        //public static void msgInfo(Activity activity, String title, String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("OK", null);
        alert.setIcon(iconId);
        alert.show();
    }

    public static void msgErrorDB(Context context, String tag, String error, Exception ex){
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }

}
