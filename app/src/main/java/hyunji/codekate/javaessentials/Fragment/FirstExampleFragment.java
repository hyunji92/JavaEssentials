package hyunji.codekate.javaessentials.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import hyunji.codekate.javaessentials.R;

/**
 * Created by hyunji on 2016. 9. 3..
 */

public class FirstExampleFragment extends Fragment {

    @BindView(R.id.content_recyclerView)
    RecyclerView recyclerView;


    private Context context = null;
    public FirstExampleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

}
