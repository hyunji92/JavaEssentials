package hyunji.codekate.javaessentials.navigation;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hyunji.codekate.javaessentials.R;

/**
 * Created by hyunji on 2016. 9. 27..
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>{

    private List<NavigationItem> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public NavigationDrawerAdapter(List<NavigationItem> navigationItems) {
        mData =  navigationItems;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.textView.setText(mData.get(position).getText());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            viewHolder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(mData.get(position).getDrawable(), null, null, null);
        }

        viewHolder.itemView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchPosition(position);
                    return false;
                case MotionEvent.ACTION_CANCEL:
                    touchPosition(-1);
                    return false;
                case MotionEvent.ACTION_MOVE:
                    return false;
                case MotionEvent.ACTION_UP:
                    touchPosition(-1);
                    return false;
            }
            return true;
        });

        viewHolder.itemView.setOnClickListener(v -> {
            if(mNavigationDrawerCallbacks != null){
                mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(position);
            }
        });

        //TODO: selected menu position, change layout accordingly
        // 클릭시 아이템 배경색 바뀐다
        if (mSelectedPosition == position || mTouchedPosition == position) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.selected_gray));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;

    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}
