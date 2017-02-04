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

public class HistoricAdapter extends BaseAdapter {

    private Context context;
    private List<HistoricCoin> list;

    public HistoricAdapter(Context context, List<HistoricCoin> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        HistoricCoin object = list.get(i);

        return view;
    }
}
