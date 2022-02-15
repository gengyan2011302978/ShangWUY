package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.TopicItemInfoBean;
import com.phjt.shangxueyuan.bean.TopicListBean;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 18:23
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface TopicInfoListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void themeListSuccess(TopicItemInfoBean beans, int pageNo, boolean isRefresh);

        void themeListFailure();

        void themeLikeSuccess(int position, String likeStatus);

        void themeLikeFaile(String msg);

        void themeDeleteSuccess();

        void themeDeleteFaile(String msg);

        void themeTopSuccess();

        void themeTopFaile(String msg);

        void showShareItemDialog(ShareItemBean shareItemBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<TopicItemInfoBean>> themeList(int pageNo, int pageSize, String type, String topicId, String topicUserId);

        Observable<BaseBean> themeLike(String themeId);

        Observable<BaseBean> themeDelete(String themeId, String topicId, String topicUserId);

        Observable<BaseBean> themeTop(String themeId, String isTop, String topicId, String topicUserId);

        Observable<BaseBean<ShareItemBean>> getShareItemData(int type, String otherId, String content);
    }
}
