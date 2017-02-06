package promoz.com.br.promoz;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import promoz.com.br.promoz.dao.CouponDAO;
import promoz.com.br.promoz.model.Coupon;
import promoz.com.br.promoz.model.User;

public class CarteiraActivity extends AppCompatActivity implements CarteiraPageFragment.OnGetUserID {
    private Integer userId;

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE).getInt(User.getChave_ID(),1);

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
            promoz.com.br.promoz.util.Message.msgInfo(this,cpn.getTitle(),cpn.getInfo(),android.R.drawable.ic_dialog_info);
    }

    public void useCupom(View view){ // TODO: rotina para utilização do cupom
        CouponDAO cpnDAO = new CouponDAO(getApplicationContext());
        Coupon cpn = cpnDAO.couponById((Integer) view.getTag());

        if(cpn.getValid() == 1){
            promoz.com.br.promoz.util.Message.msgInfo(this,"Usar Cupom","Rotina de utilização do cupom da loja com ID = " + cpn.getStoreId(),android.R.drawable.ic_dialog_info); // TODO: somente para protótipo
            cpn.setValid(0);
            cpnDAO.save(cpn);
            CarteiraPageFragment.handler.sendEmptyMessage(100);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}