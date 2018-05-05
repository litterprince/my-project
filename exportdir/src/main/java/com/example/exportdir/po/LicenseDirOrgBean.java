package com.example.exportdir.po;

import com.example.springtemplate.db.mapping.Table;

@Table(name="license_dir_org")
public class LicenseDirOrgBean {
    private String id;
    private String certificateNameCode;
    private String issuingOrganizationCode;
    private String issuingOrganization;
    private String officeCode;
    private String officeName;
    private String sectionCode;
    private String sectionName;
    private String districtsCode;
    private String districtsName;
    private String exCodeOfRelatedItem;
    private String itemId;
    private String exNameOfRelatedItem;
    private String exIssuingOrganizationType;
    private String exIssuingOrganizationLevel;
    private Integer sequence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCertificateNameCode() {
        return certificateNameCode;
    }

    public void setCertificateNameCode(String certificateNameCode) {
        this.certificateNameCode = certificateNameCode;
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

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getDistrictsCode() {
        return districtsCode;
    }

    public void setDistrictsCode(String districtsCode) {
        this.districtsCode = districtsCode;
    }

    public String getDistrictsName() {
        return districtsName;
    }

    public void setDistrictsName(String districtsName) {
        this.districtsName = districtsName;
    }

    public String getExCodeOfRelatedItem() {
        return exCodeOfRelatedItem;
    }

    public void setExCodeOfRelatedItem(String exCodeOfRelatedItem) {
        this.exCodeOfRelatedItem = exCodeOfRelatedItem;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getExNameOfRelatedItem() {
        return exNameOfRelatedItem;
    }

    public void setExNameOfRelatedItem(String exNameOfRelatedItem) {
        this.exNameOfRelatedItem = exNameOfRelatedItem;
    }

    public String getExIssuingOrganizationType() {
        return exIssuingOrganizationType;
    }

    public void setExIssuingOrganizationType(String exIssuingOrganizationType) {
        this.exIssuingOrganizationType = exIssuingOrganizationType;
    }

    public String getExIssuingOrganizationLevel() {
        return exIssuingOrganizationLevel;
    }

    public void setExIssuingOrganizationLevel(String exIssuingOrganizationLevel) {
        this.exIssuingOrganizationLevel = exIssuingOrganizationLevel;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
