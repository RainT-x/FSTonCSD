package cn.zitsong.whu.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Description: Tools for exception
 * @Source: JDK 1.8
 * @Author: ZhaoKunsong
 * @Date: 2019-08-16 10:47
 * @Since: version 1.0.0
 **/
public class ExceptionUtil {

    /**
     * 获取异常的堆栈信息
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
