package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 02/04/2021 11:17
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface TrainingCommentDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void getReplyList(List<TrainingCommentBean.ReplyVoListBean> replyVoListBeans);

        void addReplySuccess();

        void replyLikeChange(TrainingCommentBean.ReplyVoListBean replyVoListBean, int position);

        void trainingCommentLikeSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<List<TrainingCommentBean.ReplyVoListBean>>> getCommentReplyList(String id);

        Observable<BaseBean<String>> addCommentReply(String id, String replyContent);

        Observable<BaseBean<String>> replyLike(String commentId);

        Observable<BaseBean<String>> trainingCommentLike(String id);
    }
}
