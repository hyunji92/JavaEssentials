package hyunji.codekate.javaessentials.navigation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hyunji.codekate.javaessentials.R;


/**
 * Created by hyunji on 2016. 9. 24..
 */
public class NavigationDrawerFragment extends Fragment implements  NavigationDrawerCallbacks{

    @BindView(R.id.drawerList)
    RecyclerView mDrawerList;

    private int mCurrentSelectedPosition;
    private DrawerLayout mDrawerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);

        final List<NavigationMenu> navigationMenus = getMenu();
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationMenus);
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
        selectItem(mCurrentSelectedPosition);

        return view;
    }

    private List<NavigationMenu> getMenu() {
        List<NavigationMenu> items = new ArrayList<>();
        return  items;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {


    }

    public void setup(int fragment_drawer, DrawerLayout mDrawerLayout, Toolbar mToolbar) {
    }

    void selectItem(int position) {
        mCurrentSelectedPosition = position;


    }

    public boolean isDrawerOpen() {
        return  true;
    }

    public void closeDrawer() {
    }
}
