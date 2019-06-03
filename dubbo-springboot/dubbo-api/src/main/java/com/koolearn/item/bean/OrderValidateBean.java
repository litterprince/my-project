package com.koolearn.item.bean;

import com.koolearn.item.enums.ValidateEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OrderValidateBean implements Serializable {

    private static final long serialVersionUID = 2845890082893159254L;

    /**
     * 订单是否显示
     */
    private boolean disPlay;

    /**
     * 订单校验
     */
    private ValidateEnum orderValidateEnum;

    /**
     * 订单产品校验
     */
    private Map<Integer, OrderProductValidateBean> productValidateEnumMap;

    public OrderValidateBean() {

    }

    public OrderValidateBean(boolean disPlay, ValidateEnum orderValidateEnum,
                             Map<Integer, OrderProductValidateBean> productValidateEnumMap) {
        this.disPlay = disPlay;
        this.orderValidateEnum = orderValidateEnum;
        this.productValidateEnumMap = productValidateEnumMap;
    }

    /**
     * 合并map
     * 
     * @param productValidateEnumMap map
     * @return OrderValidateBean
     */
    public OrderValidateBean unionMap(Map<Integer, OrderProductValidateBean> productValidateEnumMap) {
        
        if(this.productValidateEnumMap == null){
            this.productValidateEnumMap = new HashMap<Integer, OrderProductValidateBean>();
        }

        if(productValidateEnumMap != null && productValidateEnumMap.size() > 0) {
            this.productValidateEnumMap.putAll(productValidateEnumMap);
        }
        return this;
    }

    /**
     * 合并bean
     * 
     * @param orderValidateBean bean
     * @return OrderValidateBean
     */
    public OrderValidateBean unionBean(OrderValidateBean orderValidateBean) {
        if (!orderValidateBean.disPlay) {
            return orderValidateBean;
        }

        else if (!this.disPlay) {
            return this;
        } else {
            unionMap(productValidateEnumMap);
        }

        return this;
    }

    /**
     * 获取msg
     * 
     * @return String
     */
    public String getMsg() {
        StringBuilder msg = new StringBuilder();
        if (!isDisPlay()) {
            msg = new StringBuilder(orderValidateEnum.getMessage());
        } else {
            for (int key : productValidateEnumMap.keySet()) {
                msg.append(key).append(":").append(productValidateEnumMap.get(key).getValidateEnum().getMessage()).append(";");
            }
        }
        return msg.toString();
    }

    public boolean isSuccess() {
        return isOnlyOrderSuccess() && (productValidateEnumMap == null || productValidateEnumMap.size() == 0)
                && orderValidateEnum.getValue() == ValidateEnum.SUCCESS.getValue();
    }

    public boolean isOnlyOrderSuccess() {
        return disPlay;
    }

    public boolean isDisPlay() {
        return disPlay;
    }

    public void setDisPlay(boolean disPlay) {
        this.disPlay = disPlay;
    }

    public ValidateEnum getOrderValidateEnum() {
        return orderValidateEnum;
    }

    public void setOrderValidateEnum(ValidateEnum orderValidateEnum) {
        this.orderValidateEnum = orderValidateEnum;
    }

    public Map<Integer, OrderProductValidateBean> getProductValidateEnumMap() {
        return productValidateEnumMap;
    }

    public void setProductValidateEnumMap(Map<Integer, OrderProductValidateBean> productValidateEnumMap) {
        this.productValidateEnumMap = productValidateEnumMap;
    }
}
