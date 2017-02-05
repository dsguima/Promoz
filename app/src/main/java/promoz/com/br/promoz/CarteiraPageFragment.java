package promoz.com.br.promoz;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private int walletID=0;
    private WalletDAO walletDAO;
    private CouponDAO couponDAO;
    private List<Coupon> couponList;
    private CouponAdapter couponAdapter;
    private ListView listcoupon;
    public static Handler handler;


    OnHeadlineGetUserID callback;

    // Container Activity must implement this interface
    public interface OnHeadlineGetUserID {
        public Integer getUserId();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnHeadlineGetUserID) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public static CarteiraPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CarteiraPageFragment fragment = new CarteiraPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateList(){
        couponList = couponDAO.list(walletID, PromozContract.Coupon.COLUMN_CPN_IND_VALID + " DESC, " + PromozContract.Coupon.COLUMN_CPN_DT_EXP + " ASC, " + PromozContract.Coupon.COLUMN_CPN_DT_USE + " DESC");
        couponAdapter = new CouponAdapter(getContext(),couponList);
        listcoupon.setAdapter(couponAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt(ARG_PAGE);

        if(walletDAO == null)
            walletDAO = new WalletDAO(getContext());

        walletID = walletDAO.walletIdByUserId(callback.getUserId());

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
             if( msg.what == 100 ){
                updateList();
                couponAdapter.notifyDataSetChanged();
            }
            }
        };
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
            textoSaldo.setText(walletDAO.walletById(walletID).getAmountCoin().toString());
            return view;
        } else {
            //Caso Tab CUPOM
            CouponDAO cupom = new CouponDAO(getContext());
            List<Coupon> lst = cupom.list(walletID);
            cupom.closeDatabase();
            View view = inflater.inflate(R.layout.cupom_layout, container, false);
            listcoupon = (ListView) view.findViewById(R.id.lstCoupon);
            listcoupon.setDividerHeight(5);
            updateList();
            return view;
        }
    }
}

