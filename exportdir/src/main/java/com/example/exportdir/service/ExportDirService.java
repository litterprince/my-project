package com.example.exportdir.service;

import com.example.exportdir.dao.LicenseDirDao;
import com.example.exportdir.dao.LicenseDirOrgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExportDirService {
    @Autowired
    LicenseDirDao licenseDirDao;
    @Autowired
    LicenseDirOrgDao licenseDirOrgDao;

    public List<Map<String, Object>> findDirAll() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT r.* FROM license_dir r INNER JOIN ( ");
        sb.append("  SELECT CERTIFICATE_NAME_CODE FROM LICENSE_DIR_ORG GROUP BY CERTIFICATE_NAME_CODE ");
        sb.append(" ) t ");
        sb.append(" ON r.CERTIFICATE_NAME_CODE = t.CERTIFICATE_NAME_CODE");
        sb.append(" where r.certificate_directory_status=?");
        return licenseDirDao.find(sb.toString(), new Object[]{"checked"});
    }

    public List<Map<String, Object>> findDirOrgAll() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("select o.* from license_dir_org o");
        sb.append(" left outer join license_dir r on r.CERTIFICATE_NAME_CODE = o.CERTIFICATE_NAME_CODE");
        sb.append(" where r.certificate_directory_status=?");
        return licenseDirOrgDao.find(sb.toString(), new Object[]{"checked"});
    }
}
