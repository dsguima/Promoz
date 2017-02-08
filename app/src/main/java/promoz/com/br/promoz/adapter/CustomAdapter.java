package promoz.com.br.promoz.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import promoz.com.br.promoz.model.Coupon;

/**
 * Created by vallux on 07/02/17.
 */

public class CustomAdapter extends BaseAdapter {

    protected Context context;
    protected List<?> list;

    public CustomAdapter(Context context, List<?> list) {
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
