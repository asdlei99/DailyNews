package com.lxw.dailynews.app.ui.view;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.lxw.dailynews.app.bean.LatestNewsBean;
import com.lxw.dailynews.framework.common.base.BaseMvpView;

/**
 * Created by Zion on 2016/10/19.
 */

public interface ISplashView extends BaseMvpView {
    void initView();

    void getSplashPictureInfo();

    void setSplashPicture(String imgUrl, String author);

    void setSplashPicture();

    void getLatestNews();

    void setLatestNewsBean(LatestNewsBean latestNewsBean);

    void jumpToNext();
}
