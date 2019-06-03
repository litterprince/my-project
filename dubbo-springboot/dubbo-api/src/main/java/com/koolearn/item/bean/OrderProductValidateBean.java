package com.koolearn.item.bean;

import com.koolearn.item.enums.ValidateEnum;

import java.io.Serializable;

/**
 * 退换课验证封装类
 *
 * @author lingjunxlj
 * @date 2018年03月29日 16:49
 */
public class OrderProductValidateBean implements Serializable {

    private static final long serialVersionUID = 5603378396084854486L;

    private ValidateEnum validateEnum;

    private String customerMsg;

    public OrderProductValidateBean() {

    }

    public OrderProductValidateBean(ValidateEnum validateEnum, String customerMsg) {
        this.validateEnum = validateEnum;
        this.customerMsg = customerMsg;
    }

    public ValidateEnum getValidateEnum() {
        return validateEnum;
    }

    public void setValidateEnum(ValidateEnum validateEnum) {
        this.validateEnum = validateEnum;
    }

    public String getCustomerMsg() {
        return customerMsg;
    }

    public void setCustomerMsg(String customerMsg) {
        this.customerMsg = customerMsg;
    }
}
