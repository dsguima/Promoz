package promoz.com.br.promoz;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import promoz.com.br.promoz.adapter.ShopAdapter;
import promoz.com.br.promoz.dao.CouponDAO;
import promoz.com.br.promoz.dao.VirtualStoreDAO;
import promoz.com.br.promoz.dao.WalletDAO;
import promoz.com.br.promoz.model.Coupon;
import promoz.com.br.promoz.model.User;
import promoz.com.br.promoz.model.VirtualStore;
import promoz.com.br.promoz.util.DateUtil;

public class LojaActivity extends AppCompatActivity {

    ListView listShop;
    List<VirtualStore> virtualStoreList;
    VirtualStoreDAO virtualStoreDAO;
    ShopAdapter shopAdapter;
    Integer walletId;
    Integer walletAmount;
    WalletDAO walletDAO;
    CouponDAO couponDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_loja);
        listShop = (ListView) findViewById(R.id.lstShop);
        walletDAO = new WalletDAO(this);
        walletId = walletDAO.walletIdByUserId(getIntent().getIntExtra(User.getChave_ID(),0));
        walletAmount = walletDAO.getAmountByWalletId(walletId);
        walletDAO.closeDataBase();

        updateStoreList();
    }

    public void  info(View view){
        promoz.com.br.promoz.util.Message.msgInfo(this,((VirtualStore) view.getTag()).getTitle(),((VirtualStore) view.getTag()).getInformation(),android.R.drawable.ic_dialog_info);
    }

    public void buy(View view){

        VirtualStore virtualStore = (VirtualStore) view.getTag();
        Integer price = virtualStore.getPrice();

        if(price <= walletAmount) {
            walletAmount -= price;

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 10);

            String date = new SimpleDateFormat(DateUtil.YYYYMMDD_HHmmss).format(new Date(cal.getTimeInMillis()));
            String desc = virtualStore.getTitle();

            couponDAO = new CouponDAO(this);
            Coupon coupon = new Coupon();
            coupon.setDateExp(date);
            coupon.setTitle(desc);
            coupon.setSubTitle("Compra na Loja Virtual");
            coupon.setPrice(price);
            coupon.setInfo(virtualStore.getInformation());
            coupon.setImg(virtualStore.getImg());
            coupon.setValid(1);
            coupon.setWalletId(walletId);
            coupon.setStoreId(virtualStore.getStoreId());
            couponDAO.save(coupon);
            couponDAO.closeDataBase();

            Snackbar.make(view, "Comprou " + desc, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(view,getResources().getString(R.string.saldoInsuficiente) + ": " + walletAmount, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            //Toast.makeText(this, getResources().getString(R.string.saldoInsuficiente) + ": " + walletAmount,Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStoreList(){
        virtualStoreDAO = new VirtualStoreDAO(this);
        virtualStoreList = virtualStoreDAO.list();
        shopAdapter = new ShopAdapter(this,virtualStoreList);
        listShop.setAdapter(shopAdapter);
        virtualStoreDAO.closeDataBase();
    }
}