package com.example.home.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.home.service.UserService;
import com.example.home.tool.Constant;
import com.example.home.tool.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/input")
    public String input() {
        return "pages/input";
    }

    @RequestMapping("/batchinput")
    public String batchinput() {
        return "pages/batchinput";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse respone) {
        String paramCount = request.getParameter("count");
        String paramCode = request.getParameter("licenseTypeCode");
        JSONArray result = new JSONArray();
        int count = -1;
        String typeCode = "";
        try {
            if(StringUtils.isNotBlank(paramCode)){
                typeCode = paramCode;
            }
            if(StringUtils.isNotBlank(paramCount)) {
                count = Integer.parseInt(paramCount);
            }
            List<Map<String, Object>> list = userService.getTypeList(typeCode, count);

            for(Map<String, Object> map : list){
               JSONObject obj = new JSONObject();
               String licenseTypeCode = map.get("LICENSE_TYPE_CODE") == null ? "" : map.get("LICENSE_TYPE_CODE").toString();
               String licenseTypeName = map.get("LICENSE_TYPE_NAME") == null ? "" : map.get("LICENSE_TYPE_NAME").toString();
               if (StringUtils.isNotBlank(licenseTypeCode) && StringUtils.isNotBlank(licenseTypeName)) {
                   String state = "失败";
                   int i = userService.update(licenseTypeCode);
                   if (i == 1) {
                       state = "成功";
                   }
                   obj.put("licenseTypeCode", licenseTypeCode);
                   obj.put("licenseTypeName", licenseTypeName);
                   obj.put("state", state);
                   result.add(obj);
               }
           }
        }catch (Exception e){
           System.out.println(e);
           e.printStackTrace();
        }
        ResponseUtil.createSuccessResponse(Constant.succState, result.toJSONString(), respone);
    }
}
