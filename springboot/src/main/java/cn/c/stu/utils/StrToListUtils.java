package cn.c.stu.utils;

import cn.c.data.utils.CsxNullUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 * @author 陈
 */
public class StrToListUtils {

    public static List<String> getListByStr(String str) {
        List<String> ans = new ArrayList<>();
        if(CsxNullUtils.isNull(str)) {
            return ans;
        }
        String[] split = str.split(",");
        for (String s : split) {
            ans.add(s);
        }
        return ans;
    }
}
