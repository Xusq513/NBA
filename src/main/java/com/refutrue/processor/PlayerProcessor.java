package com.refutrue.processor;

import com.refutrue.MyFilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @Auther: Michael Xu
 * @Date: 2018/9/17 10:59
 * @Description:
 */
public class PlayerProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
        String url = page.getRequest().getUrl();
        if(!url.contains("?")){
            page.putField("html",page.getHtml().toString());
            String num = getNum(url);
            String name = page.getHtml().$(".playerinfo .name","text").get();

            page.putField("fileName",num);
        }
        page.addTargetRequests(page.getHtml().links().regex("http://www.stat-nba.com/playerList.php?il=*").all());
        page.addTargetRequests(page.getHtml().links().regex("http://www.stat-nba.com/player/\\d+.html").all());
    }

    private String getNum(String httpUrl){
        String[] array = httpUrl.split("/");
        String lastStr = array[array.length - 1];
        lastStr = lastStr.replaceAll("\\.html","");
        return  lastStr;
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new PlayerProcessor())
                .addPipeline(new MyFilePipeline("Player"))
                .addUrl("http://www.stat-nba.com/playerList.php?il=A&lil=0")
                .thread(5)
                .run();
    }
}
