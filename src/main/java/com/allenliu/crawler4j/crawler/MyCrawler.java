package com.allenliu.crawler4j.crawler;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 自定义爬虫类需要继承WebCrawler类，决定哪些url可以被爬以及处理爬取的页面信息
 * 
 * @author
 *
 */
public class MyCrawler extends WebCrawler {

	/**
	 * 正则匹配指定的后缀文件
	 */
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$");

	/**
	 * 这个方法主要是决定哪些url我们需要抓取，返回true表示是我们需要的，返回false表示不是我们需要的Url
	 * 第一个参数referringPage封装了当前爬取的页面信息 第二个参数url封装了当前爬取的页面url信息
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase(); // 得到小写的url
		return !FILTERS.matcher(href).matches() // 正则匹配，过滤掉我们不需要的后缀文件
				&& href.startsWith("http://jobs.51job.com"); // url必须是http://www.java1234.com/开头，规定站点
		// return true;
	}

	/**
	 * 当我们爬到我们需要的页面，这个方法会被调用，我们可以尽情的处理这个页面 page参数封装了所有页面信息
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL(); // 获取url
		System.out.println("URL: " + url);
		// String regex = "https://item.jd.com/[^\\s]*.html";

		if (page.getParseData() instanceof HtmlParseData
		// && Pattern.matches(regex, url)
		) { // 判断是否是html数据
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); // 强制类型转换，获取html数据对象
			String text = htmlParseData.getText(); // 获取页面纯文本（无html标签）
			String html = htmlParseData.getHtml(); // 获取页面Html
			Set<WebURL> links = htmlParseData.getOutgoingUrls(); // 获取页面输出链接
			
			Document document = Jsoup.parse(html);
			System.out.println(document.select(".tHjob h1").get(0).text());
			System.out.println(document.select(".tHjob strong").get(0).text());
			System.out.println(document.select(".tHjob .cname").get(0).text());
			System.out.println(document.select(".tHjob .msg.ltype").get(0).text());
			
			Iterator<Element> iterator= document.select(".tCompany_main .jtag .t1 span.sp4").iterator();
			while(iterator.hasNext()) {
				System.out.println(iterator.next().text());
			}
			
			Iterator<Element> iterator2= document.select(".tCompany_main .jtag .t2 span").iterator();
			while(iterator2.hasNext()) {
				System.out.println(iterator2.next().text());
			}
			
			System.out.println(document.select(".tBorderTop_box .bmsg.job_msg.inbox").get(0).text());
			System.out.println(document.select(".tBorderTop_box .bmsg.inbox .fp").get(0).text());

			System.out.println("纯文本长度: " + text.length());
			System.out.println("html长度: " + html.length());
			System.out.println("输出链接个数: " + links.size());
		}
	}
}