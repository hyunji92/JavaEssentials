package hyunji.codekate.javaessentials.navigation;

import android.support.design.internal.NavigationMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hyunji.codekate.javaessentials.R;

/**
 * Created by hyunji on 2016. 9. 27..
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>{

    private List<NavigationMenu> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;

    public NavigationDrawerAdapter(List<NavigationMenu> navigationMenus) {
        mData =  navigationMenus;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}
