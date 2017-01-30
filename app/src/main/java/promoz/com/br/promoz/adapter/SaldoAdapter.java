package promoz.com.br.promoz.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import promoz.com.br.promoz.model.HistoricCoin;

/**
 * Created by vallux on 29/01/17.
 */

public class SaldoAdapter extends BaseAdapter {

    private Context context;
    private List<HistoricCoin> list;

    public SaldoAdapter(Context context, List<HistoricCoin> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 0;
    }



    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
