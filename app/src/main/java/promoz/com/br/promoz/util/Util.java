package promoz.com.br.promoz.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by dsguima on 04/02/17.
 */

public class Util extends AppCompatActivity {

    public static class Constants {

        public static String URI_GOOGLE = "https://www.google.com/intl/pt-BR/policies/terms/";
        public static String FONT_ITCKRIST = "fonts/ITCKRIST.TTF";
        public static String URI_LEMBRAR_SENHA = "https://goo.gl/fTc4Ae";

    }

    public static void setFont(AssetManager assets, TextView textView, String font){

        Typeface customFont = Typeface.createFromAsset(assets, font);
        textView.setTypeface(customFont);
    }



}
