package hyunji.codekate.javaessentials.navigation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by hyunji on 2016. 9. 24..
 */
public class NavigationDrawerFragment extends Fragment implements  NavigationDrawerCallbacks{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        return container;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {


    }

    public void setup(int fragment_drawer, DrawerLayout mDrawerLayout, Toolbar mToolbar) {
    }

    public boolean isDrawerOpen() {
        return  true;
    }

    public void closeDrawer() {
    }
}
