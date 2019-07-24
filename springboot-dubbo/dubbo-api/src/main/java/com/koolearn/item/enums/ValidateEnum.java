package com.koolearn.item.enums;

/**
 *
 * @author MHW
 * @date 2018年02月5日 13:30
 * @since
 */
public enum ValidateEnum {

    //通用

    REFRESH(-1, "刷新页面", 0, 0),
    SUCCESS(0, "成功", 0, 0),
    PRAMS_ERROR(1, "参数错误!", 0, 0),
    ERROR(2, "失败", 0, 0),

    //订单不让退
    REFUNDABLE_ORDER_INFO_ERROR(3,"订单信息不存在", 0, 0),
    REFUNDABLE_ORDER_STATUS_ERROR(4,"该课程当前处于异常状态（退课，冻结等），不可进行退换！", 0, 0),
    REFUNDABLE_ORDER_INVOICE_ERROR(5,"该课程的发票正在开具中，不可发起退换，请半小时后重试~", 0, 0),
    REFUNDABLE_ORDER_IS_REFUNDING_ERROR(6,"该课程正在退款/换课中，不可发起新的退换申请！", 0, 0),
    REFUNDABLE_ORDER_IS_CHANGING_ERROR(7,"该课程正在退款/换课中，不可发起新的退换申请！", 0, 0),
    REFUNDABLE_ORDER_IS_100_GOLD(8,"100%含金量的订单，请通过官网客服入口进行退换课", 0, 0),
    REFUNDABLE_ORDER_AGENT_CHECK_AMOUNT(9,"代理商退课非代理商支付订单金额校验失败", 0, 0),
    REFUNDABLE_ORDER_GIFT_NUM_ERROR(10,"该课程的订单含有赠品课程，仅支持整单退换！", 0, 0),
    REFUNDABLE_ORDER_PAYWAY_BAIDU_ERROR(63,"百度有钱花只允许整单退款！", 0, 0),
    REFUNDABLE_ORDER_PAYWAY_HUAWEI_ERROR(64,"华为支付只允许整单退款！", 0, 0),
    REFUNDABLE_ORDER_JD_PAY_ERROR(21,"京东白条支付的课程，售后服务请联系在线客服 或 400-690-5751", 2, 1),
    REFUNDABLE_ORDER_BD_PAY_ERROR(65,"百度分期支付的课程，售后服务请联系在线客服 或 400-690-5751", 2, 1),
    REFUNDABLE_ORDER_HUAWEI_ERROR(66,"华为支付的课程，售后服务请联系在线客服 或 400-690-5751", 2, 1),
    REFUNDABLE_ORDER_KACCOUNT_ERROR(67,"K币支付的课程，售后服务请联系在线客服 或 400-690-5751", 2, 1),


    //产品不让退
    REFUNDABLE_PRODUCT_INFO_ERROR(11,"订单产品信息不存在", 0, 0),
    REFUNDABLE_PRODUCT_STATUS_ERROR(12,"该课程当前处于异常状态（退课，冻结等），不可进行退换！", 0, 0),
    REFUNDABLE_PRODUCT_CLASS_START_ERROR(13,"订单有产品未开课无法进行售后!", 0, 0),
    REFUNDABLE_PRODUCT_APPLY_FOR_GIFT(14,"该课程正在申请延期，不可同时进行退换课！", 0, 0),
    REFUNDABLE_PRODUCT_XB_CARD_ERROR(15,"该课程参加了学霸卡返学费活动，不可进行退换！", 0, 0),
    REFUNDABLE_PRODUCT_AGREEMENT_ERROR(16,"该课程已经申请协议重读或退费，不可再进行退换课！", 0, 0),
    REFUNDABLE_PRODUCT_ENGAGEMENT_ERROR(17,"该产品有正在预约中的辅导服务，请将服务取消或使用后再申请退换课！", 0, 0),
    REFUNDABLE_PRODUCT_COST_MODIFY_ERROR(18,"系统正在处理当前产品成本变更数据，请稍后再申请退换课", 0, 0),
    REFUNDABLE_PRODUCT_STATUS_FREEZING_ERROR (18,"课程冻结中,无法退换课", 0, 0),
    REFUNDABLE_PRODUCT_STATUS_K12_ERROR (68,"该产品使用了联报优惠,不可先行退课。", 0, 0),


    //换课相关

    //通用校验

    CHANGEABLE_ORDER_ZERO_PRICE_ERROR(20,"0元的订单，不支持无理由退换课", 2, 0),
    CHANGEABLE_PRODUCT_NEW_NULL_ERROR(22,"换购产品信息不存在", 2, 0),
    CHANGEABLE_PRODUCT_NEW_OLD_ERROR(23,"新换购产品不能和老产品相等", 2, 0),
    CHANGEABLE_PRODUCT_NEW_PRICE_ZERO(24,"0元产品不参与换课", 2, 0),
    CHANGEABLE_PRODUCT_PRODUCT_NUM_ERROR(25,"换购课程与购买课程数不同", 2, 0),
    CHANGEABLE_PRODUCT_GIVE_ERROR(26,"赠品不能换课", 2, 0),
    CHANGEABLE_ORDER_FROM_AGENT_ERROR(40,"非代理商支付的课程，售后服务请联系学员", 2, 1),
    CHANGEABLE_PRODUCT_KX_ERROR(41,"酷学产品暂不支持换课", 2, 0),


    //前台

    CHANGEABLE_ORDER_SECOND_ERROR(30,"换课产生的新订单，不支持再次无理由退换课", 2, 1),
    CHANGEABLE_ORDER_AGENT_ERROR(31,"代理商支付的课程，售后服务请联系购课代理商", 2, 1),
    CHANGEABLE_ORDER_ERROR(32,"只能操作自己的订单!", 2, 1),
    CHANGEABLE_ORDER_DATE_ERROR(33,"课程已过七天售后时限，如有疑问，请联系在线客服 400-690-5751", 2, 1),
    CHANGEABLE_PRODUCT_LIVE_LT2_ERROR(27,"该课程只有一次直播，直播开始后不支持无理由退换课", 2, 1),
    CHANGEABLE_PRODUCT_LIVE_GT2_ERROR(28,"第二次直播开始后不支持无理由退换课", 2, 1),
    CHANGEABLE_PRODUCT_NOCAN_CHANGE(35,"此课程暂不支持无理由换课，如有疑问，请联系客服 400-690-5751", 2, 1),
    CHANGEABLE_PRODUCT_CLASS_TYPE_ERROR(29,"服务类课程，不支持无理由退换课，如有疑问，请联系您的购课顾问", 2, 1),
    CHANGEABLE_ORDER_TRANSACTION_RECORD_ERROR(34,"到款未确认！ 请联系客服确认到款后再申请换课  ~ ", 2, 1),
    REFUNDABLE_PRODUCT_CONTAINS_CH (35,"该订单不支持自助退换", 0, 0),

    //代理商
    CHANGEABLE_ORDER_AGENT_BOOKSPRICE_ERROR(50,"已产生资料费的课程，不支持无理由退换  ~ ", 2, 1),
    CHANGEABLE_ORDER_AGENT_46LEVEL_ERROR(51,"已报备四六级订单，换课时，仅可选择换购英语四级和英语六级课程 ~ ", 2, 0),
    CHANGEABLE_ORDER_AGENT_KAOYAN_ERROR(52,"已报备全程班、直通车、全科全程班、全科直通车订单，换课时，仅可选择换购考研产品线课程 ~ ", 2, 0),



    //代理商
    CHANGEABLE_ORDER_AGENT_TM_ERROR(61,"天猫旗舰店购课，售后请联系天猫旗舰店", 2, 1),
    CHANGEABLE_ORDER_AGENT_JD_ERROR(62,"京东旗舰店购课，售后请联系京东旗舰店", 2, 1);
    private int value;

    private String message;

    /**
     * 通用:0;退课专门:1;换课专门:2
     */
    private int type;

    /**
     * 通用:0;前台校验:1;后台校验:2
     */
    private int source;


    ValidateEnum(int value, String message, int type, int source) {
        this.value = value;
        this.message = message;
        this.type = type;
        this.source = source;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
