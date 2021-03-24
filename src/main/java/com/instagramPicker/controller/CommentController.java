package com.instagramPicker.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagramPicker.bean.Cheat;
import com.instagramPicker.bean.Comment;
import com.instagramPicker.bean.Reward;

public class CommentController {
	private static Logger log = LoggerFactory.getLogger(CommentController.class);

	/**
	 * 
	 * @param url
	 * @param tag
	 * @param words
	 * @param rewards
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static List<Comment> doLottery(String url, String tag, String keywords, String repeat,
			List<Reward> rewardList, List<Cheat> cheatList) throws InterruptedException, IOException {

		List<Comment> allComments = getAllComments(url);
//		System.out.println("爬完留言，有 " + allComments.size() + " 筆資料回來");
		if (Integer.parseInt(tag) > 0) {
			allComments = getCommentsByTag(allComments, tag);
		}
		if (keywords.trim().length() > 0) {
			allComments = getCommentsByWords(allComments, keywords);
		}
//		if (repeat.trim().equals("0")) { // 不可重複留言
//			allComments = detectRepeat(allComments,repeat);
//		}
		if("0".equals(repeat.trim())) {
			allComments = removeRepeat(allComments,repeat);
		}
		// todo: 排除自己帳號

		List<Comment> finalData = getFinalData(allComments, rewardList, cheatList);
		return finalData;
	}

	/**
	 * get all comments from define instagram post
	 * 
	 * @param url
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static List<Comment> getAllComments(String url) throws InterruptedException, IOException {
//		System.out.println("[getAllComments] Start");

		List<Comment> comments = new ArrayList<Comment>();
		// 自己選擇要用的瀏覽器 WebDriver
		// 用Chrome

//		ClassPathResource classPathResource = new ClassPathResource("chromedriver.exe");
//
//		InputStream inputStream = classPathResource.getInputStream();
//		File somethingFile = File.createTempFile("chromedriver", ".exe");
//		try {
//		    FileUtils.copyInputStreamToFile(inputStream, somethingFile);
//		} finally {
//		    IOUtils.closeQuietly(inputStream);
//		}
//
//		System.out.println(somethingFile.getAbsolutePath());
//		System.out.println("" +somethingFile.canExecute());
//		 System.setProperty("webdriver.chrome.driver", somethingFile.getAbsolutePath());
		Properties pro = new Properties();
		try {
			ClassPathResource classPathResource = new ClassPathResource("chromedriver.exe");
			InputStream inputStream = classPathResource.getInputStream();
//			System.out.println(inputStream.toString());
			pro.load(inputStream);
//			System.out.println(pro.size());
		} catch (IOException e) {
//			System.out.println("fail");
		}
//		System.setProperty("webdriver.chrome.driver","./src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		// 使用implicitlyWait，抓取DOM時，會等DOM出現才抓，最多等10秒
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(url); // 開啟瀏覽器到 目標IG

		Thread.sleep(3000); // 暫停3秒
		// user not logined
//		WebElement close_button = driver.findElement(By.className("xqRnw"));
//		if (close_button.isEnabled())
//			close_button.click();

		ObjectMapper om = new ObjectMapper();
		int x = 1;
		for (int i = 0; i < x; i++) {
			List<WebElement> load_more_comment = driver.findElements(By.cssSelector(".MGdpg > button:nth-child(1)"));
//			System.out.println(load_more_comment.size());
			if (load_more_comment.size() > 0) {
				for (WebElement w : load_more_comment) {
					w.click();
					Thread.sleep(1500); // 暫停1.5秒
					x = x + 1;
				}
			}
		}

		List<WebElement> elements = driver.findElements(By.className("gElp9"));
		int count = 0;
		for (WebElement e : elements) {
			WebElement imgTemp = e.findElement(By.className("P9YgZ"));
			String img = imgTemp.findElement(By.className("_6q-tv")).getAttribute("src");
			WebElement container = e.findElement(By.className("C4VMK"));
			String name = container.findElement(By.className("_6lAjh")).getText();
			String content = container.findElement(By.tagName("span")).getText();
			content = content.replace("\n", "");

//			System.out.println("第: " + count + " 筆");
//			System.out.println("照片: " + img);
//			System.out.println("NAME: " + name);
//			System.out.println("留言: " + content);
			if (count > 0) {
				Comment c = new Comment();
				c.setCount(String.valueOf(count));
				c.setContent(content);
				c.setImg(img);
				c.setName(name);
				comments.add(c);
			}
			count += 1;
		}
//		System.out.println("留言總數: " + count);
//		System.out.println("OutPut Json: " + om.writeValueAsString(comments));

		driver.close();
		return comments;
	}

	/**
	 * 排除非不足的標記數目
	 * 
	 * @param orgList
	 * @param tags
	 * @return
	 */
	private static List<Comment> getCommentsByTag(List<Comment> orgList, String tags) {
//		System.out.println("[getCommentsByTag]");
		int tag = Integer.parseInt(tags);
//		System.out.println("需求 留言tag數: " + tag);
		List<Comment> returnList = new ArrayList<Comment>();

		for (Comment c : orgList) {
			String content = c.getContent();
//			System.out.println("留言: " + content);
			int count = 0;
			String strTmp = "";
			for (int i = 0; i < content.length(); i++) {
				strTmp = String.valueOf(content.charAt(i));
				if (strTmp.equals("@"))
					count++;
			}
//			System.out.println("留言tag數: " + count);
			if (count >= tag)
				returnList.add(c);
		}

		return returnList;
	}

	/**
	 * 排除非指定的留言
	 * 
	 * @param orgList
	 * @param keywords
	 * @return
	 */
	private static List<Comment> getCommentsByWords(List<Comment> orgList, String keywords) {
//		System.out.println("[getCommentsByWords]");
		List<Comment> returnList = new ArrayList<Comment>();
		keywords = keywords.trim();
		for (Comment c : orgList) {
			String content = c.getContent().trim();
//			System.out.println("留言: " + content);
			if (content.contains(keywords)) {
				returnList.add(c);
//				System.out.println("加入 list! ");
			}
		}
		return returnList;
	}

	/**
	 * 
	 * @param orgList
	 * @return
	 */
	private static List<Comment> removeRepeat(List<Comment> orgList , String repeat) {
		for (int i = 0; i < orgList.size()-1; i++) {
			for (int j = orgList.size()-1; j > i; j--) {
				if (orgList.get(j).getName().equals(orgList.get(i).getName())) {
					orgList.remove(j);
				}
			}
		}
		return orgList;
	}

	/**
	 * 
	 * @param orgComment
	 * @param rewardList
	 * @param cheatList
	 * @return
	 */
	private static List<Comment> getFinalData(List<Comment> orgComment, List<Reward> rewardList,
			List<Cheat> cheatList) {
//		System.out.println("[getFinalDataMap]");
//		System.out.println("[getFinalDataMap] 共有: " + orgComment.size() + " 可供抽獎");

		List<Comment> finalData = new ArrayList<Comment>();
		// 紀錄個別獎項需求數量
		Map<String, Integer> rewardMap = new HashMap<String, Integer>();

		for (Reward r : rewardList) {
			rewardMap.put(r.getReward().trim(), Integer.parseInt(r.getCount()));
		}

		int type = rewardList.size();
		int allRewards = 0;
		for (Reward r : rewardList) {
			allRewards = allRewards + Integer.parseInt(r.getCount());
		}

		for (Cheat c : cheatList) {
			Comment comment = new Comment();
			comment.setCount(c.getCheat_reward());
			comment.setName(c.getCheat_name());
			finalData.add(comment);
		}

		orgComment = getRandomArray(orgComment, allRewards - cheatList.size());

		for (Reward r : rewardList) {
			List<Comment> temp = new ArrayList<Comment>();
			int need = rewardMap.get(r.getReward());
			int already = 0;
			for (Comment comment : finalData) {
				if (r.getReward().equals(comment.getCount())) {
					already = already + 1;
				}
			}
			if (already < need) {
				need = need - already;
				temp.addAll(orgComment.subList(0, need));
				orgComment = orgComment.subList(need, orgComment.size());
			}
			for (Comment com : temp) {
				com.setCount(r.getReward());
			}
			finalData.addAll(temp);
		}

//		for (int i = 0; i < rewardType.length; i++) {
//			List<Comment> c = finalDataMap.get(rewardType[i]);
//			if (c.size() < rewardMap.get(rewardType[i].trim())) {
//				int need = rewardMap.get(rewardType[i].trim()) - c.size();
//				c = orgComment.subList(0, need);
//				orgComment = orgComment.subList(need, orgComment.size());
//				finalDataMap.put(rewardType[i].trim(), c);
//			}
//		}
		Collections.sort(finalData);
		return finalData;
	}

	/**
	 * Description: 隨機從陣列中取出指定的不重複的n個數。
	 * 
	 * @param ArrayList 原始陣列
	 * @param int       n 隨機抽取的個數
	 * @return 返回抽取的陣列
	 */
	private static List<Comment> getRandomArray(List<Comment> orgComment, int num) {
//		System.out.println("[getRandomArray]");
//		System.out.println("有 " + orgComment.size() + " 筆可供抽獎");
//		System.out.println("需再抽 :" + num + "筆");
		Random r = new Random();
		List<Comment> returnList = new ArrayList<Comment>();
		int randomNum;
		System.out.println("orgComment的size為:"+orgComment.size());
		for (int i = 0; i < num; i++) {
//			if(orgComment.size() <= 0) {
//				randomNum = r.nextInt(orgComment.size()+1);
//			}else {
//				randomNum = r.nextInt(orgComment.size());
//			}
			System.out.println("orgComment.size():"+orgComment.size());
			randomNum = r.nextInt(orgComment.size());
			randomNum = randomNum == 0 ? 0 : randomNum - 1;
			returnList.add(orgComment.get(randomNum));
			orgComment.remove(randomNum);
		}
		return returnList;
	}

}
