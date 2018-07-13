package com.inspur.service;

/**
 * 将物化的表中的数据转为证照系统正式的数据格式
 * @author chenjiaxu
 */
public interface LicenseTransformService {
	/**
	 * 启动转化过程
	 */
	public void startTransform();
	
}
