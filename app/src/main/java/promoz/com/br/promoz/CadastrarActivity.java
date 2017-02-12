package promoz.com.br.promoz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;
import promoz.com.br.promoz.dao.UserDAO;
import promoz.com.br.promoz.model.User;
import promoz.com.br.promoz.util.ImageUtil;

public class CadastrarActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 23;
    private int SELECT_IMAGE = 1;
    Bitmap bitmap=null;
    User user = null;
    private CadastrarActivity.UserLoginTask mAuthTask = null;
    TextView viewName;
    TextView viewEmail;
    TextView viewCPF;
    TextView viewPassword;
    TextView viewConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        //TextView mWhyCPF = (TextView) findViewById(R.id.why_cpf);
        //mWhyCPF.setMovementMethod(LinkMovementMethod.getInstance());

        viewName = (TextView) findViewById(R.id.user_name);
        viewEmail = (TextView) findViewById(R.id.email);
        viewCPF = (TextView) findViewById(R.id.user_cpf);
        viewPassword = (TextView) findViewById(R.id.password);
        viewConfirm = (TextView) findViewById(R.id.password_again);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.change_photo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleyView();
            }
        });
    }

    public void galleyView(){
        if(isReadStorageAllowed()){
          //  Log.v("PERM","Já possuo");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
        }else {
          //  Log.v("PERM","Não possuo");
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
                        bitmap = ImageUtil.reSizeImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
                        CircleImageView perfilPhoto = (CircleImageView) findViewById(R.id.choose_photo);
                        perfilPhoto.setImageBitmap(bitmap);
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

/*public Bitmap reSizeImage(Bitmap bt){
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
}*/

    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
         //   Log.v("PERM","EXPLIQUEI");
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
     //   Log.v("PERM","PEDI");
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
         //   Log.v("PERM",grantResults[0]+"");
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


    public void cadastrar (View v) {
        attemptLogin();
    }

    private boolean isPasswordValid(String password) {
        //TODO: isPasswordValid
        //return password.length() > 2;
        return true;
    }

    private boolean isEmailValid(String email) {
        //TODO: isEmailValid
        return true;
        //return email.contains("@");
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Store values at the time of the login attempt.
        //  String email = mEmailView.getText().toString();

        String name = viewName.getText().toString();
        String email = viewEmail.getText().toString();
        String password = viewPassword.getText().toString();
        String confirm = viewConfirm.getText().toString();

     //   Log.e("CAD",name + ", " + email + ", " + password + ", " + confirm);

        // Reset errors.
        viewEmail.setError(null);
        viewPassword.setError(null);
        viewConfirm.setError(null);
        viewName.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password) || !password.equals(confirm)) {
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password) && !password.equals(confirm)) {
          //  Log.e("PASS","PASS INVALIDO");
            viewPassword.setError(getString(R.string.error_invalid_password));
            focusView = viewPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
         //   Log.e("EMAIL","EMAIL VAZIO");
            viewEmail.setError(getString(R.string.error_field_required));
            focusView = viewEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
        //    Log.e("EMAIL","EMAIL INVALIDO");
            viewEmail.setError(getString(R.string.error_invalid_email));
            focusView = viewEmail;
            cancel = true;
        }

        // Check for a valid user name.
        if (TextUtils.isEmpty(name)) {
          //  Log.e("NOME","NOME VAZIO");
            viewName.setError(getString(R.string.error_field_required));
            focusView = viewName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new CadastrarActivity.UserLoginTask(name, email, password);
            mAuthTask.execute((Void) null);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mName;
        private final String mCPF;
        private User authUser;

        UserLoginTask(String name, String email, String password) {
            mName = name;
            mEmail = email;
            mPassword = password;
            mCPF = viewCPF.getText().toString();
        }

        /**
         * Verificar se usuário já existe
         * @return true or false
         */
        private Boolean authUser(){

            UserDAO userDAO = new UserDAO(getApplicationContext());
            User result = userDAO.findUserByLogin(mEmail);
            Boolean sucess;

            if (result != null) {
              //  if(result.getNome() != null && result.getNome().equals(mName)) {
//                    authUser = result;
//                    sucess = true;
//                } else {
                    sucess = false;
              //  }
            } else {
                createUser(userDAO);
                sucess = true;
            }

            //userDAO.closeDatabase();
            return sucess;
        }

        /**
         * Método para criar um novo usuário
         * @param userDAO
         */
        private void createUser(UserDAO userDAO) {

            user = new User();
            authUser = new User();

            if(bitmap != null)
                authUser.setImg(ImageUtil.getThumbNail(bitmap));

            authUser.setEmail(mEmail);
            authUser.setNome(mName);
            authUser.setCpf(mCPF);
            authUser.setPassword(mPassword);
            Long id = userDAO.save(authUser);
            authUser.set_id(id.intValue());
        }

        /**
         * Login automático
         */
        private void setSharedPreferences(){
            SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE).edit();
            editor.putInt(User.getChave_ID(), authUser.get_id());
            editor.commit();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }

            return authUser();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                setSharedPreferences();
                finish();
            } else {
                viewEmail.setError("Usuário já cadastrado");
                viewEmail.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}
