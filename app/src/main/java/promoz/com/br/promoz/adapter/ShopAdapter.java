package promoz.com.br.promoz.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import promoz.com.br.promoz.R;
import promoz.com.br.promoz.model.VirtualStore;

/**
 * Created by vallux on 07/02/17.
 */

public class ShopAdapter extends CustomAdapter {

    public ShopAdapter(Context context, List<VirtualStore> lst) {
        super(context, lst);
    }

    @Override
    public long getItemId(int i) {
        return ((VirtualStore) list.get(i)).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        VirtualStore object = (VirtualStore) list.get(i);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.shop_cel, null);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.shopImg);
        imageView.setImageDrawable(ContextCompat.getDrawable(context,object.getImg()));

        TextView price = (TextView) view.findViewById(R.id.shopPrice);
        price.setText(object.getPrice().toString());

        TextView title = (TextView) view.findViewById(R.id.shopTitle);
        title.setText(object.getTitle());

        Button buy = (Button) view.findViewById(R.id.shopBuy);
        buy.setTag(object);

        ImageButton info = (ImageButton) view.findViewById(R.id.shopInfo);
        buy.setTag(object);

        return view;
    }
}
