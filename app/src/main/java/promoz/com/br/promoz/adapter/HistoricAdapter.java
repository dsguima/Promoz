package promoz.com.br.promoz.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import promoz.com.br.promoz.R;
import promoz.com.br.promoz.model.HistoricCoin;
import promoz.com.br.promoz.util.DateUtil;

/**
 * Created by vallux on 29/01/17.
 */

public class HistoricAdapter extends BaseAdapter {

    private Context context;
    private List<HistoricCoin> list;

    public HistoricAdapter(Context context, List<HistoricCoin> lst) {
        this.context = context;
        this.list = lst;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.list.get(i).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        HistoricCoin object = list.get(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.history_layout_line, null);
        }

        TextView date = (TextView) view.findViewById(R.id.historic_date);
        date.setText(DateUtil.SQLiteDateFormatToBrazilFormat(object.getHistoricDateOperation()));

        TextView desc = (TextView) view.findViewById(R.id.historic_description);
        desc.setText(object.getOperationDescription());

        TextView amount = (TextView) view.findViewById(R.id.historic_value);
        Integer value = object.getAmountCoin();
        ImageView coin = (ImageView) view.findViewById(R.id.historic_coin);

        if(value < 0){
            amount.setTextColor(view.getResources().getColor(R.color.colorPrimaryDark));
            coin.setImageDrawable(view.getResources().getDrawable(R.drawable.moeda_gasto));
        }else{
            amount.setTextColor(view.getResources().getColor(R.color.colorVerdeMoeda));
            coin.setImageDrawable(view.getResources().getDrawable(R.drawable.moeda));
        }
        amount.setText(value.toString());

        return view;
    }
}
