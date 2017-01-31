package promoz.com.br.promoz;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import promoz.com.br.promoz.dao.WalletDAO;
import promoz.com.br.promoz.model.Wallet;

public class CarteiraActivity extends AppCompatActivity {;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        //Back button on header
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CarteiraFragmentPagerAdapter(getSupportFragmentManager(),
                CarteiraActivity.this,2,new String[]{"SALDO","CUPOM"}));
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

   /* public void startSaldo(){
        ViewPager viewPager2 = (ViewPager) findViewById(R.id.viewpager2);
        viewPager2.setAdapter(new SaldoCarteiraFragmentPagerAdapter(getSupportFragmentManager(),
                CarteiraActivity.this,3,new String[]{"7 DIAS","15 DIAS","30 DIAS"}));
        TabLayout tabLayout2 = (TabLayout) findViewById(R.id.his_tabs);
          tabLayout2.setupWithViewPager(viewPager2);
    }*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}