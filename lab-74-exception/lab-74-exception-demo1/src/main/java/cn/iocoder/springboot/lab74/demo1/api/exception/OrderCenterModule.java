package cn.iocoder.springboot.lab74.demo1.api.exception;


public enum OrderCenterModule {

    /**
     * 订单
     */
    ORDER(100),

    /**
     * 订单用户
     */
    MEMBER(101),

    /**
     * 订单变更
     */
    CHANGE(102),

    /**
     * 订单详情
     */
    DETAIL(103),

    /**
     * 支付
     */
    PAYMENT(200),

    /**
     * 退款
     */
    REFUND(201),

    /**
     * 雇佣
     */
    EMPLOY(300),

    /**
     * 执行
     */
    EXECUTE(301),

    /**
     * 薪资
     */
    SALARY(302),


    /**
     * 订单合同
     */
    ORDER_CONTRACT(400),

    /**
     * 订单协议
     */
    ORDER_AGREEMENT(401),


    /**
     * 合同
     */
    CONTRACT(500),

    /**
     * 合同用户
     */
    CONTRACT_MEMBER(501),

    /**
     * 协议
     */
    AGREEMENT(502),

    /**
     * 服务
     */
    SERVE_MODEL(510),

    /**
     * 开票
     */
    INVOICE(800),

    /**
     * 杂费
     */
    EXPENSES(600),

    /**
     * 交易系统
     */
    TRADE_CENTER(700),

    /**
     * 电子收据
     */
    RECEIPT(900);

    private Integer code;

    OrderCenterModule(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
