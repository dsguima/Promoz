package promoz.com.br.promoz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import promoz.com.br.promoz.dao.CouponDAO;
import promoz.com.br.promoz.dao.WalletDAO;
import promoz.com.br.promoz.model.Coupon;
import promoz.com.br.promoz.model.Wallet;

public class CarteiraPageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private int idCarteira;
    private WalletDAO walletDAO;

    public static CarteiraPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CarteiraPageFragment fragment = new CarteiraPageFragment();
        fragment.setArguments(args);
        return fragment;
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
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Caso Tab SALDO
        if (mPage == 1) {
            View view = inflater.inflate(R.layout.saldo_layout, container, false);

            TextView textoSaldo = (TextView) view.findViewById(R.id.saldoCarteira);
            textoSaldo.setText(walletDAO.walletById(idCarteira).getAmountCoin().toString());

            CouponDAO cupom = new CouponDAO(getContext());
            List<Coupon> lst = cupom.list();
            Log.e("CUPOM","" + lst.get(0).getInfo());
            Log.e("CUPOM","" + lst.get(1).getInfo());
            cupom.closeDatabase();

            return view;

        } else {
            //Caso Tab CUPOM
            View view = inflater.inflate(R.layout.cupom_layout, container, false);
            return view;
        }
    }
}

