package com.example.exportdir.action;

import com.alibaba.fastjson.JSONObject;
import com.example.exportdir.tool.Constant;
import com.example.exportdir.tool.FilePathUtil;
import com.example.exportdir.tool.ResponseUtil;
import com.example.exportdir.service.ExportDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Controller
public class ExportDirContorller {
    @Value("${file.download.dir}")
    private String downloadDir;
    @Autowired
    ExportDirService exportDirService;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/exportDir", method = RequestMethod.POST)
    public void exportDir(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = exportJson();
            String path = FilePathUtil.getRealFilePath(downloadDir + fileName);
            ResponseUtil.createSuccessResponse(Constant.succState, path, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private String exportJson() throws Exception {
        JSONObject obj = new JSONObject();
        List<Map<String, Object>> basedirLibraries = exportDirService.findDirAll();
        List<Map<String, Object>> basedirOrgs = exportDirService.findDirOrgAll();

        List<Map<String, Object>> baseDirArray = new ArrayList<>();
        for(Map<String, Object> map : basedirLibraries){
            baseDirArray.add(toReplaceKeyLow(map));
        }

        List<Map<String, Object>> baseOrgArray = new ArrayList<>();
        for(Map<String, Object> map : basedirOrgs){
            baseOrgArray.add(toReplaceKeyLow(map));
        }

        obj.put("baseDirArray", baseDirArray);
        obj.put("baseOrgArray", baseOrgArray);

        String fileName = "dir-" + UUID.randomUUID().toString() + ".json";
        File file = new File(downloadDir, fileName);
        if (!file.exists()) {
            FileWriter writer;
            try {
                writer = new FileWriter(file);
                writer.write(obj.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileName;
    }

    public static Map<String, Object> toReplaceKeyLow(Map<String, Object> map) {
        Map re_map = new HashMap();
        if (re_map != null) {
            Iterator var2 = map.entrySet().iterator();

            while (var2.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) var2.next();
                re_map.put(underlineToCamel(((String) entry.getKey()).toLowerCase()), map.get(entry.getKey()));
            }

            map.clear();
        }
        return re_map;
    }

    public static String underlineToCamel(String param) {
        char UNDERLINE = '_';
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
