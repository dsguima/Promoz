package promoz.com.br.promoz;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import promoz.com.br.promoz.dao.UserDAO;
import promoz.com.br.promoz.model.User;
import promoz.com.br.promoz.util.ImageUtil;

public class PerfilActivity extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 23;
    private int SELECT_IMAGE = 1;
    final Context context = this;
  //  private Button button;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_perfil);

        userId = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE).getInt(User.getChave_ID(),1);

        //Change picture

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.change_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleyView();
            }
        });
        final CircleImageView ci = (CircleImageView)findViewById(R.id.perfil_foto);

        UserDAO userDAO = new UserDAO(this);
        User user = userDAO.userById(userId);
        userDAO.closeDataBase();

        if(user != null) {
            byte[] bitmapdata = user.getImg();
            if (bitmapdata != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                if (bitmap != null)
                    ci.setImageBitmap(bitmap);
            }
        }else{
            Log.e("IMG", "Não tem imagem");
        }

        TextView email = (TextView) findViewById(R.id.email);
        if(user != null) {
            email.setText(user.getEmail());

            TextView name = (TextView) findViewById(R.id.nome);
            name.setText(user.getNome());
        }


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
                        userDao.closeDataBase();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void galleyView(){
        if(isReadStorageAllowed()){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
        }else {
            Log.v("PERM","Não possuo");
            requestStoragePermission();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                        Log.e("IMAGEM","TAMANHO IMAGEM >> " + bitmap.getHeight() +" x " + bitmap.getWidth());

                        Integer imgSize = bitmap.getWidth()*bitmap.getHeight();

                        if(bitmap.getHeight()>4000 || bitmap.getWidth()>4000){
                        //if(imgSize > 2048000){
                            promoz.com.br.promoz.util.Message.msgInfo(this,"Imagem muito grande","Por favor escolher uma imagem menor que 2MB"  ,android.R.drawable.ic_dialog_info);
                        } else {
                            CircleImageView perfilPhoto = (CircleImageView) findViewById(R.id.perfil_foto);
                            perfilPhoto.setImageBitmap(reSizeImage(bitmap));


                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                            byte[] foto = stream.toByteArray();

                            UserDAO userDAO = new UserDAO(this);
                            User user = userDAO.userById(userId);
                            user.setImg(foto);
                            userDAO.save(user);
                            userDAO.closeDataBase();
                            Log.e("IMAGEM","POPULOU IMAGEM >> " + bitmap.getHeight());
                            //TODO A foto ta  aqui
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Bitmap reSizeImage(Bitmap bt){
        float largura = (float)bt.getWidth();
        float altura = (float) bt.getHeight();

        if(altura > largura){
            float altProp = ((altura/largura)*100);
            Log.d("A_SIZE",altProp + "");
            Bitmap.createScaledBitmap(bt, Math.round(altProp), 100, true);

        }
        if(largura > altura){
            float largProp = ((largura/altura)*100);
            Log.d("L_SIZE",largProp + "");
            Bitmap.createScaledBitmap(bt, 100, Math.round(largProp), true);

        }
        if(largura == altura){

            Bitmap.createScaledBitmap(bt, 100, 100, true);

        }
        return bt;
    }

    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
            Log.v("PERM","EXPLIQUEI");
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        Log.v("PERM","PEDI");
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            Log.v("PERM",grantResults[0]+"");
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                galleyView();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

}
