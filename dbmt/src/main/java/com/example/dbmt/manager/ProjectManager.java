package com.example.dbmt.manager;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ProjectManager {
    public static <T> T loadProject(File projectFile, Class<T> tClass)
            throws IOException
    {
        String projectJson = FileUtils.readFileToString(projectFile);
        return JSONObject.toJavaObject(JSONObject.parseObject(projectJson), tClass);
    }

    public static void saveProject(File projectFile, Object projectInfo) throws IOException {
        FileUtils.writeStringToFile(projectFile, JSONObject.toJSONString(JSONObject.toJSON(projectInfo), true));
    }
}