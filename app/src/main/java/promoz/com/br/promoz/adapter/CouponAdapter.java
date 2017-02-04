package promoz.com.br.promoz.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import promoz.com.br.promoz.R;
import promoz.com.br.promoz.model.Coupon;

/**
 * Created by vallux on 03/02/17.
 */

public class CouponAdapter extends BaseAdapter {

    private Context context;
    private List<Coupon> list;

    public CouponAdapter(Context context, List<Coupon> lst) {
        this.context = context;
        this.list = lst;
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

        Coupon object = list.get(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.coupon_cel, null);
        }

        ImageView logo = (ImageView) view.findViewById(R.id.logo);
        logo.setTag(object.get_id());
        logo.setImageDrawable(ContextCompat.getDrawable(context,object.getImg())); // carrega logo

        ImageButton info = (ImageButton) view.findViewById(R.id.info_button);
        info.setTag(object.get_id()); // associa tag para buscar info do cupom adequado

        TextView title = (TextView) view.findViewById(R.id.cupom_title);
        title.setText(object.getTitle());

        TextView subTitle = (TextView) view.findViewById(R.id.cupom_subtitle);
        subTitle.setText(object.getSubTitle());

        TextView date = (TextView) view.findViewById(R.id.cupom_date);

        if(object.getValid() == 1)
            date.setText("Expira em: " + object.getDateExp());
        else
            date.setText("Usado em: " + object.getDateUse());

        TextView gostore = (TextView) view.findViewById(R.id.goto_loja);
        gostore.setTag(object.getStoreId());

        RadioButton use = (RadioButton) view.findViewById(R.id.cupom_use);
        use.setTag(object.get_id()); // associa tag para buscar status do cupom adequado
        use.setChecked(object.getValid()==0);

        return view;
    }
}
