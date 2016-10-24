package com.lxw.dailynews.app.presenter;

import com.bumptech.glide.module.GlideModule;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.lxw.dailynews.app.bean.LatestNewsBean;
import com.lxw.dailynews.app.bean.SplashPictureInfoBean;
import com.lxw.dailynews.app.model.model.ISplashModel;
import com.lxw.dailynews.app.model.modelImp.SplashModel;
import com.lxw.dailynews.app.ui.view.ISplashView;
import com.lxw.dailynews.framework.common.Config.Constant;
import com.lxw.dailynews.framework.common.application.BaseApplication;
import com.lxw.dailynews.framework.common.base.BaseMvpPresenter;
import com.lxw.dailynews.framework.http.HttpListener;
import com.lxw.dailynews.framework.image.ImageManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zion on 2016/10/16.
 */

public class SplashPresenter extends BaseMvpPresenter<ISplashView> {
    private ISplashModel splashModel;

    public SplashPresenter() {
        splashModel = new SplashModel();
    }

    public void getSplashPictureInfo() {
        splashModel.getSplashPictureInfo(new HttpListener<SplashPictureInfoBean>() {

            @Override
            public void onSuccess(final SplashPictureInfoBean response) {
                if (response != null) {
                    //显示图片和版权作者
                    getView().setSplashPicture(response.img, response.text);
                    // 使用IO线程下载更新本地保存的启动页图片
                    Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            ImageManager.getInstance().downloadImage(BaseApplication.appContext, response.img,Constant.PATH_SPLASH_PICTURE, Constant.PATH_SPLASH_PICTURE_PNG);
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
                } else {
                    getView().setSplashPicture();
                }
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

    public void getLatestNews() {
        splashModel.getLatestNews(new HttpListener<LatestNewsBean>() {

            @Override
            public void onSuccess(LatestNewsBean response) {
                if (response != null) {
                    getView().setLatestNewsBean(response);
                }
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }
}
