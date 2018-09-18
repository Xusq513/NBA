package com.refutrue.processor;

import com.refutrue.MyFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @Auther: Michael Xu
 * @Date: 2018/9/17 10:32
 * @Description:
 */
public class ChikenSelectProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
        page.putField("html",page.getHtml().toString());
        String url = page.getRequest().getUrl();
        page.putField("fileName",getYear(url));
        page.addTargetRequests(page.getHtml().links().regex("http://www.stat-nba.com/award/item11isnba1season\\d\\d\\d\\d.html").all());
    }

    public Site getSite() {
        return site;
    }

    private String getYear(String httpUrl){
        String[] array = httpUrl.split("/");
        String lastStr = array[array.length - 1];
        lastStr = lastStr.replaceAll("item11isnba1season","").replaceAll("\\.html","");
        return  lastStr;
    }

    public static void main(String[] args) {
        Spider.create(new ChikenSelectProcessor()).addUrl("http://www.stat-nba.com/award/item11isnba1season2003.html")
                .addPipeline(new MyFilePipeline("ChikenSelect"))
                .thread(5).run();
    }
}
