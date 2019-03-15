package com.lib.fast.common.xlog;

import android.content.Context;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.lib.fast.common.utils.FileUtils;


/**
 * created by siwei on 2018/5/18
 */
public class LOG {

    /**okhttp日志输出*/
    public static Logger OkHttp;

    /**
     * 初始化XLog
     */
    public static void initXlog(Context context, boolean isDebug, String appname) {
        LogConfiguration configuration = new LogConfiguration.Builder()
                .logLevel(isDebug ? LogLevel.ALL : LogLevel.NONE)
                .tag(appname).build();
        Printer androidPrinter = new AndroidPrinter();
        Printer filePrinter = new FilePrinter                           // 打印日志到文件的打印器
                .Builder(FileUtils.getLoggerDir(context, "ZSWXlog").getPath()) // 指定保存日志文件的路径
                .logFlattener(new TimeFlatter())
                .build();
        XLog.init(configuration, androidPrinter, filePrinter);
        initTagLog();
    }

    private static void initTagLog(){
        OkHttp = XLog.tag("okhttp").build();
    }

}
