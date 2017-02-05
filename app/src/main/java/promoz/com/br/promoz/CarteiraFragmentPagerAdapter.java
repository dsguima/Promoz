package promoz.com.br.promoz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rafae on 30/01/2017.
 */

public class CarteiraFragmentPagerAdapter  extends FragmentPagerAdapter {
    private int PAGE_COUNT;
    private String tabTitles[];
    private Context context;

    public CarteiraFragmentPagerAdapter(FragmentManager fm, Context context, int pagec, String[] titles) {
        super(fm);
        this.context = context;
        PAGE_COUNT = pagec;
        tabTitles=titles;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return CarteiraPageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
