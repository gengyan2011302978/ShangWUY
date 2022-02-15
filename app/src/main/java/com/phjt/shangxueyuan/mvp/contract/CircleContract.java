package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.ThemeMainBean;
import com.phjt.shangxueyuan.bean.TopicMainBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 10/19/2020 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface CircleContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void recommendTopicsSuccess(List<TopicMainBean> list);

        void recommendTopicsFaile(String msg);

        void showShareItemDialog(ShareItemBean shareItemBean);

        void ListBannerSuccess(BaseBean<List<ListBannerBean>> bean, String type);

        void canLoadMore();

        void loadRefreshDataSuccess(ThemeMainBean beans, int pageNo, boolean isRefresh, int position, String themeId);

        void loadRefreshDataFailure();

        void themeLikeSuccess(int position, String likeStatus);

        void themeLikeFaile(String msg);

        void addTopicSuccess(String data);

        void addTopicFaile(String msg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<List<TopicMainBean>>> recommendTopics();

        Observable<BaseBean> themeLike(String themeSource, String themeId, String likeStatus, String commentId, String notesId, String leaveId, String courseId);

        Observable<BaseBean<List<ListBannerBean>>> ListBanner(String bannerType);

        Observable<BaseBean<ThemeMainBean>> getRefreshList(String themeId, int pageNo, int pageSize);

        Observable<BaseBean<String>> addTopic(int type);

        Observable<BaseBean<ShareItemBean>> getShareItemData(int type, String otherId, String content, int shareType, String couType);
    }
}
