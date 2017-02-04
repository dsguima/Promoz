package promoz.com.br.promoz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import promoz.com.br.promoz.adapter.CouponAdapter;
import promoz.com.br.promoz.dao.CouponDAO;
import promoz.com.br.promoz.dao.WalletDAO;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.Coupon;

public class CarteiraPageFragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private int idCarteira;
    private WalletDAO walletDAO;
    private CouponDAO couponDAO;
    private List<Coupon> couponList;
    private static CouponAdapter couponAdapter;

    public static CarteiraPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CarteiraPageFragment fragment = new CarteiraPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void updateDB() {
        couponAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        if(walletDAO == null)
            walletDAO = new WalletDAO(getContext());

        idCarteira = 1; // TODO: deve haver uma consulta para recuperar o ID da carteira com base no id do usuário que está atualmente conectado!
    }

    @Override
    public void onDestroy() {
        walletDAO.closeDatabase();
        couponDAO.closeDatabase();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        couponDAO = new CouponDAO(getContext());
        //Caso Tab SALDO
        if (mPage == 1) {
            View view = inflater.inflate(R.layout.saldo_layout, container, false);

            TextView textoSaldo = (TextView) view.findViewById(R.id.saldoCarteira);
            textoSaldo.setText(walletDAO.walletById(idCarteira).getAmountCoin().toString());

            CouponDAO cupom = new CouponDAO(getContext());
            List<Coupon> lst = cupom.list("");
            cupom.closeDatabase();
            return view;

        } else {
            //Caso Tab CUPOM
            View view = inflater.inflate(R.layout.cupom_layout, container, false);
            ListView listcoupon = (ListView) view.findViewById(R.id.lstCoupon);
            listcoupon.setDividerHeight(10);
            couponList = couponDAO.list(PromozContract.Coupon.COLUMN_CPN_IND_VALID + " DESC, " + PromozContract.Coupon.COLUMN_CPN_DT_EXP + " ASC, " + PromozContract.Coupon.COLUMN_CPN_DT_USE + " DESC");
            couponAdapter = new CouponAdapter(getContext(),couponList);
            listcoupon.setAdapter(couponAdapter);
            return view;
        }
    }
}

