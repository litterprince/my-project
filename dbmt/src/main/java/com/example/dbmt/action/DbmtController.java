package com.example.dbmt.action;

import com.alibaba.fastjson.JSONObject;
import com.example.dbmt.DBMT;
import com.example.dbmt.entity.DbLink;
import com.example.dbmt.entity.DbMetadata;
import com.example.dbmt.entity.ProjectInfo;
import com.example.dbmt.manager.MetadataManager;
import com.example.dbmt.tool.Constant;
import com.example.dbmt.tool.FilePathUtil;
import com.example.dbmt.tool.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dbmt")
public class DbmtController {
    @Value("${dir.file.download}")
    private String dnpath;
    @Value("${dir.file.download.linux}")
    private String ldnpath;
    @Value("${dir.file.upload}")
    private String upDir;
    @Value("${dir.file.upload.linux}")
    private String upDirLinux;

    @RequestMapping("/index")
    public String index(){
        return "dbmt/compare-database";
    }

    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    public void compare(HttpServletRequest request, HttpServletResponse response){
        String sData = request.getParameter("sData");
        String tData = request.getParameter("tData");

        try{
            DbLink source = JSONObject.toJavaObject(JSONObject.parseObject(sData), DbLink.class);
            DbLink target = JSONObject.toJavaObject(JSONObject.parseObject(tData), DbLink.class);
            if (!DBMT.testConnection(source)) {
                throw new SQLException("基准数据库连接失败");
            }

            if (!DBMT.testConnection(target)) {
                throw new SQLException("目标数据库连接失败");
            }
            DBMT.currentProjectInfo = new ProjectInfo();
            DBMT.currentProjectInfo.setSourceDbLink(source);
            DBMT.currentProjectInfo.setTargetDbLink(target);

            DBMT.currentSourceDbMetadata = DBMT.loadMetadata(source);
            DBMT.currentTargetDbMetadata = DBMT.loadMetadata(target);

            Map rtnMap = compare();

            ResponseUtil.createSuccessResponse(Constant.succState, rtnMap, response);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            ResponseUtil.createErrorResponse(Constant.failState, e.getMessage(), response);
        }
    }

    private Map<String, List<String>> compare() throws Exception {
        List<String> sqlList = DBMT.generaterSql(DBMT.currentSourceDbMetadata, DBMT.currentTargetDbMetadata);
        List<String> needRemoveTables = DBMT.getNeedRemoveTables(DBMT.currentTargetDbMetadata);
        List<String> neddRemoveColumns = DBMT.getNeddRemoveColumns(DBMT.currentTargetDbMetadata);
        Map rtnMap = new HashMap();
        rtnMap.put("sqlList", sqlList);
        rtnMap.put("needRemoveTables", needRemoveTables);
        rtnMap.put("neddRemoveColumns", neddRemoveColumns);
        return rtnMap;
    }

    @RequestMapping(value = "/runSql", method = RequestMethod.POST)
    public void runSql(HttpServletRequest request, HttpServletResponse response){
        try{
            List<String> listSql = DBMT.generaterSql(DBMT.currentSourceDbMetadata, DBMT.currentTargetDbMetadata);
            List<String> errorList = DBMT.excuteSql(listSql, DBMT.currentProjectInfo.getTargetDbLink());
            if (errorList.size() > 0) {
                String errMessage = "";
                for (String s : errorList) {
                    errMessage = errMessage.concat(s + System.lineSeparator());
                }
                ResponseUtil.createErrorResponse(Constant.failState, errMessage, response);
            }else{
                ResponseUtil.createSuccessResponse(Constant.succState, "", response);
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            ResponseUtil.createErrorResponse(Constant.failState, e.getMessage(), response);
        }
    }

    @RequestMapping(value = "/saveProject", method = RequestMethod.POST)
    public void saveProject(HttpServletRequest request, HttpServletResponse response){
        String sData = request.getParameter("sData");
        String tData = request.getParameter("tData");

        String root = dnpath;
        String fileName = "singleInfo.proj";
        String os = System.getProperty("os.name");
        try {
            if(!os.toLowerCase().startsWith("win")) {
                root = ldnpath;
            }
            File dir = new File(root);
            if(!dir.exists()){
                dir.mkdirs();
            }

            ProjectInfo projectInfo = new ProjectInfo();
            DbLink source = JSONObject.toJavaObject(JSONObject.parseObject(sData), DbLink.class);
            projectInfo.setSourceDbLink(source);

            if(StringUtils.isNotBlank(tData)) {
                DbLink target = JSONObject.toJavaObject(JSONObject.parseObject(tData), DbLink.class);
                projectInfo.setTargetDbLink(target);
                fileName = "projectInfo.proj";
            }

            String path = FilePathUtil.getRealFilePath(root + fileName);
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            file.deleteOnExit();
            DBMT.saveProject(file, projectInfo);
            ResponseUtil.createSuccessResponse(Constant.succState, fileName, response);
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            ResponseUtil.createErrorResponse(Constant.failState, e.getMessage(), response);
        }
    }

    @RequestMapping("/metadataIndex")
    public String metadataIndex(){
        return "dbmt/compare-metadata";
    }

    @RequestMapping(value = "/compareMetadata", method = RequestMethod.POST)
    public void compareMetadata(HttpServletRequest request, HttpServletResponse response){
        String sData = request.getParameter("sData");
        String fileName = request.getParameter("fileName");
        String root = upDir;
        String os = System.getProperty("os.name");
        if(!os.toLowerCase().startsWith("win")) {
            root = upDirLinux;
        }

        try{
            File file = new File(root + fileName);
            if(!file.exists()){
                throw new SQLException("元数据文件不存在");
            }
            DbLink target = JSONObject.toJavaObject(JSONObject.parseObject(sData), DbLink.class);
            if (!DBMT.testConnection(target)) {
                throw new SQLException("目标数据库连接失败");
            }

            DBMT.currentProjectInfo = new ProjectInfo();
            DBMT.currentProjectInfo.setTargetDbLink(target);
            DBMT.currentSourceDbMetadata = DBMT.loadMetadata(file);
            DBMT.currentTargetDbMetadata = DBMT.loadMetadata(target);

            Map rtnMap = compare();

            ResponseUtil.createSuccessResponse(Constant.succState, rtnMap, response);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            ResponseUtil.createErrorResponse(Constant.failState, e.getMessage(), response);
        }
    }

    @RequestMapping("/exportIndex")
    public String exportMetadata(){
        return "dbmt/metadata-export";
    }

    @RequestMapping(value = "/exportMetadata", method = RequestMethod.POST)
    public void exportMetadata(HttpServletRequest request, HttpServletResponse response){
        String sData = request.getParameter("sData");

        String root = dnpath;
        String fileName = "metadata.dbmd";
        String os = System.getProperty("os.name");
        try{
            if(!os.toLowerCase().startsWith("win")) {
                root = ldnpath;
            }
            File dir = new File(root);
            if(!dir.exists()){
                dir.mkdirs();
            }

            DbLink source = JSONObject.toJavaObject(JSONObject.parseObject(sData), DbLink.class);
            if (!DBMT.testConnection(source)) {
                throw new SQLException("基准数据库连接失败");
            }

            DbMetadata dbMetadata = MetadataManager.loadMetadata(source);
            String path = FilePathUtil.getRealFilePath(root + fileName);
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            file.deleteOnExit();
            DBMT.saveMetadata(file, dbMetadata);
            ResponseUtil.createSuccessResponse(Constant.succState, fileName, response);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            ResponseUtil.createErrorResponse(Constant.failState, e.getMessage(), response);
        }
    }
}
