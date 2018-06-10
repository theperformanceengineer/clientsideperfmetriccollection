package com.clientsideperfengg.org.perfmetrics;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import org.json.simple.JSONObject;
 

public class ClientsidePerfMetric
{
		/* Pass webdriver instance and absolute filepath in which 
		 * you want to have the Performance JSON content.*/
		
		/**
		 * @param webdriver
		 * @param filePath
		 */
		public static void writePerfMetricasJSON(WebDriver webdriver, String filePath)
		{
			JavascriptExecutor js1=((JavascriptExecutor)webdriver);
			try {
			Thread.sleep(5000);
			}catch(Exception e) {e.printStackTrace();}
			String url=webdriver.getCurrentUrl();
			System.out.println("Current URL :"+url);
			long pageLoadTime= (Long)js1.executeScript("return (window.performance.timing.loadEventEnd-window.performance.timing.responseStart)");
			long TTFB= (Long)js1.executeScript("return (window.performance.timing.responseStart-window.performance.timing.navigationStart)");
			long endtoendRespTime= (Long)js1.executeScript("return (window.performance.timing.loadEventEnd-window.performance.timing.navigationStart)");
			
			Date date = new Date();
	        Timestamp ts=new Timestamp(date.getTime());
	        
			System.out.println("PR Time :"+pageLoadTime);			
			System.out.println("TTFB :"+TTFB);
			System.out.println("Customer perceived Time :"+endtoendRespTime);
			System.out.println("timeStamp"+ts);
			
			
			JSONObject obj = new JSONObject();
		
			obj.put("url", url);
			obj.put("PageLoad Time", pageLoadTime);
			obj.put("TTFB", TTFB);
			obj.put("Customer Time", endtoendRespTime);	
			obj.put("Timestamp",ts.toString());
					
			try {
				String jsoncontent=obj.toJSONString();
							
				File f1=new File(filePath);		
				
				if(!(f1.exists()))
				{
					f1.createNewFile();					
				}
				FileWriter fw1=new FileWriter(f1);
				PrintWriter pw1=new PrintWriter(fw1);
				if(f1.exists()&& f1.isFile())
				{
					pw1.println(jsoncontent);	
					pw1.flush();
					pw1.close();
					fw1.close();
				}
				else {
					System.out.println("Please provide a valid path to desitnation Jsonfile");
				}					
			}catch(Exception e) {e.printStackTrace();}
			webdriver.quit();		
			
		}		
	// Sample usage is given below
	public static void main(String[] args)throws Exception 
	{	
		//Selenium WebDriver code 
		System.setProperty("webdriver.chrome.driver", "C:\\tmp\\chromedriver.exe");
		WebDriver wd1=new ChromeDriver();
		String url="http://www.msn.com";
		wd1.get(url);
		wd1.manage().window().maximize();		
		writePerfMetricasJSON(wd1,"Demo3.json");			
	}		
		
}


