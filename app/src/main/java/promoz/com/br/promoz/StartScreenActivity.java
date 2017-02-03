package promoz.com.br.promoz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLogged();

        setContentView(R.layout.activity_start_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        SpannableString ss = new SpannableString("Ao me cadastrar, eu concordo com os Termos de Serviço e Termos de Política de Privacidade do PromoZ.");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Uri uri = Uri.parse("https://www.google.com/intl/pt-BR/policies/terms/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

     //   Log.d("oi","ONCLICK");
        ss.setSpan(clickableSpan, 36, 89, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
     //   Log.d("oi","serSPAN");
        TextView trms = (TextView) findViewById(R.id.text_bellow);
     //   Log.d("oi","TV");
        trms.setText(ss);
     //   Log.d("oi","setText");
        trms.setMovementMethod(LinkMovementMethod.getInstance());
     //   Log.d("oi","set LINK");
        trms.setHighlightColor(Color.TRANSPARENT);

        TextView promoz = (TextView) findViewById(R.id.promoztv);
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/ITCKRIST.TTF");
     //   Log.d("oi","custom font ok");
        promoz.setTypeface(customFont);
     //   Log.d("oi","set face ok");
     //   Log.d("oi","layout");
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
}