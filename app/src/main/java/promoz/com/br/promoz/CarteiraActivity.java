package promoz.com.br.promoz;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import promoz.com.br.promoz.dao.CouponDAO;
import promoz.com.br.promoz.model.Coupon;
import promoz.com.br.promoz.util.Message;

public class CarteiraActivity extends AppCompatActivity {;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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

    public void showInfo(View view){

        CouponDAO cpnDAO = new CouponDAO(getApplicationContext());
        Coupon cpn = cpnDAO.couponById((Integer) view.getTag());

        if(cpn.get_id() != -1)
            Message.msgInfo(this,cpn.getTitle(),cpn.getInfo(),android.R.drawable.ic_dialog_info);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}