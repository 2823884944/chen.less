package cn.c.basics.utils;

import io.swagger.annotations.ApiOperation;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * 公用工具类
 * @author 陈
 */
public class CommonUtil {

    private static SecureRandom random = new SecureRandom();

    @ApiOperation(value = "生成随机文件名称")
    public static String renamePic(String fileName) {
        return UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
    }



    @ApiOperation(value = "避免删除 DFS 死循环")
    public static Boolean judgeIds(String tempString, String[] list){
        boolean flag = true;
        for(String id : list){
            if(Objects.equals(tempString,id)){
                flag = false;
                break;
            }
        }
        return !flag;
    }

    @ApiOperation(value = "生成随机6位验证码")
    public static String getRandomNum() {
        Random random = new Random();
        int num = random.nextInt(999999);
        return String.format("%06d", num);
    }
}
