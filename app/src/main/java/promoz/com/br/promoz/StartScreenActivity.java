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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import promoz.com.br.promoz.dao.UserDAO;
import promoz.com.br.promoz.dao.db.DatabaseHelper;
import promoz.com.br.promoz.model.User;


public class StartScreenActivity extends AppCompatActivity {

    private UserDAO userDAO;
    private Integer idUser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        userDAO = new UserDAO(this);
        List<User> usuario = userDAO.list();
        if(!usuario.isEmpty()){
            idUser = usuario.get(0).get_id();
            Log.v("ID USUARIO",idUser.toString());
        }
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

    public void cadastro(View v) {
        Intent intent = new Intent(this,ActMain.class);
        intent.putExtra(User.getChave_ID(),idUser);
        this.startActivity(intent);
    }
    public void logar(View v) {
        Context contexto = getApplicationContext();
        String texto = "Chamar classe login";
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();
        Intent i = new Intent(this,LoginActivity.class);
        this.startActivity(i);
    }
}