package hyunji.codekate.javaessentials.navigation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private static final String PREFERENCES_FILE = "my_app_settings";

    private boolean mFromSavedInstanceState;
    private NavigationDrawerCallbacks mCallbacks;
    private int mCurrentSelectedPosition;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private View mFragmentContainerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);

        final List<NavigationItem> navigationItem = getMenu();
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationItem);
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
        selectItem(mCurrentSelectedPosition);

        return view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "false"));
        if(saveInstanceState != null){
            mCurrentSelectedPosition = saveInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        }catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    public void setup(int fragment_drawer, DrawerLayout drawerLayout, Toolbar mToolbar) {
        mFragmentContainerView = getActivity().findViewById(fragment_drawer);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.myPrimaryColor));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout, mToolbar, R.string.drawer_open , R.string.drawer_close ) {

            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                if(!isAdded()){
                    return;
                }

                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveSharedSetting(getActivity(), PREF_USER_LEARNED_DRAWER, "true" );
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                if(!isAdded()){
                    return;
                }
                getActivity().invalidateOptionsMenu();
            }

        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
    void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if(mDrawerLayout != null){
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if(mCallbacks != null){
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        ((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);

    }

    public ActionBarDrawerToggle gerActionBarDrawerToggle(ActionBarDrawerToggle actionBarDrawerToggle){
       return mActionBarDrawerToggle;
    }

    public void setmActionBarDrawerToggle(ActionBarDrawerToggle actionBarDrawerToggle) {
        mActionBarDrawerToggle = actionBarDrawerToggle;
    }

    private void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    private String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences shardPreferences = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return shardPreferences.getString(settingName, defaultValue);
    }

    private List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<>();
        items.add(new NavigationItem("Example 1", getResources().getDrawable(R.drawable.ic_action_android)));
        items.add(new NavigationItem("Example 2", getResources().getDrawable(R.drawable.ic_action_android)));
        items.add(new NavigationItem("Example 3", getResources().getDrawable(R.drawable.ic_action_android)));

        items.add(new NavigationItem("Filter", getResources().getDrawable(R.drawable.ic_action_filter)));
        items.add(new NavigationItem("Take and TakeLast", getResources().getDrawable(R.drawable.ic_action_filter)));
        items.add(new NavigationItem("Distinct abd DistinctUntilChanged", getResources().getDrawable(R.drawable.ic_action_filter)));

        items.add(new NavigationItem("Map", getResources().getDrawable(R.drawable.ic_action_wizard)));
        items.add(new NavigationItem("Scan", getResources().getDrawable(R.drawable.ic_action_wizard)));
        items.add(new NavigationItem("GroupBy", getResources().getDrawable(R.drawable.ic_action_wizard)));

        items.add(new NavigationItem("Merge", getResources().getDrawable(R.drawable.ic_action_process_save)));
        items.add(new NavigationItem("Zip", getResources().getDrawable(R.drawable.ic_action_process_save)));
        items.add(new NavigationItem("Join", getResources().getDrawable(R.drawable.ic_action_process_save)));
        items.add(new NavigationItem("CombineLatest", getResources().getDrawable(R.drawable.ic_action_process_save)));
        items.add(new NavigationItem("And Then When", getResources().getDrawable(R.drawable.ic_action_process_save)));

        items.add(new NavigationItem("SharedPreferences", getResources().getDrawable(R.drawable.ic_action_playback_schuffle)));
        items.add(new NavigationItem("Long task", getResources().getDrawable(R.drawable.ic_action_playback_schuffle)));
        items.add(new NavigationItem("Network task", getResources().getDrawable(R.drawable.ic_action_playback_schuffle)));

        items.add(new NavigationItem("Stack Overflow", getResources().getDrawable(R.drawable.ic_action_android)));

        return  items;
    }
    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }
}
