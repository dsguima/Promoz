package promoz.com.br.promoz;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilActivity extends AppCompatActivity {

    final Context context = this;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_perfil);
        Button button = (Button) findViewById(R.id.logoutbt);

        // add button listener
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.logout_dialog);
                dialog.setTitle("Log Out");
                dialog.show();
                Button btY = (Button) dialog.findViewById(R.id.yes);
                Button btN = (Button) dialog.findViewById(R.id.no);
                // if button is clicked, close the custom dialog
                btN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO:IMPLEMENTAR LOGOUT PELO BANCO DE DADOS AQUI
                        dialog.dismiss();
                        Intent i = new Intent(context,StartScreenActivity.class);
                        context.startActivity(i);
                    }
                });

                dialog.show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
