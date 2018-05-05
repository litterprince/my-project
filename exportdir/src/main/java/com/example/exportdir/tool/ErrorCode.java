package com.example.exportdir.tool;

public enum ErrorCode {

    ERROR(400, "ERROR", "错误！"),
	ID_IS_NULL(400,"ID_IS_NULL", "ID为空!"),
	ENT_CODE_IS_NULL(400,"ENT_CODE_IS_NULL","企业编码为空!"),
	ADMIN_ACCOUNT_IS_NULL(400,"ADMIN_ACCOUNT_IS_NULL"," 企业管理员账号为空!"),

	STATE_FORMAT_ERROR(400,"STATE_FORMAT_ERROR","状态参数格式有误!"),
	STATE_IS_NULL(400,"STATE_IS_NULL","状态为空!"),
	OPT_IS_NULL(400,"OPT_IS_NULL","操作动作为空!");

    // 成员变量
	private int httpStatus;
    private String code;
    private String message;
    private int res_code;

    // 构造方法
    private ErrorCode(int httpStatus, String code, String message) {
    	this.setHttpStatus(200);
    	this.setRes_code(httpStatus);
        this.setCode(code);
		this.setMessage(message);
    }
    private ErrorCode() {
    	this.setHttpStatus(httpStatus);
        this.setCode(code);
		this.setMessage(message);
    }
	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRes_code() {
		return res_code;
	}

	public void setRes_code(int res_code) {
		this.res_code = res_code;
	}

}
