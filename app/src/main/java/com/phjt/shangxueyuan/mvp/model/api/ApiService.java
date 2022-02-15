package com.phjt.shangxueyuan.mvp.model.api;


import com.phjt.shangxueyuan.bean.*;
import com.phjt.shangxueyuan.utils.FileUploadUtils;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * The interface Api service.
 *
 * @author : gengyan date:    2019/11/11 14:18 company: 普华集团 description: 网络请求接口
 */
public interface ApiService {
    /**
     * 上传接口
     *
     * @param file 文件
     * @return return observable
     */
    @Multipart
    @POST("common/v1.0.0/uploadFile")
    Observable<BaseBean<FileUploadUtils.UploadResultBean>> uploadImg(@Part MultipartBody.Part file);

    /**
     * 获取用户基本信息
     *
     * @return return observable
     */
    @POST("user/v1.0.0/userInfo")
    Observable<BaseBean<UserInfoBean>> UserInfoBean();

    /**
     * banner图列表
     *
     * @param bannerType the banner type
     * @return observable
     */
    @GET
    Observable<BaseBean<List<ListBannerBean>>> ListBanner(@Url String string, @Query("bannerType") String bannerType, @Query("type") int type);

    /**
     * 未读消息总数
     *
     * @return return un read count
     */
    @GET("message/v1.0.0/unReadCount")
    Observable<BaseBean<Integer>> getUnReadCount();


    /**
     * 滑块验证
     *
     * @param sessionId sessionId
     * @param sig       sig
     * @param token     token
     * @param scene     scene
     * @return return observable
     */
    @FormUrlEncoded
    @POST("register/v1.0.0/sliderValidation")
    Observable<BaseBean> sliderValidation(@Field("sessionId") String sessionId,
                                          @Field("sig") String sig,
                                          @Field("token") String token,
                                          @Field("scene") String scene,
                                          @Field("mobile") String mobile,
                                          @Field("type") int type);

    /**
     * 充值学豆
     */
    @FormUrlEncoded
    @POST("order/v2.3.0/createStudyCoinOrder")
    Observable<BaseBean<String>> createStudyCoinOrder(@Field("commodityMoney") String sessionId,
                                                      @Field("payMethod") int payMethod);

    /**
     * 获取课程收藏列表
     *
     * @param currentPage the current page
     * @param pageSize    the page size
     * @return return collection list
     */
    @FormUrlEncoded
    @POST("myCenter/v1.0.0/findUserFavouriteList")
    Observable<BaseBean<BaseListBean<MyCollectionBean>>> getCollectionList(@Field("type") int type,
                                                                           @Field("currentPage") int currentPage,
                                                                           @Field("pageSize") int pageSize);

    /**
     * 获取专题收藏记录列表
     *
     * @param currentPage the current page
     * @param pageSize    the page size
     * @return return collection list
     */
    @FormUrlEncoded
    @POST("special/v1.3.0/specialFavoriteList")
    Observable<BaseBean<BaseListBean<MyCollectionBean>>> getSpecialFavouriteList(@Field("currentPage") int currentPage,
                                                                                 @Field("pageSize") int pageSize);

    /**
     * 修改用户基本信息
     *
     * @param nickName the nick name
     * @param userName the user name
     * @param sex      the sex
     * @return return observable
     */
    @FormUrlEncoded
    @POST("user/v1.0.0/userEdit")
    Observable<BaseBean> onClickUserEdit(@Field("nickName") String nickName,
                                         @Field("userName") String userName,
                                         @Field("sex") int sex);

    /**
     * 修改用户基本信息-有头像的
     *
     * @param nickName the nick name
     * @param userName the user name
     * @param sex      the sex
     * @return return observable
     */
    @FormUrlEncoded
    @POST("user/v1.0.0/userEdit")
    Observable<BaseBean> onClickUserEditPhoto(@Field("nickName") String nickName,
                                              @Field("userName") String userName,
                                              @Field("sex") int sex,
                                              @Field("photo") String photo);

    /**
     * 获取课程列表
     *
     * @param level       the level id
     * @param couTypeId   the couTypeId id
     * @param sort        the sort id
     * @param lecturerId  the teacher id
     * @param currentPage the current page
     * @param pageSize    the page size
     * @return the course list
     */
    @FormUrlEncoded
    @POST("course/v1.0.2/courseList")
    Observable<BaseBean<BaseCourseListBean<CourseItemBean>>> getCourseList(@Field("level") int level, @Field("couTypeId") String couTypeId,
                                                                           @Field("sort") Integer sort, @Field("lecturerId") String lecturerId,
                                                                           @Field("currentPage") int currentPage, @Field("pageSize") int pageSize);

    /**
     * 获取课程分类列表
     *
     * @return the course type list
     */
    @POST("course/v1.0.0/findCourseTypeList")
    Observable<BaseBean<List<CourseTypeItemBean>>> getCourseTypeList();

    /**
     * 学生端直播列表
     *
     * @return the course type list
     */
    @POST("live/v2.4.0/studentLiveList")
    Observable<BaseBean<List<LiveBean>>> getStudentLiveList();

    /**
     * 老师端直播列表
     *
     * @return the course type list
     */
    @POST("live/v2.0.0/teacherLiveList")
    Observable<BaseBean<List<TeacherLiveBean>>> getTeacherLiveList();

    /**
     * 获取讲师列表
     *
     * @param typeId the type id
     * @return the course teacher list
     */
    @FormUrlEncoded
    @POST("leclecturer/v1.0.0/findLecLecturerList")
    Observable<BaseBean<List<CourseTeacherItemBean>>> getCourseTeacherList(@Field("typeId") String typeId, @Field("lastTypeId") String lastTypeId);


    /**
     * 删除收藏
     *
     * @param ids the ids
     * @return return favorite edit
     */
    @FormUrlEncoded
    @POST("myCenter/v1.0.0/favoriteEdit")
    Observable<BaseBean> getFavoriteEdit(@Field("ids") String ids);

    /**
     * 直播预约
     *
     * @return return favorite edit
     */
    @FormUrlEncoded
    @POST("live/v2.0.0/liveReserve")
    Observable<BaseBean> liveReserve(@Field("liveInfoId") String liveInfoId);

    /**
     * 直播基本信息
     *
     * @return return favorite edit
     */
    @FormUrlEncoded
    @POST("live/v2.0.0/liveInfo")
    Observable<BaseBean<LiveBean>> liveInfo(@Field("id") String id);

    /**
     * 修改直播的状态
     *
     * @return return favorite edit
     */
    @FormUrlEncoded
    @POST("live/v2.0.0/updateLiveState")
    Observable<BaseBean> updateLiveState(@Field("channelId") String channelId, @Field("state") String state, @Field("id") String id, @Field("ticketId") String ticketId);

    /**
     * 专题删除收藏
     *
     * @param ids the ids
     * @return return favorite edit
     */
    @FormUrlEncoded
    @POST("special/v1.3.0/specialFavoriteEdit")
    Observable<BaseBean> getspecialEdit(@Field("ids") String ids);

    /**
     * 专题收藏、取消收藏
     *
     * @param specialId the ids
     * @return return favorite edit
     */
    @FormUrlEncoded
    @POST("special/v1.3.0/specialFavorite")
    Observable<BaseBean> getFavoriteEdit(@Field("specialId") int specialId);

    /**
     * 专题点赞
     *
     * @return return favorite edit
     */
    @FormUrlEncoded
    @POST("leaveWord/v1.4.0/insertLeaveWordLike")
    Observable<BaseBean> insertLeaveWordLike(@Field("leaveWordId") int leaveWordId, @Field("status") int status);

    /**
     * 用户注册接口 register/v1.0.0/userRegister
     *
     * @param mobile           the mobile
     * @param verificationCode the verification code
     * @param password         the password
     * @return return observable
     */
    @FormUrlEncoded
    @POST("register/v1.0.0/userRegister")
    Observable<BaseBean> userRegister(@Field("mobile") String mobile, @Field("verificationCode") String verificationCode, @Field("password") String password);

    /**
     * getVerificationCode 验证码获取
     *
     * @param mobile the mobile
     * @param type   the type
     * @return return verification code
     */
    @FormUrlEncoded
    @POST("register/v1.0.0/getVerificationCode")
    Observable<BaseBean> getVerificationCode(@Field("mobile") String mobile, @Field("type") int type);

    /**
     * getVerificationCode 忘记、修改密码验证码校验
     *
     * @return return
     */
    @FormUrlEncoded
    @POST("register/v1.0.0/getMobileValidataCode")
    Observable<BaseBean> validateCode(@Field("mobile") String mobile, @Field("verificationCode") String code, @Field("type") int codeType);

    /**
     * getVerificationCode 忘记密码重置密码
     *
     * @return return
     */
    @FormUrlEncoded
    @POST("register/v1.0.0/updatePassword")
    Observable<BaseBean> resetPassword(@Field("mobile") String mobile, @Field("newPassword") String newPassword);


    /**
     * getVerificationCode 修改密码重置密码
     *
     * @return return
     */
    @FormUrlEncoded
    @POST("register/v1.0.0/changePassword")
    Observable<BaseBean> changePassword(@Field("mobile") String mobile, @Field("newPassword") String newPassword);


    /**
     * 课程兑换记录
     */
    @FormUrlEncoded
    @POST("redeem/v1.3.0/courseExchangeRecord")
    Observable<BaseBean<ExchangeCodeBean>> getCourseExchangeRecord(@Field("currentPage") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 圈子首页---动态
     */
    @FormUrlEncoded
    @POST("theme/v1.5.0/theme")
    Observable<BaseBean<ThemeMainBean>> getRefreshList(@Field("id") String themeId, @Field("currentPage") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 发布话题
     */
    @FormUrlEncoded
    @POST("topic/v1.5.0/addTopic")
    Observable<BaseBean<String>> addTopic(@Field("type") int type, @Field("topicName") String topicName, @Field("focusDescribe") String focusDescribe, @Field("coverImg") String coverImg);


    /**
     * 会员兑换记录
     */
    @FormUrlEncoded
    @POST("redeem/v1.3.0/vipExchangeRecord")
    Observable<BaseBean<VipExchangeCodeBean>> getVipExchangeRecord(@Field("currentPage") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 兑换
     */
    @FormUrlEncoded
    @POST("redeem/v1.3.0/toChange")
    Observable<BaseBean<String>> toChange(@Field("code") String code);

    /**
     * 全部话题
     */
    @FormUrlEncoded
    @POST("topic/v1.5.0/topicList")
    Observable<BaseBean<TopicListBean>> getTopicList(@Field("type") int type, @Field("currentPage") int pageNo, @Field("pageSize") int pageSize);

    /**
     * 检查更新
     *
     * @param type the type
     * @return return check version
     */
    @GET("api/v1.0.0/getCheckVersion")
    Observable<BaseBean<UpdateAppBean>> getCheckVersion(@Query("type") int type);

    /**
     * 停服公告
     */
    @Streaming
    @GET
    Observable<BaseBean<MainAnnouncementBean>> getAnnouncementJson(@Url String string);

    /**
     * 首页公告
     *
     * @return return announcement list
     */
    @POST("api/v1.0.2/getAnnouncementList")
    Observable<BaseBean<List<AnnouncementListBean>>> getAnnouncementList();

    /**
     * 首页公告
     *
     * @return return announcement list
     */
    @POST("theme/v1.5.0/imgConfig")
    Observable<BaseBean<List<ReleaseCoverBean>>> imgConfig();

    /**
     * 首页资讯
     */
    @POST("article/v1.0.2/popularArticle")
    Observable<BaseBean<List<PopularArticleListBean>>> getPopularArticle();

    /**
     * 首页课程推荐
     */
    @POST("course/v1.0.2/courseRecommend")
    Observable<BaseBean<List<CourseRecommendListBean>>> getCourseRecommend();

    /**
     * 首页直播、数字经济等课程推荐
     */
    @POST("course/v1.7.0/courseCategory")
    Observable<BaseBean<List<CourseRecommendListBean>>> getCourseCategory();

    /**
     * 首页 直播模块 、免费公开课 、直播
     */
    @FormUrlEncoded
    @POST("course/v2.4.0/couRecommend")
    Observable<BaseBean<List<CouRecommendListBean>>> getCouRecommend(@Field("type") int type);

    /**
     * 获取分享页面图片
     */
    @FormUrlEncoded
    @POST("config/v1.0.2/getConfig")
    Observable<BaseBean<String>> getConfig(@Field("code") String code);

    /**
     * 提现规则
     */
    @FormUrlEncoded
    @POST("config/v1.2.0/rulesConfig")
    Observable<BaseBean<List<RulesConfigBean>>> mShareRules(@Field("type") String code);

    /**
     * 首页最近观看记录接口
     */
    @POST("watchHistory/v1.0.2/getUserWatchHistory")
    Observable<BaseBean<UserWatchHistoryBean>> getUserWatchHistory();

    /**
     * 首页活动展示接口
     */
    @POST("activity/v1.0.2/getActivityInfo")
    Observable<BaseBean<ActivityInfoBean>> getActivityInfo();


    /**
     * 获取微信
     */
    @Streaming
    @GET
    Observable<BaseBean<ProdInfoBean>> getSwyProd(@Url String string);


    /**
     * 资讯分类
     */
    @POST("article/v1.0.2/articleClassify")
    Observable<BaseBean<List<ArticleClassifyBean>>> getArticleClassify();

    /**
     * 资讯列表
     */
    @FormUrlEncoded
    @POST("article/v1.0.2/articleList")
    Observable<BaseBean<ArticleListBean>> getArticleList(@Field("id") String id, @Field("currentPage") int currentPage, @Field("pageSize") int pageSize);

    /**
     * 首页栏目各位置查询
     */
    @POST("course/v1.0.2/initIndexSiteInfo")
    Observable<BaseBean<List<InitIndexSiteInfoBean>>> getInitIndexSiteInfo();


    /**
     * 首页-热门推荐
     */
    @POST("course/v2.1.0/homeHotRecommend")
    Observable<BaseBean<List<HomeHotRecommendListBean>>> getHomeHotRecommend();

    /**
     * 课程详情
     *
     * @param courseId the course id
     * @return the course detail
     */
    @FormUrlEncoded
    @POST("course/v1.0.2/courseDetail")
    Observable<BaseBean<CourseDetailBean>> getCourseDetail(@Field("courseId") String courseId);

    /**
     * 课程分享接口
     *
     * @param courseId the course id
     * @return the course share data
     */
    @FormUrlEncoded
    @POST("course/v1.9.0/courseShare")
    Observable<BaseBean<ShareBean>> getCourseShareData(@Field("courseId") String courseId, @Field("type") String type, @Field("pointId") String pointId);

    /**
     * 购买课程，生成订单详情
     * commodityId 课程id
     * orderId 订单id
     *
     * @param commodityId the commodity id
     * @param payMethod   the pay method
     * @return observable
     */
    @FormUrlEncoded
    @POST("order/v1.6.0/newCreateOrder")
    Observable<BaseBean<String>> requestOrderDetail(@Field("commodityId") String commodityId,
                                                    @Field("payMethod") int payMethod,
                                                    @Field("activityState") int activityState,
                                                    @Field("userCouponId") String userCouponId,
                                                    @Field("type") int type);

    /**
     * 购买课程，支付订单
     * commodityId 课程id
     * orderId 订单id
     *
     * @param orderId the order id
     * @param payType the pay type
     * @return observable
     */
    @FormUrlEncoded
    @POST("order/v1.0.0/pay")
    Observable<BaseBean<AdvanceOrderBean>> sendRequestYuOrder(@Field("orderId") String orderId, @Field("payMethod") String payType);

    /**
     * 购买课程，获取支付方式
     */
    @FormUrlEncoded
    @POST("/api/v1.0.2/payStatus")
    Observable<BaseBean<List<PayMethodBean>>> getPayMethod(@Field("type") int client);

    /**
     * 获取课程分类
     */
    @POST("/studyRecord/v1.0.2/researchChannelCouType")
    Observable<BaseBean<List<CourseTypeBean>>> getCourseType();

    /**
     * 获取二级课程分类
     */
    @FormUrlEncoded
    @POST("/studyRecord/v1.0.2/researchChannelCourse")
    Observable<BaseBean<List<CourseChannelBean>>> getChannelCourse(@Field("couTypeId") String couTypeId);

    /**
     * 获取普通课程
     */
    @POST("/studyRecord/v1.0.2/ordinaryCourse")
    Observable<BaseBean<List<OrdinaryCourseBean>>> getOrdinaryCourse();

    /**
     * 获取专栏
     */
    @POST("/studyRecord/v1.6.0/researchColumn")
    Observable<BaseBean<List<OrdinaryCourseBean>>> getResearchColumn();

    /**
     * 我的专栏
     */
    @FormUrlEncoded
    @POST("column/v1.6.0/myColumn")
    Observable<BaseBean<ViewColumnBean>> getMyColumn(@Field("currentPage") int pageSize, @Field("pageSize") int currentPage);

    /**
     * 观看记录
     * commodityId 课程id
     * orderId 订单id
     *
     * @param pageSize    the page size
     * @param currentPage the current page
     * @return view record
     */
    @FormUrlEncoded
    @POST("myCenter/v1.0.2/findUserWatchHistoryCourse")
    Observable<BaseBean<ViewRecordBean>> getViewRecord(@Field("currentPage") int pageSize, @Field("pageSize") int currentPage);

    /**
     * 微信登录
     *
     * @param openId id
     * @return return observable
     */
    @FormUrlEncoded
    @POST("login/v1.9.0/wxLogin")
    Observable<BaseBean<String>> loginByWeChat(@Field("openId") String openId,
                                               @Field("unionid") String unionid);


    /**
     * 绑定手机号,微信登录
     *
     * @param phone            手机号
     * @param openId           id
     * @param verificationCode 验证码
     * @param photoUrl         头像
     * @param userName         微信昵称
     * @return return observable
     */
    @FormUrlEncoded
    @POST("login/v1.9.0/bindingMobile")
    Observable<BaseBean<String>> bindingPhone(@Field("mobile") String phone,
                                              @Field("openId") String openId,
                                              @Field("verificationCode") String verificationCode,
                                              @Field("wxPhoto") String photoUrl,
                                              @Field("nickName") String userName,
                                              @Field("unionid") String unionid);

    /**
     * 短信登录
     *
     * @param mobile           mobile
     * @param verificationCode verificationCode
     * @return return observable
     */
    @FormUrlEncoded
    @POST("login/v1.0.0/quickLogin")
    Observable<BaseBean> quickLogin(@Field("mobile") String mobile, @Field("verificationCode") String verificationCode);

    /**
     * 绑定星球账号并登录
     *
     * @param mobile           mobile
     * @param verificationCode verificationCode
     * @return return observable
     */
    @GET("login/v1.7.0/bindOtherMobile")
    Observable<BaseBean> bindAndLogin(@Query("mobile") String mobile, @Query("otherMobile") String planetNumber, @Query("verificationCode") String verificationCode);


    /**
     * 绑定星球账号
     *
     * @param mobile mobile
     * @return return observable
     */
    @GET("login/v1.7.0/bindMobile")
    Observable<BaseBean> bindPlanetNumber(@Query("mobile") String mobile, @Query("otherMobile") String otherMobile);

    /**
     * 检查与星球账户绑定状态
     */
    @GET("login/v1.7.0/isBind")
    Observable<BaseBean<Boolean>> checkBindStatus(@Query("mobile") String mobile, @Query("otherMobile") String otherMobile);

    /**
     * 是否显示星球登录
     *
     * @return the observable
     */
    @GET("login/v1.7.0/loginType")
    Observable<BaseBean<Boolean>> checkLoginType();

    /**
     * 课程目录列表
     *
     * @param courseId the course id
     * @return the course calalog list
     */
    @FormUrlEncoded
    @POST("course/v1.0.2/coursewareList")
    Observable<BaseBean<List<CourseCatalogFirstBean>>> getCourseCalalogList(@Field("courseId") String courseId);

    /**
     * 课程目录列表
     *
     * @param courseId the course id
     * @return the course calalog list
     */
    @FormUrlEncoded
    @POST("column/v1.6.0/columnList")
    Observable<BaseBean<List<CourseCatalogOneBean>>> getCourseCalalogListZ(@Field("id") String courseId);

    /**
     * 动态点赞、取消点赞
     *
     * @return the course calalog list
     */
    @FormUrlEncoded
    @POST("theme/v1.5.0/themeLike")
    Observable<BaseBean> themeLike(@Field("themeId") String themeId);

    /**
     * 圈子-推荐话题
     */
    @POST("topic/v1.5.0/recommendTopics")
    Observable<BaseBean<List<TopicMainBean>>> recommendTopics();

    /**
     * 收藏、取消收藏
     */
    @FormUrlEncoded
    @POST("myCenter/v1.0.0/favorite")
    Observable<BaseBean> doFavorite(@Field("couId") String couId, @Field("type") String type);

    /**
     * Do login observable.
     *
     * @param mobile   the mobile
     * @param password the password
     * @return the observable
     */
    @FormUrlEncoded
    @POST("login/v1.0.0/doLogin")
    Observable<BaseBean> doLogin(@Field("mobile") String mobile, @Field("password") String password);

    /**
     * Do login observable.
     *
     * @param uuid the uuid
     * @return the observable
     */
    @GET("login/v1.7.0/getTokendByUuid")
    Observable<BaseBean> doLoginByUuid(@Query("uuid") String uuid);


    /**
     * 退出登录
     *
     * @return 退出
     */
    @POST("login/v1.0.0/appExitLogin")
    Observable<BaseBean> outLogin();

    /**
     * 获取用户VIP状态
     *
     * @return Vip状态
     */
    @POST("user/v1.0.0/vipState")
    Observable<BaseBean<VipStateBean>> getVipState();

    /**
     * webview  分享
     */
    @FormUrlEncoded
    @POST("share/v1.0.2/vipShare")
    Observable<BaseBean<ShareBean>> getVipShareContent(@Field("pageType") int pageType);

    /**
     * 查询收藏状态
     */
    @FormUrlEncoded
    @POST("special/v1.3.0/collectionStatus")
    Observable<BaseBean<CollectionStatusBean>> getCollectionStatus(@Field("specialId") int specialId);

    /**
     * 挖掘机认证接口
     *
     * @param account the mobile
     * @param pwd     the password
     */
    @FormUrlEncoded
    @POST("auth/v1.0.2/wjjAuth")
    Observable<BaseBean> getWjjAuth(@Field("account") String account, @Field("pwd") String pwd);

    /**
     * 课程 类别列表
     *
     * @param id 首页栏目分类id
     * @return 课程 类别列表
     */
    @FormUrlEncoded
    @POST("course/v1.0.2/courseTypeListById")
    Observable<BaseBean<CourseCategoryBean>> getCourseCategory(@Field("couTypeId") String id);

    /**
     * 获取课程评论列表
     *
     * @param courseId 课程id
     * @return 课程评论列表
     */
    @FormUrlEncoded
    @POST("courseComment/v1.1.1/getCourseComment")
    Observable<BaseBean<BaseListBean<CourseCommentBean>>> getCourseCommentList(@Field("courseId") String courseId,
                                                                               @Field("commentType") String commentType,
                                                                               @Field("currentPage") int currentPage,
                                                                               @Field("pageSize") int pageSize);

    /**
     * 获取课程评论列表个数
     */
    @FormUrlEncoded
    @POST("course/v1.1.1/courseCommentCount")
    Observable<BaseBean<CourseCommentSizeBean>> getCourseCommentSize(@Field("courseId") String courseId);

    /**
     * 添加课程评论
     *
     * @param courseId 课程id
     * @param content  评论内容
     * @return Observable
     */
    @FormUrlEncoded
    @POST("courseComment/v1.0.2/insertCourseComment")
    Observable<BaseBean> addComment(@Field("courseId") String courseId, @Field("content") String content, @Field("img") String imgs, @Field("commentType") String commentType);

    /**
     * 点赞 取消点赞
     *
     * @param commentId 评论id
     * @param status    1.点赞 2取消点赞
     * @return Observable
     */
    @FormUrlEncoded
    @POST("courseComment/v1.0.2/insertCourseCommentLike")
    Observable<BaseBean> commentZanState(@Field("commentId") String commentId, @Field("status") int status);

    /**
     * 保存观看记录
     *
     * @param courseId      课程ID
     * @param pointId       小节ID
     * @param wareId        章ID
     * @param watchDuration 观看时长
     * @param endFlag       0-未看完 1-已看完
     * @return Observable
     */
    @FormUrlEncoded
    @POST("course/v1.0.2/saveWatchTime")
    Observable<BaseBean> saveWatchTime(@Field("courseId") String courseId, @Field("pointId") String pointId,
                                       @Field("wareId") String wareId, @Field("watchDuration") long watchDuration,
                                       @Field("endFlag") int endFlag,
                                       @Field("columnId") String columnId);

    /**
     * 意见反馈
     *
     * @param feedbackContent 反馈内容
     * @param feedbackImg     反馈图片 多张图片之间用逗号隔开
     * @return Observable
     */
    @FormUrlEncoded
    @POST("/api/v1.0.2/adviceFeedback")
    Observable<BaseBean> submitFeedback(@Field("feedbackContent") String feedbackContent, @Field("feedbackImg") String feedbackImg);

    /**
     * 证书频道详情
     *
     * @param couTypeId 分类ID
     * @return Observable
     */
    @FormUrlEncoded
    @POST("course/v1.0.2/researchChannelDetail")
    Observable<BaseBean<CourseClassifyBean>> getCourseClassifyList(@Field("couTypeId") String couTypeId);

    /**
     * 课程 - 资料列表
     *
     * @param courseId 课程id
     * @return 课程列表
     */
    @FormUrlEncoded
    @POST("course/v1.1.0/couMaterialList")
    Observable<BaseBean<BaseCourseListBean<DataBean>>> getDataList(@Field("courseId") String courseId,
                                                                   @Field("currentPage") int currentPage,
                                                                   @Field("pageSize") int pageSize,
                                                                   @Field("materialType") String materialType);


    /**
     * 获取笔记列表
     *
     * @param currentPage the current page
     * @param pageSize    the page size
     * @return return Notes list
     */
    @FormUrlEncoded
    @POST("notes/v1.1.0/myNotes")
    Observable<BaseBean<BaseListBean<MyNotesBean>>> getNotesList(@Field("currentPage") int currentPage,
                                                                 @Field("pageSize") int pageSize);


    /**
     * 课程——笔记列表
     *
     * @param pageSize the page size
     * @return return Notes list
     */
    @FormUrlEncoded
    @POST("notes/v1.1.1/notesList")
    Observable<BaseBean<BaseListBean<MyNotesBean>>> getCouNotesList(@Field("courseId") String courseId,
                                                                    @Field("pointId") String pointId,
                                                                    @Field("type") int type,
                                                                    @Field("currentPage") int currentPage,
                                                                    @Field("pageSize") int pageSize,
                                                                    @Field("notesType") String notesType);

    /**
     * 获取笔记详情
     *
     * @param currentPage the current page
     * @param pageSize    the page size
     */
    @FormUrlEncoded
    @POST("notes/v1.1.0/notesDetails")
    Observable<BaseBean<BaseListBean<NotesDetailsBean>>> getnotesDetails(@Field("notesId") int notesId,
                                                                         @Field("currentPage") int currentPage,
                                                                         @Field("pageSize") int pageSize);

    /**
     * 回复笔记
     *
     * @param notesId the notesId
     */
    @FormUrlEncoded
    @POST("notes/v1.1.0/addNotesBack")
    Observable<BaseBean> backContent(@Field("notesId") int notesId,
                                     @Field("backContent") String backContent,
                                     @Field("courseId") int courseId);

    /**
     * 添加笔记
     *
     * @param courseId the notesId
     */
    @FormUrlEncoded
    @POST("notes/v1.1.0/addNotes")
    Observable<BaseBean> addNotess(@Field("courseId") String courseId,
                                   @Field("pointId") String pointId,
                                   @Field("noteContent") String noteContent,
                                   @Field("openState") int openState,
                                   @Field("notesImg") String notesImg,
                                   @Field("coursePauseTime") long coursePauseTime,
                                   @Field("type") int type,
                                   @Field("notesType") String notesType);

    /**
     * 弟子群绑定
     */
    @FormUrlEncoded
    @POST("auth/v1.1.0/dzqAuth")
    Observable<BaseBean> getDiscipleGroupAuth(@Field("phone") String phone, @Field("code") String code);

    /**
     * 公开展示
     */
    @FormUrlEncoded
    @POST("exerciseBook/v1.9.0/openState")
    Observable<BaseBean> openState(@Field("state") int state, @Field("exerciseUserRecordId") String exerciseUserRecordId);

    /**
     * 答案点赞
     */
    @FormUrlEncoded
    @POST("exerciseBook/v1.9.0/answerUp")
    Observable<BaseBean> answerUp(@Field("exerciseUserRecordId") String exerciseUserRecordId);

    /**
     * 扫一扫
     *
     * @param certificate 从二维码中获取的字符串
     */
    @FormUrlEncoded
    @POST("login/v1.1.0/scanQRcode")
    Observable<BaseBean> getScanQRcode(@Field("certificate") String certificate);

    /**
     * 扫一扫确认
     *
     * @param certificate 从二维码中获取的字符串
     * @param optionType  操作类型 0 取消 1 确认
     */
    @FormUrlEncoded
    @POST("login/v1.1.0/qRcodeConfirm")
    Observable<BaseBean> getScanRcodeConfirm(@Field("certificate") String certificate,
                                             @Field("optionType") int optionType);

    /**
     * 邀请有礼接口
     */
    @POST("invitePolite/v1.3.0/shareImg")
    Observable<BaseBean<List<ShareImgBean>>> inviteShare();

    /**
     * 来问答
     */
    @GET("topic/v2.1.0/answersState")
    Observable<BaseBean<String>> answersState();

    /**
     * 邀请有礼接口
     */
    @POST("register/v1.1.0/inviteShare")
    Observable<BaseBean> inviteShareT();

    /**
     * 是否显示首页 活动弹框
     */
    @POST("image/v1.1.1/bannerTips")
    Observable<BaseBean> getBannerTips();

    /**
     * 收入记录 列表
     */
    @FormUrlEncoded
    @POST("integral/v2.3.0/getUserAuthRecord")
    Observable<BaseBean<BaseListBean<IncomeRecordBean>>> getIncomeRecordList(@Field("currentPage") int currentPage,
                                                                             @Field("pageSize") int pageSize);

    /**
     * 提现记录 列表
     */
    @FormUrlEncoded
    @POST("userWallet/v1.2.0/withdrawalRecord")
    Observable<BaseBean<BaseListBean<WithdrawalRecordBean>>> getWithdrawalRecordList(@Field("currentPage") int currentPage,
                                                                                     @Field("pageSize") int pageSize);

    /**
     * 提现记录 详情
     */
    @FormUrlEncoded
    @POST("userWallet/v1.2.0/withdrawalDetails")
    Observable<BaseBean<WithdrawalRecordBean>> getWithdrawalRecordDetail(@Field("id") String id);

    /**
     * 保存银行卡信息
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("bankCard/v1.2.0/saveBankCardInfo")
    Observable<BaseBean> saveBankCardInfo(@Body RequestBody json);

    /**
     * 可提现金额 提现总额 和 提现最低金额
     */
    @POST("userWallet/v1.2.0/withdrawalBalance")
    Observable<BaseBean<WalletMoneyBean>> getWalletMoneys();

    /**
     * 获取实名认证记录
     */
    @POST("integral/v2.3.0/getUserAuthSum")
    Observable<BaseBean<String>> getUserAuthSum();

    /**
     * 申请提现
     */
    @POST("userWallet/v1.2.0/applyWithdrawal")
    Observable<BaseBean> applyWithdrawal();

    /**
     * 确认提现
     */
    @FormUrlEncoded
    @POST("userWallet/v1.2.0/confirmWithdrawal")
    Observable<BaseBean> confirmWithdrawal(@Field("withdrawalAsset") double withdrawalAsset);

    /**
     * 查询用户银行卡信息
     */
    @POST("bankCard/v1.2.0/getBankCardInfo")
    Observable<BaseBean<BankCardFillBean>> getBankCardInfo();

    /**
     * 我的邀请
     */
    @POST("myCenter/v1.1.0/myInvitation")
    Observable<BaseBean<List<InvitationRecordBean>>> getInvitationRecordList();

    /**
     * 我要点播
     */
    @POST("liveExclusive/v1.4.0/onDemand")
    Observable<BaseBean<OnDemandBean>> getOnDemandBean();

    /**
     * 笔记点赞、取消点赞
     *
     * @param notesId
     * @param courseId
     * @return
     */
    @FormUrlEncoded
    @POST("notes/v1.4.0/thumbsUp")
    Observable<BaseBean> thumbsUp(@Field("notesId") int notesId,
                                  @Field("courseId") int courseId);

    /**
     * 添加笔记
     *
     * @param courseId the notesId
     */
    @FormUrlEncoded
    @POST("notes/v1.4.0/editNotes")
    Observable<BaseBean> editNotes(@Field("courseId") String courseId,
                                   @Field("id") String id,
                                   @Field("noteContent") String noteContent,
                                   @Field("openState") int openState,
                                   @Field("notesImg") String notesImg,
                                   @Field("coursePauseTime") long coursePauseTime);

    /**
     * 消息分类
     *
     * @return
     */

    @GET("message/v1.5.0/messageClassify")
    Observable<BaseBean<List<MessageBean>>> getMessage();

    /**
     * 打卡日历
     *
     * @return
     */
    @FormUrlEncoded
    @POST("punchCard/v1.8.0/homeCalendar")
    Observable<BaseBean<ThemeBean>> homeCalendar(@Field("id") String id);

    /**
     * 主题
     *
     * @return
     */
    @FormUrlEncoded
    @POST("motif/v1.8.0/motifList")
    Observable<BaseBean<List<MotifBean>>> motifList(@Field("id") String id);

    /**
     * 主题详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("motif/v1.8.0/motifDetail")
    Observable<BaseBean<MotifDetailBean>> motifDetail(@Field("id") String id, @Field("punchCardId") String punchCardId);

    /**
     * 主题详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("diary/v1.8.0/diaryList")
    Observable<BaseBean<List<MotifDiaryListBean>>> diaryList(@Field("punchCardId") String punchCardId, @Field("type") String type, @Field("motifId") String motifId);

    /**
     * 排行榜
     *
     * @return
     */
    @FormUrlEncoded
    @POST("punchCard/v1.8.0/rankingList")
    Observable<BaseBean<RankBean>> rankingList(@Field("id") String id);

    /**
     * 消息列表
     *
     * @param pageNo   the page no
     * @param pageSize the page size
     * @return return list message
     */
    @GET("message/v1.5.0/messageClassifyList")
    Observable<BaseBean<MessageListBean>> getListMessage(@Query("type") int type, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 消息详情
     */
    @GET("message/v1.5.0/messageClassifyDetail")
    Observable<BaseBean<MessageDetailBean>> getMessageDetail(@Query("messageId") int messageId);

    /**
     * 查询个人总积分
     */
    @POST("integral/v1.5.0/getUserIntegral")
    Observable<BaseBean<MyPointsBean>> getUserIntegral();

    /**
     * 查询任务列表
     */
    @POST("integral/v1.5.0/getIntegralTaskList")
    Observable<BaseBean<List<TaskListBean>>> getIntegralTaskList();

    /**
     * 话题详情
     */
    @FormUrlEncoded
    @POST("topic/v1.5.0/topicDetail")
    Observable<BaseBean<TopicInfoBean>> topicDetail(@Field("topicId") String topicId);

    /**
     * 话题分享
     */
    @FormUrlEncoded
    @POST("theme/v1.8.0/shareTheme")
    Observable<BaseBean<ShareBean>> shareTheme(@Field("topicId") String topicId);

    /**
     * 详情----动态列表
     */
    @FormUrlEncoded
    @POST("theme/v1.5.0/themeList")
    Observable<BaseBean<TopicItemInfoBean>> themeList(@Field("currentPage") int pageNo,
                                                      @Field("pageSize") int pageSize,
                                                      @Field("type") String type,
                                                      @Field("topicId") String topicId,
                                                      @Field("topicUserId") String topicUserId);

    /**
     * 详情----动态置顶
     */
    @FormUrlEncoded
    @POST("theme/v1.5.0/themeTop")
    Observable<BaseBean> themeTop(@Field("themeId") String themeId,
                                  @Field("state") String state,
                                  @Field("topicId") String topicId,
                                  @Field("topicUserId") String topicUserId);

    /**
     * 详情----动态删除
     */
    @FormUrlEncoded
    @POST("theme/v1.5.0/themeDelete")
    Observable<BaseBean> themeDelete(@Field("themeId") String themeId,
                                     @Field("addThemeUserId") String topicId,
                                     @Field("topicUserId") String topicUserId);

    /**
     * 查询积分明细
     */
    @FormUrlEncoded
    @POST("integral/v2.3.0/getUserDataRecord")
    Observable<BaseBean<BaseListBean<PointsDetailBean>>> getIntegralRecord(@Field("recordStatus") int type,
                                                                           @Field("recordType") int detailType,
                                                                           @Field("currentPage") int pageNo,
                                                                           @Field("pageSize") int pageSize);

    /**
     * 查询学豆排行榜
     */
    @POST("userWalletAsset/v2.3.0/getUserWalletRanking")
    Observable<BaseBean<List<IntegralRankingBean>>> getIntegralRanking();

    /**
     * 个人中心—我的话题
     */
    @FormUrlEncoded
    @POST("topic/v1.5.0/myTopic")
    Observable<BaseBean<BaseListBean<MyTopicBean>>> getTopicList(@Field("currentPage") int pageNo,
                                                                 @Field("pageSize") int pageSize);

    /**
     * 动态、课程评论、笔记、专题留言分享
     */
    @FormUrlEncoded
    @POST("theme/v1.5.0/share")
    Observable<BaseBean<ShareItemBean>> getShareItemData(@Field("type") int type, @Field("otherId") String otherId,
                                                         @Field("content") String content, @Field("showLocalType") int shareType,
                                                         @Field("couType") String couType);

    /**
     * 作业分享
     */
    @FormUrlEncoded
    @POST("exerciseBook/v1.9.0/share")
    Observable<BaseBean<ShareItemBean>> getShareexErciseBook(@Field("id") String id, @Field("couId") String couId,
                                                             @Field("trainingId") String trainingId);

    /**
     * 发布动态
     */
    @FormUrlEncoded
    @POST("theme/v1.5.0/addTheme")
    Observable<BaseBean> addTheme(@Field("themeName") String themeName, @Field("topicId") String topicId, @Field("themeImg") String themeImg);

    /**
     * H5 分包
     */
    @POST("api/v1.5.0/getWebConfig")
    Observable<BaseBean<List<WebAddressListBean>>> getWebAddressList();

    /**
     * @param type
     * @return可用兑换礼券列表
     */
    @FormUrlEncoded
    @POST("coupon/v1.6.0/exchangeCouponList")
    Observable<BaseBean<BaseListBean<CouponBean>>> getExchangeCouponList(@Field("type") int type,
                                                                         @Field("currentPage") int pageNo,
                                                                         @Field("pageSize") int pageSize);

    /**
     * 话题编辑
     */
    @FormUrlEncoded
    @POST("topic/v1.6.0/editTopic")
    Observable<BaseBean> editTopic(@Field("topicId") String topicId, @Field("topicName") String topicName,
                                   @Field("focusDescribe") String focusDescribe, @Field("coverImg") String coverImg);

    /**
     * 获取订单可用优惠卷列表
     */
    @FormUrlEncoded
    @POST("coupon/v1.6.0/orderCouponList")
    Observable<BaseBean<List<CouponBean>>> getCouponList(@Field("commodityTyId") String commodityTyId, @Field("type") int type,
                                                         @Field("activityState") int activityState);

    /**
     * 优惠券到期通知
     */
    @POST("coupon/v1.6.0/bannerNotice")
    Observable<BaseBean<ExpirationNoticeBean>> getExpirationNotice();


    /**
     * 个人中心—我的专栏/训练营列表
     */
    @FormUrlEncoded
    @POST("myCenter/v1.8.0/myCampOrColumn")
    Observable<BaseBean<BaseListBean<MyTrainingCampBean>>> getTrainingBattalionList(@Field("type") int type,
                                                                                    @Field("currentPage") int pageNo,
                                                                                    @Field("pageSize") int pageSize);


    /**
     * 个人中心—我参与的打卡列表
     */
    @FormUrlEncoded
    @POST("punchCard/v1.8.0/myPunchCard")
    Observable<BaseBean<BaseListBean<ParticipatingPunchBean>>> getMyPunchCardList(@Field("currentPage") int pageNo,
                                                                                  @Field("pageSize") int pageSize);

    /**
     * 个人中心—我的日记列表
     */
    @FormUrlEncoded
    @POST("diary/v1.8.0/myDiary")
    Observable<BaseBean<BaseListBean<MyDiaryBean>>> getMyDiaryList(@Field("currentPage") int pageNo,
                                                                   @Field("pageSize") int pageSize);

    /**
     * 个人中心—打卡介绍
     */
    @FormUrlEncoded
    @POST("punchCard/v1.8.0/homeDetail")
    Observable<BaseBean<IntroductionPunchCardsBean>> getIntroductionCardst(@Field("id") String id);

    /**
     * 个人中心—打卡主页顶部焦点图
     */
    @FormUrlEncoded
    @POST("punchCard/v1.8.0/homeFocus")
    Observable<BaseBean<IntroductionTopCardsBean>> getHomeFocus(@Field("id") String id,
                                                                @Field("couId") String couId);

    /**
     * 个人中心—点击保存生成图片
     */
    @FormUrlEncoded
    @POST("diary/v1.8.0/generateImages")
    Observable<BaseBean<SaveGeneratePicturesBean>> clickSaveGeneratePictures(@Field("id") String id,
                                                                             @Field("punchCardId") String punchCardId);

    /**
     * 个人中心—日记详情
     */
    @FormUrlEncoded
    @POST("diary/v1.8.0/diaryDetails")
    Observable<BaseBean<MyDiaryBean>> diaryDetails(@Field("id") String id);

    /**
     * 个人中心—删除日记
     */
    @FormUrlEncoded
    @POST("diary/v1.8.0/delectDiary")
    Observable<BaseBean> delectDiary(@Field("id") String id);

    /**
     * 日记发布/编辑
     */
    @FormUrlEncoded
    @POST("diary/v1.8.0/addDiary")
    Observable<BaseBean<AddDiaryBean>> addDiary(@Field("id") String id,
                                                @Field("punchCardId") String punchCardId,
                                                @Field("content") String content,
                                                @Field("reissueCardType") int reissueCardType,
                                                @Field("diaryImg") String diaryImg,
                                                @Field("calendarDate") String calendarDate,
                                                @Field("nodeTaskLinkId") String nodeTaskLinkId,
                                                @Field("trainingCampId") String trainingCampId,
                                                @Field("motifId") String motifId);

    /**
     * 日记详情
     */
    @FormUrlEncoded
    @POST("diary/v1.8.0/diaryDetails")
    Observable<BaseBean<MyDiaryBean>> getDiaryDetails(@Field("id") String id);

    /**
     * 日记-发布评论
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("diary/v1.8.0/addComment")
    Observable<BaseBean> addDiaryComment(@Body RequestBody json);

    /**
     * 打卡主页分享
     */
    @FormUrlEncoded
    @POST("punchCard/v1.8.0/share")
    Observable<BaseBean<ShareBean>> diaryShare(@Field("id") String id,
                                               @Field("couId") String punchCardId,
                                               @Field("trainingId") String trainingId,
                                               @Field("diaryId") String motifId);

    /**
     * 日记点赞/取消点赞
     *
     * @param notesId
     * @param courseId
     * @return
     */
    @FormUrlEncoded
    @POST("diary/v1.8.0/punchThumbsUp")
    Observable<BaseBean> diaryPunchThumbsUp(@Field("otherId") String notesId,
                                            @Field("otherType") String courseId);

    /**
     * 训练营详情页
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/trainingCamp")
    Observable<BaseBean<TrainingDetailBean>> getTrainingDetail(@Field("id") String id);

    /**
     * 训练营分享
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/shareTranining")
    Observable<BaseBean<ShareBean>> getTrainingShare(@Field("id") String id);

    /**
     * 训练营评论列表
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/discussById")
    Observable<BaseBean<TrainingCommentBean>> getTrainingComment(@Field("id") String id);

    /**
     * 训练营 免费购买（报名）
     *
     * @param trainingCampId 训练营id
     * @return
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/freeBuy")
    Observable<BaseBean<String>> trainingFreeBuy(@Field("trainingCampId") String trainingCampId);

    /**
     * 训练营 添加评论
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/addDiscuss")
    Observable<BaseBean<String>> addTrainingComment(@Field("punchCardId") String punchCardId, @Field("otherId") String otherId,
                                                    @Field("content") String content, @Field("img") String img);

    /**
     * 训练营点赞
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/discussLike")
    Observable<BaseBean<String>> trainingCommentLike(@Field("diaryId") String id);

    /**
     * 训练营-更新观看次数
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/updateTaskById")
    Observable<BaseBean<String>> updateTaskById(@Field("id") String id);

    /**
     * 训练营-更新观看时间
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/updateTaskRecordById")
    Observable<BaseBean<String>> updateTaskRecordById(@Field("trainingCampId") String trainingCampId, @Field("id") String secondId,
                                                      @Field("status") int status, @Field("watchTime") long watchTime);

    /**
     * 我的学习——获取训练营记录
     */
    @POST("tranining/v1.8.0/traniningRecord")
    Observable<BaseBean<List<TrainingBattalionBean>>> getTrainingBattalion();

    /**
     * 训练营-评论详情页-获取回复列表
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/discussByDirayId")
    Observable<BaseBean<List<TrainingCommentBean.ReplyVoListBean>>> getCommentReplyList(@Field("id") String id);

    /**
     * 训练营-评论详情页-新增评论
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/replyDiscuss")
    Observable<BaseBean<String>> addCommentReply(@Field("diaryId") String id, @Field("replyContent") String replyContent);

    /**
     * 训练营-评论详情页-回复点赞
     */
    @FormUrlEncoded
    @POST("tranining/v1.8.0/replyLike")
    Observable<BaseBean<String>> replyLike(@Field("commentId") String commentId);

    /**
     * 课程/训练营——打卡列表
     */
    @FormUrlEncoded
    @POST("punchCard/v1.8.0/cardList")
    Observable<BaseBean<BaseListBean<ParticipatingPunchBean>>> getPunchClockList(@Field("couId") String couId, @Field("couType") String couType,
                                                                                 @Field("currentPage") int currentPage, @Field("pageSize") int pageSize);

    /**
     * 答题-获取作业详情列表
     */
    @FormUrlEncoded
    @POST("exerciseBook/v1.9.0/exerciseBookDetail")
    Observable<BaseBean<ExerciseBean>> getExerciseBookDetail(@Field("id") String id,
                                                             @Field("couId") String couId,
                                                             @Field("exerciseBookId") String exerciseBookId,
                                                             @Field("trainingId") String trainingId);

    /**
     * 答题-提交作业答案
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("exerciseBook/v1.9.0/submitExerciseAnswer")
    Observable<BaseBean<ExerciseBean>> submitExerciseAnswer(@Body RequestBody json);


    /**
     * 答题-获取答案列表
     */
    @FormUrlEncoded
    @POST("exerciseBook/v1.9.0/userAnswer")
    Observable<BaseBean<BaseListBean<ExerciseShowBean>>> userAnswer(@Field("type") String type,
                                                                    @Field("exerciseId") String exerciseId,
                                                                    @Field("couId") String couId,
                                                                    @Field("trainingId") String trainingId,
                                                                    @Field("currentPage") int currentPage,
                                                                    @Field("pageSize") int pageSize);

    /**
     * 个人中心——我的作业列表
     */
    @FormUrlEncoded
    @POST("exerciseBook/v1.9.0/myExerciseBookList")
    Observable<BaseBean<BaseListBean<MineExerciseBean>>> myExerciseBookList(@Field("type") String type,
                                                                            @Field("currentPage") int currentPage,
                                                                            @Field("pageSize") int pageSize);

    /**
     * 作业列表
     */
    @FormUrlEncoded
    @POST("exerciseBook/v1.9.0/exerciseBookList")
    Observable<BaseBean<BaseListBean<MineExerciseBean>>> exerciseBookList(@Field("couId") String type,
                                                                          @Field("couType") String couType,
                                                                          @Field("currentPage") int currentPage,
                                                                          @Field("pageSize") int pageSize);


    /**
     * 直播分享——获取分享页面图片
     */
    @FormUrlEncoded
    @POST("live/v2.0.0/shareImg")
    Observable<BaseBean<List<LiveShareImgBean>>> getSharepicture(@Field("id") String id);

    /**
     * 训练营兑换记录
     */
    @FormUrlEncoded
    @POST("redeem/v1.9.0/trainingExchangeRecord")
    Observable<BaseBean<BaseListBean<TrainingBattalionExchangeBean>>> getRecordList(@Field("currentPage") int currentPage,
                                                                                    @Field("pageSize") int pageSize);

    /**
     * 课程简介
     */
    @FormUrlEncoded
    @POST("live/v2.0.0/liveDetail")
    Observable<BaseBean<MZDesBean>> getLiveDetail(@Field("liveId") String liveId);

    /**
     * 打赏礼物
     */
    @FormUrlEncoded
    @POST("live/v2.3.0/rewardGifts")
    Observable<BaseBean> sendGif(@Field("studyCoin") Double integral, @Field("giftName") String name, @Field("liveId") String liveId);

    /**
     * 老师端分享
     */
    @FormUrlEncoded
    @POST("live/v2.0.0/teacherShare")
    Observable<BaseBean<ShareBean>> getTeacherShare(@Field("id") String id);


    /**
     * 成长营列表
     */
    @FormUrlEncoded
    @POST("community/v2.4.0/h5Community")
    Observable<BaseBean<BaseListBean<StudyCampBean>>> getStudyCampList(@Field("trainingCampType") String trainingCampType,@Field("currentPage") int currentPage,
                                                                       @Field("pageSize") int pageSize);

    /**
     * 课程列表浮层
     *
     * @return the observable
     */
    @GET("image/v2.1.0/floatImg")
    Observable<BaseBean<SuspendImgBean>> showSuspendImgInfo();


    /**
     * 获取热门推荐列表
     *
     * @param currentPage the current page
     * @param pageSize    the page size
     * @return the course list
     */
    @FormUrlEncoded
    @POST("course/v2.1.0/recommendList")
    Observable<BaseBean<BaseCourseListBean<CourseItemBean>>> getRecommendList(@Field("currentPage") int currentPage, @Field("pageSize") int pageSize);

    /**
     * 获取消息跳转作业详情
     *
     * @return the course list
     */
    @FormUrlEncoded
    @POST("message/v2.1.0/mesExerciseDetail")
    Observable<BaseBean<MesExerciseBean>> getMesExerciseDetail(@Field("otherId") String pageSize);


    /**
     * 修改密码、找回密码
     *
     * @param type 1修改 2找回
     */
    @FormUrlEncoded
    @POST("userTrade/modifyTradePassword")
    Observable<BaseBean> modifyPassword(@Field("mobile") String mobile,
                                        @Field("originalPassword") String originalPassword,
                                        @Field("newPassword") String newPassword,
                                        @Field("repeatNewPassword") String repeatNewPassword,
                                        @Field("verificationCode") String verificationCode,
                                        @Field("type") int type);


    /**
     * 验证用户是否已实名和是否设置安全密码
     */
    @FormUrlEncoded
    @POST("userTrade/validTradePwdAndUserAuth")
    Observable<BaseBean<UserAuthBean>> isUserAuth(@Field("validType") int validType);

    /**
     * 验证是否展示启富通
     */
    @POST("userTrade/showQftPointFlag")
    Observable<BaseBean<UserAuthBean>> isShowQftPointFlag();

    /**
     * 身份认证 -提交信息
     */
    @FormUrlEncoded
    @POST("userTrade/userAuth")
    Observable<BaseBean> addUserCertificateInfo(@Field("front") String front,
                                                @Field("back") String back,
                                                @Field("handIdCard") String handIdCard);


    /**
     * 问答——获取精选解答列表
     */
    @FormUrlEncoded
    @POST("questionAnswer/v2.3.0/recommendAnswers")
    Observable<BaseBean<BaseListBean<SelectedAnswersBean>>> getQuestionList(@Field("currentPage") int front,
                                                                            @Field("pageSize") int pageSize,
                                                                            @Field("realmIdStr") String realmIdStr,
                                                                            @Field("recommendTimeSort") int recommendTimeSort,
                                                                            @Field("likeSort") int likeSort);

    /**
     * 问答——获取我的提问列表
     */
    @FormUrlEncoded
    @POST("questionAnswer/v2.3.0/myQuestions")
    Observable<BaseBean<BaseListBean<SelectedAnswersBean>>> getMyQuestionsList(@Field("currentPage") int front,
                                                                               @Field("pageSize") int pageSize,
                                                                               @Field("realmIdStr") String realmIdStr,
                                                                               @Field("createTimeSort") int createTimeSort,
                                                                               @Field("isReply") int isReply);

    /**
     * 问答——筛选所有领域
     *
     * @return return
     */
    @POST("questionAnswer/v2.3.0/allRealms")
    Observable<BaseBean<List<ScreenlBean>>> getRealmSelectList();

    /**
     * 问答——筛选老师领域
     *
     * @return return
     */
    @FormUrlEncoded
    @POST("questionAnswer/v2.3.0/getRealmsByTeacherId")
    Observable<BaseBean<List<ScreenlBean>>> getRealmsByTeacherId(@Field("teacherId") String teacherId);

    /**
     * 问答——提问
     *
     * @return return
     */
    @FormUrlEncoded
    @POST("questionAnswer/v2.3.0/saveQuestion")
    Observable<BaseBean> sendQuestion(@Field("tutorId") String teacherId,
                                      @Field("title") String title,
                                      @Field("content") String content,
                                      @Field("realmId") String realmId,
                                      @Field("isOpen") int isOpen,
                                      @Field("questionImg") String questionImg,
                                      @Field("payType") int payType,
                                      @Field("quantity") int quantity);

    /**
     * 问答——问题明细
     *
     * @return return
     */
    @FormUrlEncoded
    @POST("questionAnswer/v2.3.0/questionDetail")
    Observable<BaseBean<CheckTheAnswerBean>> getUserQuestion(@Field("questionId") String questionId);

    /**
     * 问答——点赞
     *
     * @return return
     */
    @FormUrlEncoded

    @POST("questionAnswer/v2.3.0/likeTt")
    Observable<BaseBean> toLike(@Field("answerId") String questionId,
                                @Field("likeState") int likeState);


    /**
     * 问答——校验用户学分是否足够支付提问
     *
     * @return return
     */
    @FormUrlEncoded
    @POST("questionAnswer/v2.3.0/checkStudyCoin")
    Observable<BaseBean> checkUserCapital(@Field("teacherId") String teacherId);

    /**
     * 提问确认订单
     */
    @FormUrlEncoded
    @POST("questionAnswer/v2.3.0/confirmOrder")
    Observable<BaseBean> answersConfirmOrder(@Field("teacherId") String teacherId,
                                             @Field("payType") int payType,
                                             @Field("quantity") int quantity);

    /**
     * 商城首页-获取商品列表
     */
    @FormUrlEncoded
    @POST("virtualCommodity/v2.3.0/virtualCommodityList")
    Observable<BaseBean<BaseListBean<MallCommodityBean>>> getMallCommodityList(@Field("commodityType") int commodityType,
                                                                               @Field("currentPage") int currentPage,
                                                                               @Field("pageSize") int pageSize);

    /**
     * 兑换记录
     */
    @FormUrlEncoded
    @POST("virtualCommodity/v2.3.0/exchangeList")
    Observable<BaseBean<BaseListBean<MallCommodityBean>>> getExchangeRecordList(@Field("currentPage") int currentPage,
                                                                                @Field("pageSize") int pageSize);

    /**
     * 用户资产
     */
    @POST("userWalletAsset/v2.3.0/getUserWalletInfo")
    Observable<BaseBean<UserAssetsBean>> getUserAssetsInfo();

    /**
     * 积分汇率
     */
    @POST("userWalletAsset/v2.3.0/getExchangeRatio")
    Observable<BaseBean<Double>> getExchangeRatio();

    /**
     * 训练营购买
     */
    @FormUrlEncoded
    @POST("order/v2.3.0/createCommodityOrder")
    Observable<BaseBean> createTrainingOrder(@Field("commodityId") String commodityId, @Field("buyNum") int buyNum,
                                             @Field("payMethod") int payMethod, @Field("commodityMoney") double commodityMoney,
                                             @Field("type") int type);

    /**
     * 商品兑换
     */
    @FormUrlEncoded
    @POST("virtualCommodity/v2.3.0/exchangeVirtualCommodity")
    Observable<BaseBean> exchangeVirtualCommodity(@Field("virtualCommodityId") String virtualCommodityId,
                                                  @Field("payType") int payType,
                                                  @Field("quantity") int quantity);


    /**
     * 新积分任务列表
     */
    @POST("integral/v2.3.0/getNewIntegralTaskList")
    Observable<BaseBean<List<TaskCurrencyFirstBean>>> getNewTaskList();

    /**
     * 问答——获取导师信息列表
     */
    @FormUrlEncoded
    @POST("questionAnswer/v2.3.0/teachList")
    Observable<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>> getAnswerTutorInfoList(@Field("currentPage") int front,
                                                                                           @Field("pageSize") int pageSize,
                                                                                           @Field("realmIdStr") String realmIdStr);

    /**
     * 问答/咨询 ——获取导师信息列表
     */
    @FormUrlEncoded
    @POST("leclecturer/v2.4.0/findConsultLecLecturerList")
    Observable<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>> getTutorInfoList(@Field("currentPage") int front,
                                                                                     @Field("pageSize") int pageSize,
                                                                                     @Field("realmIdStr") String realmIdStr);


    /**
     * 咨询-我的咨询
     */
    @FormUrlEncoded
    @POST("userConsultation/v2.4.0/getList")
    Observable<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>> getMyConsultationList(@Field("currentPage") int front,
                                                                                          @Field("pageSize") int pageSize,
                                                                                          @Field("realmId") String realmId,
                                                                                          @Field("timeSort") int timeSort,
                                                                                          @Field("replyStatus") int replyStatus);


    /**
     * 开通直播专栏/开通达人——下单
     */
    @FormUrlEncoded
    @POST("order/v2.4.0/addOrder")
    Observable<BaseBean> getAddOrder(@Field("commodityId") String commodityId,
                                     @Field("payType") int payType,
                                     @Field("payMethod") int payMethod);

    /**
     * 保存训练营最后观看节点位置
     */
    @FormUrlEncoded
    @POST("tranining/v2.4.0/saveTrainingCampLastWatch")
    Observable<BaseBean> saveTrainingCampLastWatch(@Field("trainingCampId") String trainingCampId,
                                                   @Field("nodeId") String nodeId,
                                                   @Field("taskId") String taskId);

    /**
     * 直播评论（行为奖励bocc）
     */
    @FormUrlEncoded
    @POST("live/v2.4.0/liveComment")
    Observable<BaseBean> liveCommment(@Field("liveId") String liveId);

    /**
     * 添加用户黑名单
     */
    @FormUrlEncoded
    @POST("live/v2.4.0/saveUserBlacklist")
    Observable<BaseBean> saveUserBlackList(@Field("liveId") String liveId);

    /**
     * 分享邀请有礼海报/分享直播行为奖励
     */
    @FormUrlEncoded
    @POST("integral/v2.4.0/addUserIntegralRecord")
    Observable<BaseBean> addUserIntegralRecord(@Field("type") int type,
                                               @Field("id") String liveId);
}
