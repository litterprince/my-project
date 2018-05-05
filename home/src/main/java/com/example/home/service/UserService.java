package com.example.home.service;

import com.example.home.dao.*;
import com.example.home.po.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    @Value("${spring.datasource.dbtype}")
    private String dbtype;
    @Autowired
    LicenseDirDao licenseDirDao;
    @Autowired
    LicenseDirOrgDao licenseDirOrgDao;
    @Autowired
    LicenseTypeDao licenseTypeDao;
    @Autowired
    LicenseTypeUnitsDao licenseTypeUnitsDao;
    @Autowired
    LicenseItemDao licenseItemDao;

    public List<Map<String, Object>> getTypeList(String licenseTypeCode, int count){
        StringBuilder sb = new StringBuilder();
        List<Object> param = new ArrayList<Object>();
        sb.append("select t.license_type_code,t.license_type_name ");
        sb.append(" from license_type t ");
        sb.append(" where t.dir_code is NULL AND t.STATE=? ");
        param.add("effective");
        if(StringUtils.isNotBlank(licenseTypeCode)){
            sb.append(" and t.license_type_code = ? ");
            param.add(licenseTypeCode);
        }
        if(count != -1){
            if(dbtype.equals("oracle")){
                sb.append(" and rownum <= ?");
            }else if(dbtype.equals("mysql")){
                sb.append(" limit ?");
            }
            param.add(count);
        }


        List<Map<String, Object>> list = null;
        try {
            list = licenseDirDao.find(sb.toString(), param.toArray());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return list;
    }

    public int update(String licenseTypeCode){
        int i = 1;
        try {
            String certificateNameCode = UUID.randomUUID().toString();
            LicenseTypeBean licenseTypeBean = licenseTypeDao.find(LicenseTypeBean.class, licenseTypeCode);

            String sql = "select * from license_type_units where license_type_code=?";
            List<LicenseTypeUnitsBean> unitsList = licenseTypeUnitsDao.find(LicenseTypeUnitsBean.class, sql, new Object[]{licenseTypeCode});
            LicenseDirBean licenseDirBean = new LicenseDirBean();
            licenseDirBean.setCertificateNameCode(certificateNameCode);
            LicenseTypeUnitsBean licenseTypeUnitsBean = unitsList.size()>0 ? unitsList.get(0) : null;
            setLicenseDirBean(licenseDirBean, licenseTypeBean, licenseTypeUnitsBean);
            licenseDirDao.save(licenseDirBean);

            sql = "select * from license_approveitems where license_type_code=?";
            List<LicenseItemBean> itemList = licenseItemDao.find(LicenseItemBean.class, sql, new Object[]{licenseTypeCode});
            for(LicenseItemBean licenseItemBean: itemList) {
                LicenseDirOrgBean licenseDirOrgBean = new LicenseDirOrgBean();
                licenseDirOrgBean.setCertificateNameCode(certificateNameCode);
                setLicenseDirOrgBean(licenseDirOrgBean, licenseItemBean, unitsList);
                licenseDirOrgDao.save(licenseDirOrgBean);
            }

            sql = "update license_type set dir_code = ? where license_type_code=?";
            licenseDirDao.update(sql, new Object[]{ certificateNameCode, licenseTypeCode });
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            i = -1;
        }
        return i;
    }

    private void setLicenseDirBean(LicenseDirBean licenseDirBean, LicenseTypeBean licenseTypeBean, LicenseTypeUnitsBean licenseTypeUnitsBean){
        if(licenseTypeBean != null) {
            licenseDirBean.setCertificateName(licenseTypeBean.getLicenseTypeName());
            licenseDirBean.setCategory(licenseTypeBean.getCategory());
            licenseDirBean.setPeriodOfValidity(licenseTypeBean.getPeriodOfValidity());
            licenseDirBean.setCertificateHolderType(licenseTypeBean.getOwnerType());
        }
        if(licenseTypeUnitsBean != null) {
            licenseDirBean.setIssuingOrganizationCode(licenseTypeUnitsBean.getUnitsCode());
            licenseDirBean.setIssuingOrganization(licenseTypeUnitsBean.getUnitsName());
            licenseDirBean.setDistrictsCode(licenseTypeUnitsBean.getDistrictsCode());
            licenseDirBean.setDistrictsName(licenseTypeUnitsBean.getDistrictsName());
        }
        licenseDirBean.setIsCentrallyManage("1");
        licenseDirBean.setCertificateDirectoryStatus("checked");
        licenseDirBean.setSubjectName("行政许可");
        licenseDirBean.setSubjectId("0a3102bc-4a9c-4190-8e91-6892f524c607");
        licenseDirBean.setBechecked("0");
        licenseDirBean.setWarningModel("1");
        licenseDirBean.setAdvanceDate(0);
    }

    private void setLicenseDirOrgBean(LicenseDirOrgBean licenseDirOrgBean, LicenseItemBean licenseItemBean, List<LicenseTypeUnitsBean> unitsList){
        licenseDirOrgBean.setId(UUID.randomUUID().toString());
        if(licenseItemBean != null) {
            licenseDirOrgBean.setIssuingOrganizationCode(licenseItemBean.getUnitsCode());
            licenseDirOrgBean.setIssuingOrganization(licenseItemBean.getUnitsName());
            licenseDirOrgBean.setExCodeOfRelatedItem(licenseItemBean.getApproveitemCode());
            licenseDirOrgBean.setItemId(licenseItemBean.getApproveitemId());
            licenseDirOrgBean.setExNameOfRelatedItem(licenseItemBean.getApproveitemName());
        }
        for(LicenseTypeUnitsBean licenseTypeUnitsBean : unitsList) {
            if(licenseTypeUnitsBean.getUnitsCode() == licenseItemBean.getUnitsCode()) {
                licenseDirOrgBean.setDistrictsCode(licenseTypeUnitsBean.getDistrictsCode());
                licenseDirOrgBean.setDistrictsName(licenseTypeUnitsBean.getDistrictsName());
            }
        }
    }
}
