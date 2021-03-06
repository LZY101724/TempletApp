package com.common.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.common.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基础dialog
 *
 * @author lzy
 * create at 2018/7/9 15:09
 **/
public abstract class BaseBottomSheetDialog extends BottomSheetDialog implements DialogInterface.OnShowListener {

    protected View rootView;

    protected LayoutParams mLayoutParams;

    Unbinder unbinder;

    boolean isUnbinder = false;

    public BaseBottomSheetDialog(Context context, int themeResId) {
        super(context, themeResId);
        onCreateView();
    }

    public BaseBottomSheetDialog(Context context) {
        super(context, R.style.BaseBottomSheetDialog);
        onCreateView();
    }


    protected void doBeforeOnCreateView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
    }

    /**
     * 装载view（不能放在oncrete()方法中，会找不到view）
     *
     * @author lzy
     * create at 2018/7/9 15:04
     **/
    private void onCreateView() {
        doBeforeOnCreateView();
        rootView = getLayoutInflater().inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, rootView);
        isUnbinder = false;
        setContentView(rootView);
        initAnimation();
        setOnShowListener(this);
        initView();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void initView() {
    }

    protected void initAnimation() {
//        getWindow().setWindowAnimations(R.style.BaseBottomSheetDialog_windowAnim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutParams(getDefaultLayoutParams());
    }

    /**
     * 隐藏头部导航栏状态栏
     */
    public void skipTools() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置宽度match_parent
     */
    public void setFullScreenWidth() {
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
    }

    public void setOnWhole() {
        getWindow().setType(LayoutParams.TYPE_SYSTEM_ALERT);
    }

    public void setLayoutParams(LayoutParams params) {
        getWindow().setAttributes(params);
    }

    public LayoutParams getDefaultLayoutParams() {
        if (mLayoutParams == null) {
            mLayoutParams = getWindow().getAttributes();
            mLayoutParams.width = LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = LayoutParams.WRAP_CONTENT;
        }
        return mLayoutParams;
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void onShow(DialogInterface dialog) {

    }

    public void showDialog() {
        if (!isShowing()) {
            show();
        }
    }
}
