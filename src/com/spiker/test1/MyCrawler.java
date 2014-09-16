package com.spiker.test1;

import com.spiker.type.*;

import java.util.*;
import org.htmlparser.tags.*;
import org.htmlparser.NodeFilter;
import org.htmlparser.Node;
import org.htmlparser.filters.*;

public class MyCrawler {

    /**
    * 使用种子初始化 URL 队列
    * @return
    * @param seeds 种子 URL
    */
    private void initCrawlerWithSeeds(String[] seeds) {
        for(int i=0;i<seeds.length;i++) {
            LinkQueue.addUnvisitedUrl(seeds[i]);
        }
    }

    /**
    * 抓取过程
    * @return
    * @param seeds
    */
    public void crawling(String[] seeds) {
    //定义过滤器,提取以 http://www.lietu.com 开头的链接
    	//NodeFilter filter = new LinkStringFilter("www.lietu.com");
    	NodeFilter filter = new NodeFilter(){
            public boolean accept(Node node) {
                if (node instanceof LinkTag) {// <a> 标签
					LinkTag link = (LinkTag) node;
					String linkUrl = link.getLink();// URL

					System.out.println(linkUrl);
					System.out.println("======================");

					if(linkUrl.startsWith("http://www.lietu.com")) {
                    	return true;
                	} else {
                    	return false;
                	}
				} else {
					return false;
				}
            }
        };
        /*NodeFilter filter = new NodeFilter(){
            public boolean accept(String url) {
                if(url.startsWith("http://www.lietu.com")) {
                    return true;
                } else {
                    return false;
                }
            }
        };*/
        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        //循环条件:待抓取的链接不空且抓取的网页不多于 1000
        while(!LinkQueue.unVisitedUrlsEmpty()
            &&LinkQueue.getVisitedUrlNum()<=1000) {
            //队头 URL 出队列
			String visitUrl = (String)LinkQueue.unVisitedUrlDeQueue();

            if( visitUrl==null ) {
                continue;
            }

            DownLoadFile downLoader = new DownLoadFile();
            //下载网页
            try{
            	downLoader.downloadFile(visitUrl);
        	} catch(Exception e) {
        		e.printStackTrace();
        		LinkQueue.addVisitedUrl(visitUrl);
        		continue;
        	}
            //该 URL 放入已访问的 URL 中
            LinkQueue.addVisitedUrl(visitUrl);
            //提取出下载网页中的 URL
            Set<String> links = HtmlParserTool.extracLinks(visitUrl,filter);
            //新的未访问的 URL 入队
            for(String link : links) {
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }


    //main 方法入口
    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[]{"http://www.lietu.com"});
    }
}
