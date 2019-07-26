package com.example.home.po;

import com.example.springtemplate.db.mapping.Table;

import java.math.BigDecimal;

@Table(name="license_approveitems", pk = "license_type_code")
public class LicenseItemBean {
    private String licenseTypeCode;
    private String approveitemCode;
    private String unitsCode;
    private String approveitemName;
    private String useClass;
    private BigDecimal isUseOnly;
    private String approveitemId;
    private String unitsName;

    public String getLicenseTypeCode() {
        return licenseTypeCode;
    }

    public void setLicenseTypeCode(String licenseTypecode) {
        this.licenseTypeCode = licenseTypecode;
    }

    public String getApproveitemCode() {
        return approveitemCode;
    }

    public void setApproveitemCode(String approveitemCode) {
        this.approveitemCode = approveitemCode;
    }

    public String getUnitsCode() {
        return unitsCode;
    }

    public void setUnitsCode(String unitsCode) {
        this.unitsCode = unitsCode;
    }

    public String getApproveitemName() {
        return approveitemName;
    }

    public void setApproveitemName(String approveitemName) {
        this.approveitemName = approveitemName;
    }

    public String getUseClass() {
        return useClass;
    }

    public void setUseClass(String useClass) {
        this.useClass = useClass;
    }

    public BigDecimal getIsUseOnly() {
        return isUseOnly;
    }

    public void setIsUseOnly(BigDecimal isUseOnly) {
        this.isUseOnly = isUseOnly;
    }

    public String getApproveitemId() {
        return approveitemId;
    }

    public void setApproveitemId(String approveitemId) {
        this.approveitemId = approveitemId;
    }

    public String getUnitsName() {
        return unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
    }
}
