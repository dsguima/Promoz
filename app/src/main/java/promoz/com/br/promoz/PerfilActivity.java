package promoz.com.br.promoz;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import promoz.com.br.promoz.dao.UserDAO;
import promoz.com.br.promoz.model.User;

public class PerfilActivity extends AppCompatActivity {

    final Context context = this;
  //  private Button button;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_perfil);

        userId = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE).getInt(User.getChave_ID(),1);

        final CircleImageView ci= (CircleImageView)findViewById(R.id.foto);
        //TODO implementar colocar foto do usuário no Perfil  EX: "ci.setImageResource(R.drawable.scarletmenor);"
        Button button_logout = (Button) findViewById(R.id.logoutbt);
        Button button_change = (Button) findViewById(R.id.change_pass);
        // add button listener
        button_logout.setOnClickListener(new View.OnClickListener() {

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
                        SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE).edit();
                        editor.putInt(User.getChave_ID(), 0);
                        editor.commit();
                        dialog.dismiss();
                        finish();
                    }
                });

                dialog.show();
            }
        });


        button_change.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.change_pass_dialog);


                dialog.setTitle("Trocar senha");
                dialog.show();
                Button btconfirm = (Button) dialog.findViewById(R.id.confirm_change);
                Button btcancel = (Button) dialog.findViewById(R.id.cancel_change);

                btcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO:IMPLEMENTAR TROCAR SENHAR
                        UserDAO userDao = new UserDAO(context);
                        User user = userDao.userById(userId);

                        EditText lastpass = (EditText) dialog.findViewById(R.id.last_pass);
                        EditText newpass = (EditText) dialog.findViewById(R.id.new_pass);
                        EditText confirmpass = (EditText) dialog.findViewById(R.id.new_pass_confirm);

                        String newPass = newpass.getText().toString();
                        if(user.getPassword().equals(lastpass.getText().toString())){
                            if(newPass.equals(confirmpass.getText().toString())){
                               // Log.e("SENHA",newPass + " == " + lastpass.getText().toString());
                                user.setPassword(newPass);
                                userDao.save(user);
                                Toast.makeText(getApplicationContext(),R.string.senhaAtualizada,Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),R.string.camposNovaSenha,Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),R.string.senhaAtualErrada,Toast.LENGTH_LONG).show();
                        }
                        userDao.closeDatabase();
                        dialog.dismiss();
                        //finish();
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
