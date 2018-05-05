package com.example.home.po;

import com.example.springtemplate.db.mapping.Table;

@Table(name="license_type_units", pk = "license_type_code")
public class LicenseTypeUnitsBean {
    private String licenseTypeCode;
    private String unitsCode;
    private String unitsName;
    private String districtsCode;
    private String districtsName;

    public String getLicenseTypeCode() {
        return licenseTypeCode;
    }

    public void setLicenseTypeCode(String licenseTypeCode) {
        this.licenseTypeCode = licenseTypeCode;
    }

    public String getUnitsCode() {
        return unitsCode;
    }

    public void setUnitsCode(String unitsCode) {
        this.unitsCode = unitsCode;
    }

    public String getUnitsName() {
        return unitsName;
    }

    public void setUnitsName(String unitsName) {
        this.unitsName = unitsName;
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
}
