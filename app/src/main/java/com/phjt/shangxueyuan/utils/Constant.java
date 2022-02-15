package com.phjt.shangxueyuan.utils;

/**
 * @author: gengyan
 * date:    2020/3/24 14:58
 * company: 普华集团
 * description: 常量类
 */
public class Constant {

    /**
     * --------------------------------------常量值-------------------------------------
     */

    /**
     * 登录过期
     */
    public static final int BUSINESS_CODE_FORCE_OFFLINE = 90002;
    /**
     * token为空
     */
    public static final int BUSINESS_CODE_IS_NULL = 90001;
    /**
     * token错误，请重新登录
     */
    public static final int BUSINESS_CODE_ERROR = 90000;

    /**
     * token失效
     */
    public static final int LOGOUT_CODE_ERROR = 66666;

    /**
     * 微信号
     */
    public static final String WECHAT_NUMBER = "Puhua-BOC";

    public static final int VERIFICATIONCODE_TYPE_REG = 2;
    public static final int VERIFICATIONCODE_TYPE_SMS = 1;
    public static final int VERIFICATIONCODE_TYPE_FORGOT_PWD = 3;
    public static final int VERIFICATIONCODE_TYPE_BIND = 4;
    public static final String H5_AGREEMENT = "agreement";
    public static final String H5_PRIVACY = "privacy";
    public static final String H5_WITHDRAWAL_RULES = "withdrawalRules";
    public static final String H5_CERTIFICATE = "certificate";
    public static final String H5_TEACHING = "teaching";
    public static final String H5_FUND = "fund";
    public static final String H5_VIP = "vip";
    public static final String H5_DETAILS = "certificate/details";
    public static final String H5_REFERENCE = "reference";
    public static final String H5_FATSALARY = "fatSalary";
    public static final int MAX_PICTURE_NUMBER = 9;

    public static final String H5_COURSE_COMMENT_DETAIL = "livePlayback/dynamic";

    public static final int TX_APP_ID = 1300105446;

    /**
     * 微信、支付宝 支付成功
     */
    public static final String ORDER_PAY_SUCCESS = "订单兑换成功";
    /**
     * ---------------------------------------SP 值-------------------------------------
     */
    public static final String SP_TOKEN = "token";
    public static final String WECHAT_APPID = "wx42657b4ea7624503";
    public static final String WECHAT_SECRET = "7ba871482266a6ca1cc2995ae5d27363";
    /**
     * 设置在wifi下看视频
     */
    public static final String SP_SET_WIFI_DOWNLOAD = "sp_set_wifi_download";
    /**
     * 课程描述 是否显示
     */
    public static final String COURSE_SHOW_DES = "course_show_des";
    /**
     * 保存缓存随机数
     */
    public static final String SP_CACHE_DATA = "SP_CACHE_DATA";
    /**
     * 手机号
     */
    public static final String SP_MOBILE = "mobile";
    public static final String SP_TEMP_LOGOUT = "logout_temp";
    /**
     * 首页初级、中级、高级 课程id
     */
    public static final String SP_PRIMARY_COURSE_ID = "sp_primary_course_id";
    public static final String SP_MIDDLE_COURSE_ID = "sp_middle_course_id";
    public static final String SP_HIGH_COURSE_ID = "sp_high_course_id";
    /**
     * 首页精品试听id
     */
    public static final String SP_PRACTICE_ID = "sp_practice_id";
    /**
     * 首页活动 - 当天时间记录
     */
    public static final String SP_CURRENT_DAY = "sp_current_day";
    /**
     * 首页直播列表id
     */
    public static final String SP_LIVE_LIST_ID = "sp_live_list_id";
    /**
     * 用户id
     */
    public static final String SP_USER_ID = "sp_user_id";
    /**
     * 用户昵称
     */
    public static final String SP_NICK_NAME = "sp_nick_name";
    /**
     * 用户头像
     */
    public static final String SP_AVATAR = "sp_avatar";

    /**
     * 倒计时30天
     */
    public static final String SP_COUNT_DOWN_DAY = "sp_count_down_day";

    /**
     * --------------------------------------Bundle 值-------------------------------------
     */
    /**
     * 课程视频模块
     */
    public static final String BUNDLE_COURSE_ID = "bundle_course_id";
    public static final String BUNDLE_COURSE_FREE_TYPE = "bundel_course_free_type";
    public static final String BUNDLE_COURSE_PLAY_PERMISSION = "bundle_course_play_permission";
    public static final String BUNDLE_COURSE_PUNCHCARDNUM = "bundle_course_punchCardNum";
    public static final String BUNDLE_COURSE_EXERCISENUM = "bundle_course_exerciseNum";
    public static final String BUNDLE_COURSE_TYPE = "courseType";
    /**
     * 星球传值
     */
    public static final String BUNDLE_PLANET_PHONE = "planet_phone";
    public static final String BUNDLE_LINK_TYPE = "link_type";
    public static final String BUNDLE_LINK_ADDRESS = "link_address";

    /**
     * 笔记中 点击观看时间赋值
     */
    public static final String BUNDLE_COURSE_WATCH_TIME = "bundle_course_watch_time";
    /**
     * 是否定位到笔记tab
     */
    public static final String BUNDLE_COURSE_TO_COMMENT = "bundle_course_to_comment";
    public static final String BUNDLE_ANSWER_TO_COMMENT = "bundle_answer_to_comment";
    /**
     * 是否定位到评论tab
     */
    public static final String BUNDLE_COURSE_TO_NOTES = "bundle_course_to_notes";
    /**
     * 课程小节id
     */
    public static final String BUNDLE_POINT_ID = "bundle_pointId";
    public static final String BUNDLE_POINT_PAGE = "bundle_point_page";
    public static final String BUNDLE_COURSE_NAME = "bundle_course_name";
    public static final String BUNDLE_COURSE_BEAN = "bundle_course_bean";
    public static final String BUNDLE_POINT_NAME = "bundle_point_name";

    /**
     * 下载的本视频地址
     */
    public static final String BUNDLE_LOCAL_PATH = "bundle_local_path";

    /**
     * 一级分类名字 、 简介
     */
    public static final String BUNDLE_LEVEL_NAME = "bundle_level_name";
    public static final String BUNDLE_LEVEL_DESC = "bundle_level_desc";
    public static final String BUNDLE_LEVEL_URL = "bundle_level_url";
    public static final String ISLIVEPLAYBACK = "isLivePlayback";

    /**
     * 首页蒙层
     */
    public static final String BUNDLE_HOMPAGE_BG = "bundle_hompage_bg4";

    /**
     * 圈子蒙层
     */
    public static final String BUNDLE_CIRCLE_BG = "bundle_circle_bg4";

    /**
     * 问答蒙层
     */
    public static final String BUNDLE_QUESTIONS_BG = "bundle_questions_bg";

    /**
     * 个人中心蒙层
     */
    public static final String BUNDLE_MINE_BG = "bundle_mine_bg";
    /**
     * 阅览协议
     */
    public static final String AGREEMENT = "agreement_new";

    /**
     * WebView 的 title 和 URl
     */
    public static final String BUNDLE_WEB_TITLE = "web_title";
    public static final String BUNDLE_WEB_URL = "web_url";
    public static final String BUNDLE_WEB_CODE_DATA = "web_code_data";
    public static final String BUNDLE_WEB_SHOW_SHARE = "web_show_share";

    /**
     * 引导页获取展示状态
     */
    public static final String GO_GUIDE = "go_guide";

    /**
     * H5页面地址
     */
    //VIP页面
    public static final String WEB_VIP_PAGE = "vip";
    //精英
    public static final String WEB_ELITE_PAGE = "elite";

    /**
     * 课程：栏目一级分类id
     */
    public static final String COURSE_TYPE_ID = "course_type_id";
    /**
     * 课程：二级分类id
     */
    public static final String COURSE_TYPE_SECOND_ID = "course_type_second_id";


    /**
     * 资料文件路径
     */
    public static final String FILE_SAVE_PATH = "file_save_path";
    /**
     * 友盟 UMENG_CHANNEL
     */
    public static final String UMENG_CHANNEL = "UMENG_CHANNEL";

    /**
     * 钱包模块
     */
    /**
     * 提现记录 id
     */
    public static final String BUNDLE_WITHDRAWAL_ID = "bundle_withdrawal_id";
    /**
     * 用户资产
     */
    public static final String BUNDLE_USER_ASSET = "bundle_user_asset";
    /**
     * 最低提现金额
     */
    public static final String BUNDLE_MINIMUM_WITHDRAWAL = "bundle_minimum_withdrawal";
    /**
     * 银行卡实体
     */
    public static final String BUNDLE_BANKCARD_INFO = "bundle_bankcard_info";

    /**
     * 查看大图的list
     */
    public static final String BUNDLE_BIG_IMAGE_URLS = "image_urls";

    /**
     * 查看大图的当前角标
     */
    public static final String BUNDLE_BIG_IMAGE_POSITION = "image_position";

    /**
     * 查看大图的型号
     */
    public static final String BUNDLE_BIG_IMAGE_TYPE = "image_pyte";
    /**
     * 查看大图的前缀
     */
    public static final String BUNDLE_BIG_IMAGE_PRE = "image_pre";

    /**
     * 话题
     */
    public static final String BUNDLE_TOPIC_ID = "topicId";
    public static final String BUNDLE_TOPIC_TITLE = "bundle_topic_title";
    public static final String BUNDLE_TOPIC_CONTENT = "bundle_topic_content";
    public static final String BUNDLE_TOPIC_BG = "bundle_topic_bg";

    /**
     * 订单支付页
     */
    public static final String BUNDLE_ORDER_COMMODITYID = "commodityId";
    public static final String BUNDLE_ORDER_NAME = "name";
    public static final String BUNDLE_ORDER_REALPRICE = "realPrice";
    public static final String BUNDLE_ORDER_CARDTYPE = "cardType";
    public static final String BUNDLE_ORDER_ACTIVITYSTATE = "activityState";
    public static final String BUNDLE_ORDER_TYPE = "bundle_order_type";


    /**
     * 参与打卡-课程或专栏ID
     */
    public static final String PUNCH_CARDS_COURSE_ID = "punchCardsCourseId";
    /**
     * 参与打卡-打卡ID
     */
    public static final String PARTICIPATION_PUNCH_CARDSID = "participationPunchCardsId";
    /**
     * 日记ype
     */
    public static final String PUNCHM_TYPE = "punchmType";


    /**
     * 日记发布-日记ID
     */
    public static final String BUNDLE_ADD_DIARY_ID = "bundleAddDiaryId";
    /**
     * 日记发布-打卡ID
     */
    public static final String BUNDLE_ADD_PUNCH_CARD_ID = "punchCardId";

    /**
     * 日记发布-主题ID
     */
    public static final String BUNDLE_ADD_MOTIF_ID = "motifId";
    /**
     * 日记发布-打卡日期
     */
    public static final String BUNDLE_ADD_CALENDAR_DATE = "calendarDate";
    /**
     * 日记发布-打卡的内容
     */
    public static final String BUNDLE_ADD_CONTENT = "diaryContent";
    /**
     * 日记发布-主题标题
     */
    public static final String BUNDLE_ADD_MOTIF_TITLE = "motifTitle";

    /**
     * 日记发布-再次修改发布
     */
    public static final String BUNDLE_ADD_ONCE_MORE = "onceMore";
    /**
     * 日记发布-补卡(0.正常 1.补卡）
     */
    public static final String BUNDLE_ADD_REISSUE_CARD_TYPE = "reissueCardType";

    /**
     * 日记发布-小节的ID
     */
    public static final String BUNDLE_ADD_NODE_TASK_LINK_ID = "nodeTaskLinkId";
    /**
     * 日记发布-训练营ID
     */
    public static final String BUNDLE_ADD_TRAINING_CAMP_ID = "trainingCampId";


    /**
     * 日记发布-评论ID
     */
    public static final String BUNDLE_ADD_COMMENT_ID = "addCommentId";

    /**
     * 训练营id
     */
    public static final String BUNDLE_TRAINING_CAMP_ID = "bundle_training_camp_id";
    /**
     * 训练营实体
     */
    public static final String BUNDLE_TRAINING_DETAIL_BEAN = "bundle_training_detail_bean";


    /**
     * 视频播放实体
     */
    public static final String BUNDLE_TRAINING_PLAY_BEAN = "bundle_training_play_bean";

    /**
     * 训练营 任务id
     */
    public static final String BUNDLE_TRAINING_SECOND_ID = "bundle_training_second_id";

    /**
     * 训练营 评论id
     */
    public static final String BUNDLE_TRAINING_COMMENT_BEAN = "bundle_training_comment_bean";

    /**
     * 训练营-评论-详情页 CODE
     */
    public static final int COMMENT_DETAIL_REQUEST_CODE = 1000;
    public static final int COMMENT_DETAIL_RESULT_CODE = 1001;

    /**
     * 客服地址
     */
    public static final String CUSTOMER_SERVICE_URL = "http://p.qiao.baidu.com/cps/chat?siteId=15838843&userId=10887419&siteToken=ea3d499f1b724f347aff01ee0051fbcf";
    /**
     * 启富通客服地址
     */
    public static final String Qiitong_SERVICE_URL = "http://p.qiao.baidu.com/cps/chat?siteId=16792525&userId=10887419&siteToken=ae7f9a1dfc87eb15053626616b26743d&cp=BOCh5&cr=BOCh5&cw=BOCh5";
    /**
     * 消息标题
     */
    public static final String MESSAGE_HEADER = "message_header";
    /**
     * 消息类型
     */
    public static final String MESSAGE_TYPE = "message_type";
    /**
     * 训练营类型
     */
    public static final String BUNDLE_TRAINING_MESSAGE_TYPE = "bundle_training_message_type";
    /**
     * 消息Id
     */
    public static final String MESSAGE_ID = "message_id";
    /**
     * 跳转到首页
     */
    public static final String PUSH_HOME = "push_home";

    /**
     * 总积分
     */
    public static final String USER_INTEGRAL = "user_Integral";
    /**
     * 兑换礼券
     */
    public static final String EXCHANGE_VOUCHER_TYPE = "exchange_voucher_type";
    /**
     * 消息跳入兑换礼券
     */
    public static final String MESSAGE_IN_VOUCHER_TYPE = "message_in_voucher_type";


    /**
     * 推送——常量
     * Alias
     * phone：手机号标识特定用户
     * Tags
     * login：标识是否已登录
     */
    public static final String PUSH_IS_LOGIN = "login";
    public static final String PUSH_PHONE = "phone";

    //发布动态回调
    public static final String PUSH_TOPICID_CALLBACK = "topicIdCallback";

    //发布动态
    public static final String PUSH_TOPICID = "topicId";
    public static final String PUSH_TOPICNAME = "topicName";
    /**
     * 积分规则
     */
    public static final String INTEGRAL_RULES = "integrationRule";

    /**
     * 考生信息
     */
    public static final String CANDIDATE_INFORMATION = "examSystem/examInfo?status=1";

    /**
     * 我的订单
     */
    public static final String MY_ORDER = "examSystem/other?num=2";

    /**
     * 我的证书
     */
    public static final String MY_CERTIFICATE = "examSystem/other?num=3";

    /**
     * 考试系统
     */
    public static final String EXAMINATION_SYSTEM = "examSystem/exam";

    /**
     * 帮助中心
     */
    public static final String HELP_CENTER = "issue";

    /**
     * 训练营 视频播放完成-刷新当前实体
     */
    public static final String EVENT_TRAINING_CAMP = "event_training_camp";

    //作业本id
    public static final String BUNDLE_EXERCISE_ID = "exerciseId";
    public static final String BUNDLE_EXERCISE_BOOK_ID = "exerciseBookId";
    public static final String BUNDLE_NODE_TASK_ID = "bundle_node_task_id";

    /**
     * 是否是修改作业
     */
    public static final String BUNDLE_EXERCISE_UPDATE = "bundle_exercise_update";

    /**
     * fragment的传递的type
     */
    public static final String FRAGMENT_TYPE = "type_id";
    /**
     * 身份认证状态
     */
    public static final String CERTIFICATION_STATUS = "Certification_Status";


    /**
     * 判断手机是否root
     */
    public static final String SP_IS_ROOT = "SP_IS_ROOT";
    /**
     * 启富通首页
     */
    public static final String QIITONG_HOME = "home";

    /**
     * 启富通积分兑换
     */
    public static final String QIITONG_INTEGRAL = "integral";


    /**
     * 导师解答疑
     */
    public static final int INDEX_TUTOR_ANSWER = 0;
    /**
     * 精选解答
     */
    public static final int INDEX_SELECTED_ANSWER = 1;
    /**
     * 我的提问
     */
    public static final int INDEX_MINE_QUESTION = 2;
    /**
     * 我的咨询
     */
    public static final int INDEX_MY_CONSULTATION = 3;

    /**
     * 设置字号大小
     */
    public static final String SP_SET_TEXT_SIZE = "sp_set_text_size";
    /**
     * 导师ID
     */
    public static final String TUTOR_ID = "tutorId";
    /**
     * 问题id
     */
    public static final String QUESTION_ID = "questionId";

    /**
     * 确认订单页  商品类型 1.vip 2.大专栏 3.训练营
     */
    public static final String BUNDLE_ORDER_COMMODITY_TYPE = "bundle_order_commodity_type";
    /**
     * 商品类型 虚拟商品/实物商品
     */
    public static final String BUNDLE_MALL_COMMODITY_TYPE = "bundle_mall_commodity_type";
}
