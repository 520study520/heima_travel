package cn.itcast.travel.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @title: TrunJson
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/5 下午 3:56
 */
public class TrunJson {
    private ObjectMapper ob = new ObjectMapper();//创建实体类数据格式转json数据格式对象
    private  ResultInfo result = new ResultInfo();    //    //创建交互数据信息类对象
    public String getResultInfoJson(boolean flag,String errorMsg,Object data){
        //4.2用户已存在
        if(errorMsg!=null){
            result.setErrorMsg(errorMsg);
        }
        if(data!=null){
            result.setData(data);
        }
        result.setFlag(flag);

        String json = null;  //转换后的json数据格式
        //4.2.1将数据转为json格式
        try {
            json = ob.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
