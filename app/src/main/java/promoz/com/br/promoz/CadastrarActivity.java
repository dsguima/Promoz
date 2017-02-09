package promoz.com.br.promoz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import promoz.com.br.promoz.model.User;

public class CadastrarActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 23;
    private int SELECT_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        TextView mWhyCPF = (TextView) findViewById(R.id.why_cpf);
        mWhyCPF.setMovementMethod(LinkMovementMethod.getInstance());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.change_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleyView();
            }
        });



    }

    public void cadastrar (View v){
            //TODO Fazer o casdastro
    }


    public void galleyView(){
        if(isReadStorageAllowed()){
            Log.v("PERM","Já possuo");
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

                        if(bitmap.getHeight()>4000 || bitmap.getWidth()>4000){
                            promoz.com.br.promoz.util.Message.msgInfo(this,"Imagem muito grande","Por favor escolher uma imagem com resolução menor que 4000px"  ,android.R.drawable.ic_dialog_info);
                        }else{
                        CircleImageView perfilPhoto = (CircleImageView) findViewById(R.id.choose_photo);
                        perfilPhoto.setImageBitmap(reSizeImage(bitmap));
                        //TODO A foto ta  aqui
                      }
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }

public Bitmap reSizeImage(Bitmap bt){
        float largura = (float)bt.getWidth();
        Log.d("SIZE",largura + "");
        float altura = (float) bt.getHeight();
        Log.d("SIZE",altura + "");
    if(altura > largura){
        float altProp = ((largura/altura)*100);
        Log.d("SIZE",altProp + "");
        Bitmap.createScaledBitmap(bt, Math.round(altProp), 100, false);

    }
    if(largura > altura){
        float largProp = ((altura/largura)*100);
        Log.d("SIZE",largProp + "");
        Bitmap.createScaledBitmap(bt, 100, Math.round(largProp), false);

    }
    if(largura == altura){

        Bitmap.createScaledBitmap(bt, 100, 100, false);

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
