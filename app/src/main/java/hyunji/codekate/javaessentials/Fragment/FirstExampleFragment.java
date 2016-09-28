package hyunji.codekate.javaessentials.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hyunji.codekate.javaessentials.R;
import hyunji.codekate.javaessentials.adapter.BaseAdapter;
import hyunji.codekate.javaessentials.app.App;
import hyunji.codekate.javaessentials.app.ApplicationsList;
import hyunji.codekate.javaessentials.utils.Utils;
import hyunji.codekate.javaessentials.vo.AppInfo;
import hyunji.codekate.javaessentials.vo.AppInfoRich;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;


/**
 * Created by hyunji on 2016. 9. 3..
 */

public class FirstExampleFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_first_example_swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private BaseAdapter mAdapter;

    private Context context = null;
    private File mFilesDir;

    public FirstExampleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_recyclerview, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        context = getContext();
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new BaseAdapter(new ArrayList<AppInfo>(), R.layout.applications_list_item);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myPrimaryColor));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        // Progress
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);

//        getFileDir()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(file -> {
//                    mFilesDir = file;
//                    refreshTheList();
//                });
    }

    private void refreshTheList() {
        //Fragment가 로드될때 불린다
        getApps().
                toSortedList()
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "Here is the list!", Toast.LENGTH_LONG).show();
                    }

                    //1. 데이터가 들어오면 프로그레스 바를 숨기고 데이터를 리스트에 추가한다.
                    //2. 리스트를 출력한다.
                    //3. 에러가발생할 경우는 Toast창을 띄운다.
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter.addApplication(appInfos);
                        mSwipeRefreshLayout.setRefreshing(false);
                        storeList(appInfos);
                    }
                });
    }

    private void storeList(List<AppInfo> appInfos) {
        ApplicationsList.getInstance().setList(appInfos);

        Schedulers.io().createWorker().schedule(() -> {
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            Type appInfoType = new TypeToken<List<AppInfo>>() {
            }.getType();
            sharedPreferences.edit().putString("APPS", new Gson().toJson(appInfos, appInfoType)).apply();

        });

    }

    private Observable<AppInfo> getApps() {
        return Observable.create(subscriber -> {
            List<AppInfoRich> apps = new ArrayList<AppInfoRich>();

            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> infos = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);
            for (ResolveInfo info : infos) {
                apps.add(new AppInfoRich(getActivity(), info));
            }

            for (AppInfoRich appinfo : apps) {
                Bitmap icon = Utils.drawableToBitmap(appinfo.getIcon());
                String name = appinfo.getName();
                String iconPath = mFilesDir + "/" + name;
                Utils.storeBitmap(App.instance, icon, name);

                if (subscriber.isUnsubscribed()) {
                    return;
                }
                subscriber.onNext(new AppInfo(name, iconPath, appinfo.getLastUpdateTime()));
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }

        });
    }

    private Observable<File> getFileDir() {
        return Observable.create(subscriber -> {
            subscriber.onNext(App.instance.getFilesDir());
            subscriber.onCompleted();
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
}


