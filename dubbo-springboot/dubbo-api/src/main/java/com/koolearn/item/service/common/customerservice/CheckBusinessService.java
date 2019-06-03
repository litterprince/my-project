package com.koolearn.item.service.common.customerservice;

import com.koolearn.item.bean.OrderValidateBean;
import com.koolearn.item.bean.common.customerservice.CheckCondition;

/**
 * 客户服务通用校验接口
 *
 * @author lingjunxlj
 * @date 2018年06月04日 16:53
 */
public interface CheckBusinessService {

    /**
     * 校验所有情况 - 代理商
     *
     * @param checkCondition 参数封装
     * @return Result -> isSuccess()
     */
    OrderValidateBean validateForAgent(CheckCondition checkCondition);

    /**
     * 校验所有情况 - 代理商无理由退课
     *
     * @param checkCondition 参数封装
     * @return Result -> isSuccess()
     */
    OrderValidateBean validateForAgentNoReason(CheckCondition checkCondition);

    /**
     * 校验所有情况 - 客服
     *
     * @param checkCondition 参数封装
     * @return Result -> isSuccess()
     */
    OrderValidateBean validateForCustomer(CheckCondition checkCondition);

    /**
     * 校验所有情况 - 前台学员
     *
     * @param checkCondition 参数封装
     * @return Result -> isSuccess()
     */
    OrderValidateBean validateForUser(CheckCondition checkCondition);

    /**
     * 后台无理由退课
     *
     * @param checkCondition 参数封装
     * @return Result -> isSuccess()
     */
    OrderValidateBean validateNoAuditForCustomer(CheckCondition checkCondition);

}
