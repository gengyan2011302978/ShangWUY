package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;

import io.reactivex.Observable;


public interface MyNotesContract {

    interface View extends IBaseView {

        void thumbsUpSuccess(int position, int likeStatus);

        void loadDataSuccess(BaseListBean<MyNotesBean> beans, boolean isRefresh);

        void loadDataFailure(boolean isRefresh);

        void showShareItemDialog(ShareItemBean shareItemBean);
    }

    /**
     * Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
     */
    interface Model extends IModel {

        /**
         * 获取笔记列表
         */
        Observable<BaseBean<BaseListBean<MyNotesBean>>> getNotesList(int pageNo, int pageSize);

        /**
         * 笔记点赞、取消点赞
         */
        Observable<BaseBean> thumbsUp(int notesId, int courseId);

        /**
         * 动态、课程评论、笔记、专题留言分享
         */
        Observable<BaseBean<ShareItemBean>> getShareItemData(int type, String otherId, String content, String couType);

    }
}
