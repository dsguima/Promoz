package promoz.com.br.promoz.util;

import android.app.Activity;
import android.app.AlertDialog;

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

}
