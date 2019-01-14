package com.example.yinyue_xiazai.urlToHtml;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class UrlToHtml {

	

    
    
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {  
		
		
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",    "org.apache.commons.logging.impl.NoOpLog");  
		  
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")  
	        .setLevel(Level.OFF);  

	    java.util.logging.Logger.getLogger("org.apache.commons.httpclient")  
	        .setLevel(Level.OFF); 
	    
	    
        // TODO Auto-generated method stub  
        WebClient wc=new WebClient(BrowserVersion.FIREFOX_24);  
        wc.setJavaScriptTimeout(5000);  
        wc.getOptions().setUseInsecureSSL(true);//接受任何主机连接 无论是否有有效证书  
        wc.getOptions().setJavaScriptEnabled(true);//设置支持javascript脚本   
        wc.getOptions().setCssEnabled(false);//禁用css支持  
        wc.getOptions().setThrowExceptionOnScriptError(false);//js运行错误时不抛出异常  
        wc.getOptions().setTimeout(100000);//设置连接超时时间  
        wc.getOptions().setDoNotTrackEnabled(false);   
        HtmlPage page=wc.getPage("http://blog.csdn.net/su20145104009?viewmode=contents");  
        String res=page.asText();  
        //处理源码  
        deal(res);  
          
    } 
	
}
