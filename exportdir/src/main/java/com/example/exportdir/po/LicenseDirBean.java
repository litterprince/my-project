package com.example.exportdir.po;

import com.example.springtemplate.db.mapping.Table;

import java.util.Date;

@Table(name="license_dir", pk = "certificate_name_code")
public class LicenseDirBean {
    private String certificateNameCode;
    private String certificateName;
    private String exRemark;
    private String issuingOrganizationCode;
    private String issuingOrganization;
    private String shareClass;
    private String shareWay;
    private String updateClass;
    private String serviceClass;
    private String subjectClass;
    private String industryClass;
    private String isCentrallyManage;
    private String category;
    private String certificateDirectoryStatus;
    private String districtsName;
    private String districtsCode;
    private String subjectName;
    private String subjectId;
    private Integer periodOfValidity;
    private String certificateHolderType;
    private String bechecked;
    private String authorityCode;
    private String authorityName;
    private String warningModel;
    private Integer advanceDate;
    private String staticDateBegin;
    private String staticDateEnd;
    private Date fixedPeriodEnd;
    private String certificateType;
    private String certificateTypeCode;
    private String certificateTypeId;
    private String defineOrganization;
    private String defineOrganizationCode;
    private String exDefineOrganizationLevel;
    private String exState;

    public String getCertificateNameCode() {
        return certificateNameCode;
    }

    public void setCertificateNameCode(String certificateNameCode) {
        this.certificateNameCode = certificateNameCode;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getExRemark() {
        return exRemark;
    }

    public void setExRemark(String exRemark) {
        this.exRemark = exRemark;
    }

    public String getIssuingOrganizationCode() {
        return issuingOrganizationCode;
    }

    public void setIssuingOrganizationCode(String issuingOrganizationCode) {
        this.issuingOrganizationCode = issuingOrganizationCode;
    }

    public String getIssuingOrganization() {
        return issuingOrganization;
    }

    public void setIssuingOrganization(String issuingOrganization) {
        this.issuingOrganization = issuingOrganization;
    }

    public String getShareClass() {
        return shareClass;
    }

    public void setShareClass(String shareClass) {
        this.shareClass = shareClass;
    }

    public String getShareWay() {
        return shareWay;
    }

    public void setShareWay(String shareWay) {
        this.shareWay = shareWay;
    }

    public String getUpdateClass() {
        return updateClass;
    }

    public void setUpdateClass(String updateClass) {
        this.updateClass = updateClass;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getSubjectClass() {
        return subjectClass;
    }

    public void setSubjectClass(String subjectClass) {
        this.subjectClass = subjectClass;
    }

    public String getIndustryClass() {
        return industryClass;
    }

    public void setIndustryClass(String industryClass) {
        this.industryClass = industryClass;
    }

    public String getIsCentrallyManage() {
        return isCentrallyManage;
    }

    public void setIsCentrallyManage(String isCentrallyManage) {
        this.isCentrallyManage = isCentrallyManage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCertificateDirectoryStatus() {
        return certificateDirectoryStatus;
    }

    public void setCertificateDirectoryStatus(String certificateDirectoryStatus) {
        this.certificateDirectoryStatus = certificateDirectoryStatus;
    }

    public String getDistrictsName() {
        return districtsName;
    }

    public void setDistrictsName(String districtsName) {
        this.districtsName = districtsName;
    }

    public String getDistrictsCode() {
        return districtsCode;
    }

    public void setDistrictsCode(String districtsCode) {
        this.districtsCode = districtsCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getPeriodOfValidity() {
        return periodOfValidity;
    }

    public void setPeriodOfValidity(Integer periodOfValidity) {
        this.periodOfValidity = periodOfValidity;
    }

    public String getCertificateHolderType() {
        return certificateHolderType;
    }

    public void setCertificateHolderType(String certificateHolderType) {
        this.certificateHolderType = certificateHolderType;
    }

    public String getBechecked() {
        return bechecked;
    }

    public void setBechecked(String bechecked) {
        this.bechecked = bechecked;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getWarningModel() {
        return warningModel;
    }

    public void setWarningModel(String warningModel) {
        this.warningModel = warningModel;
    }

    public Integer getAdvanceDate() {
        return advanceDate;
    }

    public void setAdvanceDate(Integer advanceDate) {
        this.advanceDate = advanceDate;
    }

    public String getStaticDateBegin() {
        return staticDateBegin;
    }

    public void setStaticDateBegin(String staticDateBegin) {
        this.staticDateBegin = staticDateBegin;
    }

    public String getStaticDateEnd() {
        return staticDateEnd;
    }

    public void setStaticDateEnd(String staticDateEnd) {
        this.staticDateEnd = staticDateEnd;
    }

    public Date getFixedPeriodEnd() {
        return fixedPeriodEnd;
    }

    public void setFixedPeriodEnd(Date fixedPeriodEnd) {
        this.fixedPeriodEnd = fixedPeriodEnd;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateTypeCode() {
        return certificateTypeCode;
    }

    public void setCertificateTypeCode(String certificateTypeCode) {
        this.certificateTypeCode = certificateTypeCode;
    }

    public String getCertificateTypeId() {
        return certificateTypeId;
    }

    public void setCertificateTypeId(String certificateTypeId) {
        this.certificateTypeId = certificateTypeId;
    }

    public String getDefineOrganization() {
        return defineOrganization;
    }

    public void setDefineOrganization(String defineOrganization) {
        this.defineOrganization = defineOrganization;
    }

    public String getDefineOrganizationCode() {
        return defineOrganizationCode;
    }

    public void setDefineOrganizationCode(String defineOrganizationCode) {
        this.defineOrganizationCode = defineOrganizationCode;
    }

    public String getExDefineOrganizationLevel() {
        return exDefineOrganizationLevel;
    }

    public void setExDefineOrganizationLevel(String exDefineOrganizationLevel) {
        this.exDefineOrganizationLevel = exDefineOrganizationLevel;
    }

    public String getExState() {
        return exState;
    }

    public void setExState(String exState) {
        this.exState = exState;
    }
}
