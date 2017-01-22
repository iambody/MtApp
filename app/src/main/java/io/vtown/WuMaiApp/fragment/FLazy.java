package io.vtown.WuMaiApp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by datutu on 16/10/20.
 */

public abstract class FLazy extends FBase {
    protected View FBaseView;
    protected Activity FBaseActivity;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;


    //获取参数
    protected abstract void create(Bundle Mybundle);
    //填充fragment

    protected abstract int getContentViewLayoutID();

    //初始化
    protected abstract void initViewsAndEvents(View view);

    //第一次可见
    protected abstract void onFirstUserVisible();

    //fragment可见
    protected abstract void onUserVisible();
    //fragment不可见

    protected abstract void onUserInvisible();

    //fragment销毁
    protected abstract void DetoryViewAndThing();


    private void onFirstUserInvisible() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FBaseActivity = getActivity();
        if (getArguments() != null && getArguments().containsKey("screenWidth")) {
            screenWidth = getArguments().getInt("screenWidth");
            screenHeight = getArguments().getInt("screenHeight");
        }
        create(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            FBaseView = inflater.inflate(getContentViewLayoutID(), null);
            ButterKnife.bind(this, FBaseView);
            return FBaseView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewsAndEvents(view);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    @Override
    public void onDestroy() {
        DetoryViewAndThing();
        try {
            ButterKnife.unbind(this);
        } catch (Exception e) {
        }

        super.onDestroy();
    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }



//    protected void showToast(String msg) {
//        if (null != msg && !StringUtils.isEmpty(msg)) {
//            Snackbar.make(((Activity) mContext).getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
//        }
//    }
}
