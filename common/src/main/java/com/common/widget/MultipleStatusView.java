package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.common.R;

import java.util.ArrayList;

/**
 * 一个方便在多种状态切换的view
 *
 * @author lzy
 * create at 2018/10/30 14:07
 **/
@SuppressWarnings("unused")
public class MultipleStatusView extends FrameLayout {
    private static final String TAG = "MultipleStatusView";

    public static final FrameLayout.LayoutParams DEFAULT_LAYOUT_PARAMS =
            new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);

    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;
    public static final int STATUS_NO_LOGIN = 0x05;

    private static final int NULL_RESOURCE_ID = -1;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mNoNetworkView;
    private View mNoLoginView;
    private View mContentView;
    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private int mNoLoginViewResId;
    private int mContentViewResId;
    private boolean isFirstShowContent = false;

    private int mViewStatus;
    private LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;
    private OnClickListener mOnGoLoginClickListener;

    private final ArrayList<Integer> mOtherIds = new ArrayList<>();

    public MultipleStatusView(Context context) {
        this(context, null);
    }

    public MultipleStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.MultipleStatusView_emptyView, R.layout.common_multi_status_empty_view);
        mErrorViewResId = a.getResourceId(R.styleable.MultipleStatusView_errorView, R.layout.common_multi_status_error_view);
        mLoadingViewResId = a.getResourceId(R.styleable.MultipleStatusView_loadingView, R.layout.common_multi_status_loading_view);
        mNoNetworkViewResId = a.getResourceId(R.styleable.MultipleStatusView_noNetworkView, R.layout.common_multi_status_no_network_view);
        mNoLoginViewResId = a.getResourceId(R.styleable.MultipleStatusView_noLoginView, R.layout.common_multi_status_no_login_view);
        mContentViewResId = a.getResourceId(R.styleable.MultipleStatusView_contentView, NULL_RESOURCE_ID);
        isFirstShowContent = a.getBoolean(R.styleable.MultipleStatusView_isFirstShowContent, false);
        a.recycle();
        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            showContent();
        } else {
            if (isFirstShowContent) {
                showContent();
            } else {
                showLoading();
            }
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clear(mEmptyView, mLoadingView, mErrorView, mNoNetworkView, mNoLoginView);
        if (null != mOtherIds) {
            mOtherIds.clear();
        }
        if (null != mOnRetryClickListener) {
            mOnRetryClickListener = null;
        }
        mInflater = null;
    }

    /**
     * 获取当前状态
     */
    public int getViewStatus() {
        return mViewStatus;
    }

    /**
     * 设置重试点击事件
     *
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    /**
     * 设置去登录点击事件
     *
     * @param onGoLoginClickListener 去登录点击事件
     */
    public void setOnGoLoginClickListener(OnClickListener onGoLoginClickListener) {
        this.mOnGoLoginClickListener = onGoLoginClickListener;
    }

    /**
     * 显示空视图
     */
    public final void showEmpty() {
        showEmpty(mEmptyViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示空视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showEmpty(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showEmpty(inflateView(layoutId), layoutParams);
    }

    /**
     * 显示空视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showEmpty(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Empty view is null!");
        mViewStatus = STATUS_EMPTY;
        if (null == mEmptyView) {
            mEmptyView = view;
            View emptyRetryView = mEmptyView.findViewById(R.id.empty_retry_view);
            if (null != mOnRetryClickListener && null != emptyRetryView) {
                emptyRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(mEmptyView.getId());
            addView(mEmptyView, 0, layoutParams);
        }
        showViewById(mEmptyView.getId());
    }

    /**
     * 显示错误视图
     */
    public final void showError() {
        showError("加载失败");
    }

    public final void showError(String notice) {
        showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS, notice);
    }

    /**
     * 显示错误视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showError(int layoutId, ViewGroup.LayoutParams layoutParams, String notice) {
        showError(inflateView(layoutId), layoutParams, notice);
    }

    /**
     * 显示错误视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showError(View view, ViewGroup.LayoutParams layoutParams, String notice) {
        checkNull(view, "Error view is null!");
        mViewStatus = STATUS_ERROR;
        if (null == mErrorView) {
            mErrorView = view;
            TextView errorRetryView = mErrorView.findViewById(R.id.error_retry_view);
            if (null != mOnRetryClickListener && null != errorRetryView) {
                errorRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(mErrorView.getId());
            addView(mErrorView, 0, layoutParams);
        }
        TextView errorRetryView = mErrorView.findViewById(R.id.error_retry_view);
        if (errorRetryView != null) {
            errorRetryView.setText(notice);
        }
        showViewById(mErrorView.getId());
    }

    /**
     * 显示加载中视图
     */
    public final void showLoading() {
        showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示加载中视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showLoading(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showLoading(inflateView(layoutId), layoutParams);
    }

    /**
     * 显示加载中视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showLoading(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Loading view is null!");
        mViewStatus = STATUS_LOADING;
        if (null == mLoadingView) {
            mLoadingView = view;
            mOtherIds.add(mLoadingView.getId());
            addView(mLoadingView, 0, layoutParams);
        }
        showViewById(mLoadingView.getId());
    }

    /**
     * 显示无网络视图
     */
    public final void showNoNetwork() {
        showNoNetwork(mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示无网络视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showNoNetwork(inflateView(layoutId), layoutParams);
    }

    /**
     * 显示无网络视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "No network view is null!");
        mViewStatus = STATUS_NO_NETWORK;
        if (null == mNoNetworkView) {
            mNoNetworkView = view;
            View noNetworkRetryView = mNoNetworkView.findViewById(R.id.no_network_retry_view);
            View setNetworkView = mNoNetworkView.findViewById(R.id.set_network_view);
            if (null != mOnRetryClickListener && null != noNetworkRetryView) {
                noNetworkRetryView.setOnClickListener(mOnRetryClickListener);
            }
            if (setNetworkView != null) {
                setNetworkView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NetworkUtils.openWirelessSettings();
                    }
                });
            }
            mOtherIds.add(mNoNetworkView.getId());
            addView(mNoNetworkView, 0, layoutParams);
        }
        showViewById(mNoNetworkView.getId());
    }

    /**
     * 显示未登录视图
     */
    public final void showNoLogin() {
        showNoLogin(mNoLoginViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示未登录视图
     *
     * @param layoutId     自定义视图
     * @param layoutParams 布局参数
     */
    public final void showNoLogin(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showNoLogin(inflateView(layoutId), layoutParams);
    }

    /**
     * 显示未登录视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showNoLogin(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "No login view is null!");
        mViewStatus = STATUS_NO_LOGIN;
        if (null == mNoLoginView) {
            mNoLoginView = view;
            View noLoginGoView = mNoLoginView.findViewById(R.id.no_login_go_view);
            if (null != mOnGoLoginClickListener && null != noLoginGoView) {
                noLoginGoView.setOnClickListener(mOnGoLoginClickListener);
            }
            mOtherIds.add(mNoLoginView.getId());
            addView(mNoLoginView, 0, layoutParams);
        }
        showViewById(mNoLoginView.getId());
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        mViewStatus = STATUS_CONTENT;
        if (null == mContentView && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater.inflate(mContentViewResId, null);
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
        }
        showContentView();
    }

    private void showContentView() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(mOtherIds.contains(view.getId()) ? View.GONE : View.VISIBLE);
        }
    }

    private View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    private void showViewById(int viewId) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
        }
    }

    private void checkNull(Object object, String hint) {
        if (null == object) {
            throw new NullPointerException(hint);
        }
    }

    private void clear(View... views) {
        if (null == views) {
            return;
        }
        try {
            for (View view : views) {
                if (null != view) {
                    removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View getEmptyView() {
        return mEmptyView;
    }

}
