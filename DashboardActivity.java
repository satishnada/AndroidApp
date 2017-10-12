package com.bestdb;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bestdb.snackbar.SnackBarInstance;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private final int SNACK_TIME = 2000;
    public SnackBarInstance mSnackBarInstance;

    private EditText mEdtSearchKeyword;
    private
    private RecyclerView mRecyclerViewList;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<String> mStringList = new ArrayList<>();
    private EventsListAdapter mEventsListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mPageNo = 1;
    private int mTotalRecords = 0;
    private int mPageLimit = 0;
    private String mSearchKeyword = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mEdtSearchKeyword = (EditText) findViewById(R.id.edtSearchKeyword);
        mRecyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewList);
        mLayoutManager = new LinearLayoutManager(DashboardActivity.this);
        mRecyclerViewList.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        mSnackBarInstance = new SnackBarInstance(this);
        mSnackBarInstance.setSnackBarDuration(SNACK_TIME);

        mStringList.add("Satish");
        mStringList.add("Satish");
        mStringList.add("Satish");
        mStringList.add("Satish");
        mStringList.add("Satish");
        mStringList.add("Satish");
        mStringList.add("Satish");

        mEventsListAdapter = new EventsListAdapter(DashboardActivity.this, mStringList, mRecyclerViewList);
        mRecyclerViewList.setAdapter(mEventsListAdapter);

        mEventsListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("hint", "Load More");
                mRecyclerViewList.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mStringList.size() > (mPageLimit - 1) && mStringList.size() < mTotalRecords) {
                            mStringList.add(null);
                            mEventsListAdapter.notifyItemInserted(mStringList.size() - 1);
                            mPageNo++;
                            //getArticle(false, false);
                        }
                    }
                });
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSearchKeyword = "";
                mPageNo = 1;
                //getArticle(true, true);
            }
        });

        mEdtSearchKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    Utility.hideKeyBoardFromView(DashboardActivity.this);
                    mPageNo = 1;
                    mSearchKeyword = mEdtSearchKeyword.getText().toString().trim();
                    //getArticle(true, true);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    public void onFilterClick(View viewFilter) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_filter, null, false);
            final PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

            LinearLayout llNews = (LinearLayout) layout.findViewById(R.id.llNews);
            final ImageView ivNews = (ImageView) layout.findViewById(R.id.ivNews);
            LinearLayout llBlogs = (LinearLayout) layout.findViewById(R.id.llBlogs);
            final ImageView ivBlogs = (ImageView) layout.findViewById(R.id.ivBlogs);
            LinearLayout llArticles = (LinearLayout) layout.findViewById(R.id.llArticles);
            final ImageView ivArticles = (ImageView) layout.findViewById(R.id.ivArticles);

            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            final View view1 = popupWindow.getContentView();
            view1.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupWindow.showAsDropDown(viewFilter, 0, -10);

            llNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivNews.setSelected(!ivNews.isSelected());
                    if (ivNews.isSelected()) {
                        ivNews.setSelected(true);
                    } else {
                        ivNews.setSelected(false);
                    }
                }
            });

            llBlogs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivBlogs.setSelected(!ivBlogs.isSelected());
                    if (ivBlogs.isSelected()) {
                        ivBlogs.setSelected(true);
                    } else {
                        ivBlogs.setSelected(false);
                    }
                }
            });

            llArticles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivArticles.setSelected(!ivArticles.isSelected());
                    if (ivArticles.isSelected()) {
                        ivArticles.setSelected(true);
                    } else {
                        ivArticles.setSelected(false);
                    }
                }
            });


            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //getArticle(true, true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            finish();
            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        mSnackBarInstance.showSnackBar(mRecyclerViewList, "Press again to exit", R.color.colorAccent,R.color.colorWhite);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}