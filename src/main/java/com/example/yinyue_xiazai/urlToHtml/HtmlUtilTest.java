package com.example.yinyue_xiazai.urlToHtml;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUtilTest {
	
	
	public static void main(String[] args) {
		
    	
    	String url = "http://www.djye.com/";
    	
    	
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        
    	//启动js
    	webClient.getOptions().setJavaScriptEnabled(true);
    	//关闭css渲染
    	webClient.getOptions().setCssEnabled(false);
    	//启动重定向
    	webClient.getOptions().setRedirectEnabled(true);
    	//启动cookie管理
    	webClient.setCookieManager(new CookieManager());
    	//启动ajax代理
    	webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    	//js运行时错误，是否抛出异常
    	webClient.getOptions().setThrowExceptionOnScriptError(false);
    	
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        
        
        
        HtmlPage page = null;
        try {
            page = webClient.getPage(url);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(300000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
        
        System.out.println("---------------源码开始了----------------------");
        System.out.println(pageXml);
        System.out.println("---------------源码结束了----------------------");

    }
}