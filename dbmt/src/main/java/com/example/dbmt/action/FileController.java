package com.example.dbmt.action;

import com.alibaba.fastjson.JSONObject;
import com.example.dbmt.DBMT;
import com.example.dbmt.tool.Constant;
import com.example.dbmt.tool.FilePathUtil;
import com.example.dbmt.tool.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController {
    @Value("${dir.file.download}")
    private String downDir;
    @Value("${dir.file.download.linux}")
    private String downDirLinux;
    @Value("${dir.file.upload}")
    private String upDir;
    @Value("${dir.file.upload.linux}")
    private String upDirLinux;

    @RequestMapping("/download/{fileName:[a-zA-Z0-9\\.\\-\\_]+}")
    public void download(@PathVariable("fileName")String fileName, HttpServletResponse response){
        String root = downDir;
        String os = System.getProperty("os.name");
        if(!os.toLowerCase().startsWith("win")) {
            root = downDirLinux;
        }
        String path = FilePathUtil.getRealFilePath(root + fileName);
        File file = new File(path);

        if (file.exists()) {
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream output = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    output.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @RequestMapping(value="/uploadProjFile", method = RequestMethod.POST)
    public void uploadProjFile(@RequestParam("file") MultipartFile file, HttpServletResponse response){
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        File f = null;
        try{
            String fileName = file.getOriginalFilename();//原文件名称
            String fileNameTemp = fileName;
            int endIndex = fileNameTemp.lastIndexOf(".");
            if (endIndex <= 0) {
                throw new Exception("请上传proj文件!");
            } else {
                String rightName = fileNameTemp.substring(endIndex+1, fileName.length());
                if (!("proj".equals(rightName.toLowerCase()))) {
                    throw new Exception("请上传proj文件!");
                }
            }

            f = File.createTempFile("tmp", null);
            file.transferTo(f);
            DBMT.currentProjectInfo = DBMT.loadProject(f);
            f.deleteOnExit();

            ResponseUtil.createSuccessResponse(Constant.succState, JSONObject.toJSONString(DBMT.currentProjectInfo), response);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            ResponseUtil.createErrorResponse(Constant.failState, e.getMessage(), response);
        }
    }

    @RequestMapping(value="/uploadMetadataFile", method = RequestMethod.POST)
    public void uploadMetadataFile(@RequestParam("file") MultipartFile file, HttpServletResponse response){
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        String root = upDir;
        String os = System.getProperty("os.name");
        if(!os.toLowerCase().startsWith("win")) {
            root = upDirLinux;
        }
        try{
            String fileName = file.getOriginalFilename();//原文件名称
            String path = FilePathUtil.getRealFilePath(root + fileName);
            int endIndex = fileName.lastIndexOf(".");
            if (endIndex <= 0) {
                throw new Exception("请上传dbmd文件!");
            } else {
                String rightName = fileName.substring(endIndex+1, fileName.length());
                if (!("dbmd".equals(rightName.toLowerCase()))) {
                    throw new Exception("请上传dbmd文件!");
                }
            }

            File directory = new File(root);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File f = new File(root + fileName);
            if (f.exists()) {
                f.delete();
            }
            file.transferTo(f);
            f.deleteOnExit();

            ResponseUtil.createSuccessResponse(Constant.succState, fileName, response);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            ResponseUtil.createErrorResponse(Constant.failState, e.getMessage(), response);
        }
    }
}
