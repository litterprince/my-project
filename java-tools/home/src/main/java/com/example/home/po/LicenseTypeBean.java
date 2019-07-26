package com.example.home.po;

import com.example.springtemplate.db.mapping.Table;
import java.util.Date;

@Table(name = "license_type", pk = "license_type_code")
public class LicenseTypeBean {
    private String licenseTypeCode;
    private String licenseTypeName;
    private Integer periodOfValidity;
    private Date fixedPeriodEnd;
    private String licenseTypeDescription;
    private String ownerType;
    private String warningModel;
    private Integer advanceDate;
    private String staticDateBegin;
    private String staticDateEnd;
    private String state;
    private String className;
    private String category;
    private String bechecked;
    private String becopy;
    private String isSynchronous;
    private String authorityCode;
    private String authorityName;
    private String defineRule;
    private String parentTypeCode;
    private Integer version;
    private Date createTime;
    private String incrementId;
    private String subjectName;
    private String subjectId;
    private String approveitemId;
    private String approveitemCode;
    private String approveitemName;
    private String dirCode;

    public String getLicenseTypeCode() {
        return licenseTypeCode;
    }

    public void setLicenseTypeCode(String licenseTypeCode) {
        this.licenseTypeCode = licenseTypeCode;
    }

    public String getLicenseTypeName() {
        return licenseTypeName;
    }

    public void setLicenseTypeName(String licenseTypeName) {
        this.licenseTypeName = licenseTypeName;
    }

    public Integer getPeriodOfValidity() {
        return periodOfValidity;
    }

    public void setPeriodOfValidity(Integer periodOfValidity) {
        this.periodOfValidity = periodOfValidity;
    }

    public Date getFixedPeriodEnd() {
        return fixedPeriodEnd;
    }

    public void setFixedPeriodEnd(Date fixedPeriodEnd) {
        this.fixedPeriodEnd = fixedPeriodEnd;
    }

    public String getLicenseTypeDescription() {
        return licenseTypeDescription;
    }

    public void setLicenseTypeDescription(String licenseTypeDescription) {
        this.licenseTypeDescription = licenseTypeDescription;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBechecked() {
        return bechecked;
    }

    public void setBechecked(String bechecked) {
        this.bechecked = bechecked;
    }

    public String getBecopy() {
        return becopy;
    }

    public void setBecopy(String becopy) {
        this.becopy = becopy;
    }

    public String getIsSynchronous() {
        return isSynchronous;
    }

    public void setIsSynchronous(String isSynchronous) {
        this.isSynchronous = isSynchronous;
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

    public String getDefineRule() {
        return defineRule;
    }

    public void setDefineRule(String defineRule) {
        this.defineRule = defineRule;
    }

    public String getParentTypeCode() {
        return parentTypeCode;
    }

    public void setParentTypeCode(String parentTypeCode) {
        this.parentTypeCode = parentTypeCode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIncrementId() {
        return incrementId;
    }

    public void setIncrementId(String incrementId) {
        this.incrementId = incrementId;
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

    public String getApproveitemId() {
        return approveitemId;
    }

    public void setApproveitemId(String approveitemId) {
        this.approveitemId = approveitemId;
    }

    public String getApproveitemCode() {
        return approveitemCode;
    }

    public void setApproveitemCode(String approveitemCode) {
        this.approveitemCode = approveitemCode;
    }

    public String getApproveitemName() {
        return approveitemName;
    }

    public void setApproveitemName(String approveitemName) {
        this.approveitemName = approveitemName;
    }

    public String getDirCode() {
        return dirCode;
    }

    public void setDirCode(String dirCode) {
        this.dirCode = dirCode;
    }
}
