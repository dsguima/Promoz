package promoz.com.br.promoz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import promoz.com.br.promoz.dao.HistoricCoinDAO;
import promoz.com.br.promoz.dao.UserDAO;
import promoz.com.br.promoz.dao.WalletDAO;
import promoz.com.br.promoz.model.HistoricCoin;
import promoz.com.br.promoz.model.User;
import promoz.com.br.promoz.util.DateUtil;

public class ActMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Integer userID=0;
    private CircleImageView foto,fotoclick;
    int backButtonCount = 0;
    int countDown = 10000;
    final Context context =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        backButtonCount = 0;
        Log.e("MENU", "BACK BT: "+backButtonCount);
        checkLogged();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        fotoclick =  (CircleImageView) hView.findViewById(R.id.foto_nav);
        fotoclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PerfilActivity.class);
                context.startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActMain.this,CarteiraActivity.class);
                intent.putExtra(User.getChave_ID(),userID);
                ActMain.this.startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                backButtonCount =0;
                Log.e("MENU", "Fechei");
                Log.e("MENU", "BACK BT: "+backButtonCount);
                super.onDrawerClosed(view);

            }
            public void onDrawerOpened(View drawerView) {
                backButtonCount =0;
                Log.e("MENU", "ABRI EIN");
                Log.e("MENU", "BACK BT: "+backButtonCount);
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setMenu();
    }

    public void onMoeda(View v){
        addCoin(1);
        Context contexto = getApplicationContext();
        String texto = "Ganhou uma moeda";
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();
    }

    public void onBau(View v){
     //    addCupom(1);
        Context contexto = getApplicationContext();
        String texto = "Ganhou um cupom";
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();
    }


    public void addCupom(Integer amountCoin){

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            backButtonCount =0;
            Log.e("MENU", "BACK BT: "+backButtonCount);
            drawer.closeDrawer(GravityCompat.START); // recolhe o menu caso esteja "aberto"
            Log.e("MENU", "RECOLHEU");
        } else {
            if (backButtonCount >= 1) {
                Log.e("MENU", "BACK BT: "+backButtonCount);
                moveTaskToBack(true);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Pressione o botão voltar novamente para sair do aplicativo", Toast.LENGTH_SHORT).show();
                backButtonCount++;
                Log.e("MENU", "BACK BT: "+backButtonCount);
                new CountDownTimer(countDown, 5000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        backButtonCount = 0;
                    }

                }.start();

            }
        }
    }



    private void checkLogged(){
        userID = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE).getInt(User.getChave_ID(),0);
        if(userID == 0)
            finish();
    }

    private void setMenu(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        foto =  (CircleImageView) hView.findViewById(R.id.foto_nav);

        UserDAO userDAO = new UserDAO(this);
        User user = userDAO.userById(userID);
        byte[] bitmapdata;
        if(user != null) {
            bitmapdata = user.getImg();
            TextView name = (TextView) hView.findViewById(R.id.navDrawerNome);
            name.setText(user.getNome());
            if(bitmapdata != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                if (bitmap != null)
                    foto.setImageBitmap(bitmap);
            }
        }
        userDAO.closeDataBase();
    }

    @Override
    protected void onRestart() {

        backButtonCount = 0;
        Log.e("MENU", "BACK BT: "+backButtonCount);
        checkLogged();
        super.onRestart();
        setMenu();
    }

    // TODO: Adicionar moedas apenas para protótipo
    private void addCoin(Integer amountCoin){
        WalletDAO wallet = new WalletDAO(this);
        Integer walletId = wallet.walletIdByUserId(userID);
        String date = new SimpleDateFormat(DateUtil.YYYYMMDD_HHmmss).format(new Date());
        HistoricCoin historicCoin = new HistoricCoin(walletId,1,date,amountCoin,"Ganhou Moeda");
        HistoricCoinDAO historicCoinDAO = new HistoricCoinDAO(this);
        historicCoinDAO.save(historicCoin);
        wallet.closeDataBase();
        historicCoinDAO.closeDataBase();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        backButtonCount = 0;
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Intent intent = new Intent(this,PerfilActivity.class);
            intent.putExtra(User.getChave_ID(),userID);
            this.startActivity(intent);

        } else if (id == R.id.nav_wallet) {
            Intent intent = new Intent(this,CarteiraActivity.class);
            intent.putExtra(User.getChave_ID(),userID);
            this.startActivity(intent);
        } else if (id == R.id.nav_missions) {
            Context contexto = getApplicationContext();
            String texto = "MISSÔES";
            int duracao = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(contexto, texto,duracao);
            toast.show();

        } else if (id == R.id.nav_shop) {
            Intent intent = new Intent(this,LojaActivity.class);
            intent.putExtra(User.getChave_ID(), userID);
            this.startActivity(intent);

        } else if (id == R.id.nav_config) {
            Context contexto = getApplicationContext();
            String texto = "CONFIGURAÇÕES";
            int duracao = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(contexto, texto,duracao);
            toast.show();

        } else if (id == R.id.nav_help) {
            Context contexto = getApplicationContext();
            String texto = "AJUDA";
            int duracao = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(contexto, texto,duracao);
            toast.show();

        } else if (id == R.id.nav_feedback) {
            Context contexto = getApplicationContext();
            String texto = "FEEDBACK";
            int duracao = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(contexto, texto,duracao);
            toast.show();

        } else if (id == R.id.nav_terms) {
            Context contexto = getApplicationContext();
            String texto = "Ganhou uma moeda";
            int duracao = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(contexto, texto,duracao);
            toast.show();
           // addCoin(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
