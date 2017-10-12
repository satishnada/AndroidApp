package com.bestdb;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOADING = 2;

    private Context mContext;
    private ArrayList<String> mEventList;

    private int mLastPosition = 0;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean mIsLoading;
    private int mVisibleThreshold = 1;
    private int mLastVisibleItem, mTotalItemCount;

    public EventsListAdapter(Context context, ArrayList<String> activityList,RecyclerView recyclerView) {
        mContext = context;
        mEventList = activityList;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    mIsLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mEventList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_events_list, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final EventsListAdapter.ViewHolder holder, final int position) {
        try {
            if (holder instanceof ViewHolder) {
                holder.txtEventName.setText("Event Name "+position);

                setAnimation(holder.itemView, position);

            }else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.pbLoadMore.setIndeterminate(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            return mEventList.size();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressbar;
        private TextView txtEventName;
        private ImageView imgEventImage;

        public ViewHolder(View view) {
            super(view);

            progressbar = (ProgressBar) view.findViewById(R.id.progressBar);
            txtEventName = (TextView) view.findViewById(R.id.txtEventName);
            imgEventImage = (ImageView) view.findViewById(R.id.imgEventImage);
        }

    }

    private static class LoadingViewHolder extends EventsListAdapter.ViewHolder {
        public ProgressBar pbLoadMore;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            pbLoadMore = (ProgressBar) itemView.findViewById(R.id.pbLoadMore);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.bottom_to_top);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }
    public void setLoaded() {
        mIsLoading = false;
    }
}