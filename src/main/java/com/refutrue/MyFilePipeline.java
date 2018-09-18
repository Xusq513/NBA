package com.refutrue;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @Auther: Michael Xu
 * @Date: 2018/9/17 10:21
 * @Description:
 */
public class MyFilePipeline extends FilePipeline {

    public static final String DEFAULT_PATH = "d:/NBA/";

    private String fileType;

    public MyFilePipeline(String fileType){
        this.fileType = fileType;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String fileName = resultItems.get("fileName");
        String html = resultItems.get("html");
        if(StringUtils.isBlank(fileName) || StringUtils.isBlank(html)){
            return;
        }
        String path = this.DEFAULT_PATH + PATH_SEPERATOR + fileType + PATH_SEPERATOR + fileName + ".html";
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path)),"UTF-8"));
            printWriter.println("url:\t" + resultItems.getRequest().getUrl());
            printWriter.write(html);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
