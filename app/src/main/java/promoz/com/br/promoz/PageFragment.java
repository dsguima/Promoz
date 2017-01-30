package promoz.com.br.promoz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import promoz.com.br.promoz.dao.WalletDAO;
import promoz.com.br.promoz.model.Wallet;


public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Caso Tab SALDO
        if (mPage == 1) {
            View view = inflater.inflate(R.layout.saldo_layout, container, false);
            WalletDAO wallet = new WalletDAO(view.getContext());
            List<Wallet> lstWallet = wallet.list();
            TextView textoSaldo = (TextView) view.findViewById(R.id.saldoCarteira);

            int idCarteira = 0; // deve haver uma consulta para recuperar o ID da carteira com base no id do usuário que está atualmente conectado!

            if(!lstWallet.isEmpty())
                textoSaldo.setText(wallet.list().get(idCarteira).getAmountCoin());
            else
              textoSaldo.setText("1"); // apenas par teste de funcionamento
            return view;
        } else {
            //Caso Tab CUPOM
            View view = inflater.inflate(R.layout.cupom_layout, container, false);
            return view;
        }
    }
}

