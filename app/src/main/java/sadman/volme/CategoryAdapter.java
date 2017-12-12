package sadman.volme;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by sadman on 02/12/17.
 */

public class CategoryAdapter extends FragmentPagerAdapter{

    private Context mContext;


    public CategoryAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position){
        if (position == 0){
            return new ThisWeekFragment();
        } else if (position == 1){
            return new ThisMonthFragment();
        } else {
            return new PermanentFragment();
        }
    }

    @Override
    public int getCount(){return 3;}

    @Override
    public CharSequence getPageTitle(int position){
        if (position == 0){
            return "Останні";
        } else if (position == 1){
            return "Підписки";
        } else {
            return "Збережені";
        }
    }


}
