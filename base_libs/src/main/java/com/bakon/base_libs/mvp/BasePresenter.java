package com.bakon.base_libs.mvp;


import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * BasePresenter
 * bakon 2017-9-11 14:06:52
 */
public class BasePresenter implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    public BasePresenter() {
        onStart();
    }

    public void onStart() {
        //如果要使用eventbus请将此方法返回true
        if (useEventBus()){
            EventBus.getDefault().register(this);//注册eventbus
        }
    }

    @Override
    public void onDestroy() {
        if (useEventBus()){
            //如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);//解除注册eventbus
        }
        unSubscribe();//解除订阅
        this.mCompositeDisposable = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }

    /**
     * 添加统一处理
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        //将所有subscription放入,集中处理
        mCompositeDisposable.add(disposable);
    }

    /**
     * 解除订阅
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            //保证activity结束时取消所有正在执行的订阅
            mCompositeDisposable.clear();
        }
    }

}
