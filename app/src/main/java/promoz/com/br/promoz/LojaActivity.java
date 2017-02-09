package promoz.com.br.promoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import promoz.com.br.promoz.adapter.ShopAdapter;
import promoz.com.br.promoz.dao.HistoricCoinDAO;
import promoz.com.br.promoz.dao.VirtualStoreDAO;
import promoz.com.br.promoz.dao.WalletDAO;
import promoz.com.br.promoz.model.HistoricCoin;
import promoz.com.br.promoz.model.User;
import promoz.com.br.promoz.model.VirtualStore;
import promoz.com.br.promoz.model.Wallet;
import promoz.com.br.promoz.util.DateUtil;

public class LojaActivity extends AppCompatActivity {

    ListView listShop;
    List<VirtualStore> virtualStoreList;
    VirtualStoreDAO virtualStoreDAO;
    ShopAdapter shopAdapter;
    Integer walletId;
    Integer walletAmount;
    WalletDAO walletDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_loja);
        listShop = (ListView) findViewById(R.id.lstShop);
        walletDAO = new WalletDAO(this);
        walletId = walletDAO.walletIdByUserId(getIntent().getIntExtra(User.getChave_ID(),0));
        walletAmount = walletDAO.getAmountByWalletId(walletId);

        updateStoreList();
    }

    public void  info(View view){
        promoz.com.br.promoz.util.Message.msgInfo(this,((VirtualStore) view.getTag()).getTitle(),((VirtualStore) view.getTag()).getInformation(),android.R.drawable.ic_dialog_info);
    }

    public void buy(View view){
        Integer price = ((VirtualStore) view.getTag()).getPrice();

        if(price <= walletAmount) {
            walletAmount -= price;
            String date = new SimpleDateFormat(DateUtil.YYYYMMDD_HHmmss).format(new Date());
            String desc = ((VirtualStore) view.getTag()).getTitle();
            HistoricCoinDAO historicCoinDAO = new HistoricCoinDAO(this);
            HistoricCoin historicCoin = new HistoricCoin(walletId,1,date, -price,desc);
            historicCoinDAO.save(historicCoin);
            Toast.makeText(this, "Comprou " + desc,Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.saldoInsuficiente) + ": " + walletAmount,Toast.LENGTH_SHORT).show();
        }

    }

    private void updateStoreList(){
        virtualStoreDAO = new VirtualStoreDAO(this);
        virtualStoreList = virtualStoreDAO.list();
        shopAdapter = new ShopAdapter(this,virtualStoreList);
        listShop.setAdapter(shopAdapter);
    }

    @Override
    protected void onDestroy() {
        virtualStoreDAO.closeDatabase();
        walletDAO.closeDatabase();
        super.onDestroy();
    }
}