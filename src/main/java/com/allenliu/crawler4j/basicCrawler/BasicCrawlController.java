package com.allenliu.crawler4j.basicCrawler;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class BasicCrawlController {

	  public static void main(String[] args) throws Exception {

	    String crawlStorageFolder = "c:/crawl"; // 定义爬虫数据存储位置
	    int numberOfCrawlers = 7; // 定义7个爬虫，也就是7个线程

	    CrawlConfig config = new CrawlConfig();  // 实例化爬虫配置文件

	    config.setCrawlStorageFolder(crawlStorageFolder); // 设置爬虫文件存储位置

	    /*
	     * 设置请求的频率
	     * 每1000毫秒，也就是两次请求的间隔至少是1秒
	     */
	    config.setPolitenessDelay(1000);

	    /*
	     * 设置请求的网页的深度（后面专门会讲）  设置2 为两层
	     * 默认值-1 无限深度
	     */
	    config.setMaxDepthOfCrawling(2);

	    /*
	     * 设置爬取的最大网页数 这里设置1000  最多爬取1000次
	     * 默认值是-1，表示无限制
	     */
	    config.setMaxPagesToFetch(1000);

	    /**
	     * 是否爬取二进制文件，比如图片，PDF文档，视频之类的东西 这里设置false 不爬取
	     * 默认值true，爬取
	     */
	    config.setIncludeBinaryContentInCrawling(false);

	    /*
	     * 这里可以设置代理
	     * config.setProxyHost("proxyserver.example.com");  // 代理地址
	     * config.setProxyPort(8080); // 代理端口
	     *
	     * 如果使用代理，也可以设置身份认证  用户名和密码
	     * config.setProxyUsername(username); config.getProxyPassword(password);
	     */

	    /*
	     * 这个配置假如设置成true，当一个爬虫突然终止或者奔溃，我们可以恢复；
	     * 默认配置是false；推荐用默认配置，假如设置成true，性能会大打折扣；
	     */
	    config.setResumableCrawling(false);

	    /*
	     * 实例化爬虫控制器
	     */
	    PageFetcher pageFetcher = new PageFetcher(config); // 实例化页面获取器
	    RobotstxtConfig robotstxtConfig = new RobotstxtConfig(); // 实例化爬虫机器人配置 比如可以设置 user-agent
	    
	    // 实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件 规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
	    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	    
	    // 实例化爬虫控制器
	    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

	    /*
         * 配置爬虫种子页面，就是规定的从哪里开始爬，可以配置多个种子页面
         */
        controller.addSeed("http://www.java1234.com/");
        controller.addSeed("http://www.java1234.com/a/kaiyuan/");
        controller.addSeed("http://www.java1234.com/a/bysj/");

        /*
         * 启动爬虫，爬虫从此刻开始执行爬虫任务，根据以上配置
         */
	    controller.start(BasicCrawler.class, numberOfCrawlers);
	  }
	}