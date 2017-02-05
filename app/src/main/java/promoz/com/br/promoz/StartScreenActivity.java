package promoz.com.br.promoz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import promoz.com.br.promoz.model.User;
import promoz.com.br.promoz.util.Util;

import static promoz.com.br.promoz.R.string.terms;


public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLogged();

        setContentView(R.layout.activity_start_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setSpannableString(getString(terms), Util.Constants.URI_GOOGLE, (TextView) findViewById(R.id.text_bellow));

        Util.setFont(getAssets(), (TextView) findViewById(R.id.promoztv), Util.Constants.FONT_ITCKRIST);

    }

    @Override
    protected void onRestart() {
        checkLogged();
        super.onRestart();
    }

    private void checkLogged(){
        if(getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE).getInt(User.getChave_ID(),0) != 0)
            loadMain();
    }

    public void faceBookBt(View v){
        Context contexto = getApplicationContext();
        String texto = "Fucionalidade do facebook não criada";
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();
    }
    public void googleBt(View v){
        Context contexto = getApplicationContext();
        String texto = "Fucionalidade do google não criada";
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();
    }

    private void loadMain(){
        Intent intent = new Intent(this,ActMain.class);
        this.startActivity(intent);
    }

    public void cadastro(View v) {
        //loadMain();
    }
    public void logar(View v) {
        Intent intent = new Intent(this,LoginActivity.class);
        this.startActivity(intent);
    }

    public void setSpannableString(String span, final String link, TextView txtView){

        SpannableString ss = new SpannableString(span);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        ss.setSpan(clickableSpan, 36, 89, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtView.setText(ss);
        txtView.setMovementMethod(LinkMovementMethod.getInstance());
        txtView.setHighlightColor(Color.TRANSPARENT);
    }
}