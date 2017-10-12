package com.bestdb.snackbar;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.bestdb.R;

public class SnackBarInstance {

    private int mSnackBarColor = -1;
    private int mSnackBarInfoTextColor = -1;
    private int mSnackBarActionTextColor = -1;
    private int mSnackBarDuration = Snackbar.LENGTH_LONG;
    private String mSnackBarInfoText;
    private String mSnackBarActionText;
    private SnackBarActionListener mSnackBarActionListener;
    private View mViewSnackBar;
    private Snackbar snackBar;
    private Context mContext;

    public SnackBarInstance(Context context) {
        this.mContext = context;
    }

    /**
     * Method for show Snack bar
     *
     * @param view    view to find parent from.
     * @param message Set message to be displayed
     */

    public void showSnackBar(View view, String message) {
        mViewSnackBar = view;
        mSnackBarInfoText = message;
        mSnackBarActionText = "";
        setSnackBar();
    }

    /**
     * Method for show Snack bar
     *
     * @param view            view to find parent from
     * @param message         Set message to be displayed
     * @param backGroundColor Set background color to be displayed, Default it is gray
     */

    public void showSnackBar(View view, String message, int backGroundColor) {
        mViewSnackBar = view;
        mSnackBarInfoText = message;
        mSnackBarActionText = "";
        mSnackBarColor = backGroundColor;
        setSnackBar();
    }

    /**
     * Method for show Snack bar
     *
     * @param view            view to find parent from
     * @param message         Set message to be displayed
     * @param backGroundColor Set background color to be displayed, Default it is gray
     * @param infoTextColor   Set info color to be displayed
     */

    public void showSnackBar(View view, String message, int backGroundColor, int infoTextColor) {
        mViewSnackBar = view;
        mSnackBarInfoText = message;
        mSnackBarActionText = "";
        mSnackBarInfoTextColor = infoTextColor;
        mSnackBarColor = backGroundColor;
        setSnackBar();
    }

    /**
     * Method for show Snack bar
     *
     * @param view       view to find parent from
     * @param message    Set message to be displayed
     * @param actionText Set Action text to be displayed
     * @param listener   Set listener to be invoked when action text is clicked.
     */

    public void showSnackBar(View view, String message, String actionText,
                             SnackBarActionListener listener) {
        mViewSnackBar = view;
        mSnackBarInfoText = message;
        mSnackBarActionText = actionText;
        mSnackBarActionListener = listener;
        setSnackBar();
    }

    /**
     * Method for show Snack bar
     *
     * @param view            view to find parent from
     * @param message         Set message to be displayed
     * @param actionText      Set Action text to be displayed
     * @param actionTextColor Set Action text color to be displayed
     * @param listener        Set listener to be invoked when action text is clicked.
     */

    public void showSnackBar(View view, String message, String actionText, int actionTextColor,
                             SnackBarActionListener listener) {
        mViewSnackBar = view;
        mSnackBarInfoText = message;
        mSnackBarActionText = actionText;
        mSnackBarActionListener = listener;
        mSnackBarActionTextColor = actionTextColor;
        setSnackBar();
    }

    /**
     * Method for show Snack bar
     *
     * @param view            view to find parent from
     * @param message         Set message to be displayed
     * @param actionText      Set Action text to be displayed
     * @param actionTextColor Set Action text color to be displayed
     * @param backGroundColor Set background color to be displayed, Default it is gray
     * @param infoTextColor   Set info text color to be displayed
     * @param listener        Set listener to be invoked when action text is clicked.
     */

    public void showSnackBar(View view, String message, String actionText, int actionTextColor,
                             int backGroundColor, int infoTextColor,
                             SnackBarActionListener listener) {
        mViewSnackBar = view;
        mSnackBarInfoText = message;
        mSnackBarActionText = actionText;
        mSnackBarInfoTextColor = infoTextColor;
        mSnackBarColor = backGroundColor;
        mSnackBarActionListener = listener;
        mSnackBarActionTextColor = actionTextColor;
        setSnackBar();
    }


    private void setSnackBar() {
        snackBar = Snackbar.make(mViewSnackBar, mSnackBarInfoText, mSnackBarDuration);
        View snackBarView = snackBar.getView();
        TextView sncTxt=(TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        if (mSnackBarColor != -1) {
            snackBarView.setBackgroundColor(mContext.getResources().getColor(mSnackBarColor));
        }
        if (mSnackBarInfoTextColor != -1) {
            TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
            textView.setMaxLines(4);
            textView.setTextColor(mContext.getResources().getColor(mSnackBarInfoTextColor));
        }

        if (mSnackBarActionText.length() > 0) {
            snackBar.setAction(mSnackBarActionText, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSnackBarActionListener.onSnackBarActionListener(view);
                }
            });
        }

        if (mSnackBarActionTextColor > 0) {
            snackBar.setActionTextColor(mContext.getResources().getColor(mSnackBarActionTextColor));
        }
        snackBar.show();
    }

    /**
     * Set duration for SnackBar
     *
     * @param mSnackBarDuration values from Snackbar.LENGTH_SHORT ,
     *                          Snackbar.LENGTH_LONG ,
     *                          Snackbar.LENGTH_INDEFINITE
     */

    public void setSnackBarDuration(int mSnackBarDuration) {
        this.mSnackBarDuration = mSnackBarDuration;
    }
}
