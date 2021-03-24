package com.instagramPicker.controller;
//package com.ryan.insta.cotroller;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ryan.insta.data.Comment;
//
//
//public class test {
//
//	public static void main(String[] args) throws InterruptedException, JsonProcessingException {
//
//		System.out.println("Start");
//		
//		List<Comment> comments = new ArrayList<Comment>();
//		// 自己選擇要用的瀏覽器 WebDriver
//		// 用Chrome
//		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.manage().deleteAllCookies();
//		// 使用implicitlyWait，抓取DOM時，會等DOM出現才抓，最多等10秒
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.get("https://www.instagram.com/p/CK_izsknw0t/"); // 開啟瀏覽器到 目標IG
//		Thread.sleep(3000); // 暫停3秒
//
//
//		ObjectMapper om = new ObjectMapper();
//		
//		int x =1;
//		for (int i=0; i<x; i++) {
//			List<WebElement> load_more_comment = driver.findElements(By.cssSelector(".MGdpg > button:nth-child(1)"));
//			System.out.println(load_more_comment.size());
//			if (load_more_comment.size() >0) {
//				for(WebElement w: load_more_comment) {
//					w.click();
//					Thread.sleep(150000); // 暫停1.5秒
//					x = x+1;
//				}
//			}
//		}
//		
//		List<WebElement> elements = driver.findElements(By.className("gElp9"));
//		int count = 0;
//		for(WebElement e: elements ) {
//			WebElement imgTemp = e.findElement(By.className("P9YgZ"));
//			String img = imgTemp.findElement(By.className("_6q-tv")).getAttribute("src");
//			WebElement container = e.findElement(By.className("C4VMK"));
//			String name = container.findElement(By.className("_6lAjh")).getText();
//			String content = container.findElement(By.tagName("span")).getText();
//			content = content.replace("\n", "");
//			
//			count +=1;
//			System.out.println("第: " + count + " 筆");
//			System.out.println("照片: " + img);
//			System.out.println("NAME: " + name);
//			System.out.println("留言: " + content);
//			if(count != 0) {
//				Comment c = new Comment();
//				c.setCount(String.valueOf(count));
//				c.setContent(content);
//				c.setImg(img);
//				c.setName(name);
//				comments.add(c);
//			}
//		}
//		System.out.println("留言總數: " + count);
//		System.out.println("OutPut Json: " + om.writeValueAsString(comments));
//
//
//		driver.close();
//	}
//
//}