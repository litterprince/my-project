package com.koolearn.item.bean.common.customerservice;

import com.koolearn.item.bean.OrderProductValidateBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 客户服务验证
 *
 * @author lingjunxlj
 * @date 2018年06月05日 14:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class CheckCondition implements Serializable {

    private static final long serialVersionUID = -4367666900222320369L;

    //订单产品主键id
    private List<Integer> orderProductIdList;
    //代理商id
    private Integer agentId;
    //代理商账号id
    private Integer agentAccountId;
    //代理商ssoid
    private Integer agentSsoId;
    //用户id
    private Integer userId;
    //不考虑换课中 默认需要考虑
    private boolean notConsiderChanging;
    //不考虑协议 默认需要考虑
    private boolean notConsiderUserProtocolApply;
    //校验初始值
    private Map<Integer, OrderProductValidateBean> productValidateEnumMap;

}
