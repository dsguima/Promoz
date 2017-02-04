package promoz.com.br.promoz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import promoz.com.br.promoz.model.User;

public class ActMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActMain.this,CarteiraActivity.class);
                ActMain.this.startActivity(i);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void checkLogged(){
        if(getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE).getInt(User.getChave_ID(),0) == 0)
            finish();
    }

    @Override
    protected void onRestart() {
        checkLogged();
        super.onRestart();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            Intent i = new Intent(this,PerfilActivity.class);
            this.startActivity(i);

        } else if (id == R.id.nav_wallet) {
            Log.v("MENU","chama tela");
            Intent i = new Intent(this,CarteiraActivity.class);
            this.startActivity(i);

        } else if (id == R.id.nav_missions) {
            Context contexto = getApplicationContext();
            String texto = "MISSÔES";
            int duracao = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(contexto, texto,duracao);
            toast.show();

        } else if (id == R.id.nav_shop) {
            Log.v("MENU","chama Loja");
            Intent i = new Intent(this,LojaActivity.class);
            this.startActivity(i);

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
            String texto = "TERMOS";
            int duracao = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(contexto, texto,duracao);
            toast.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
