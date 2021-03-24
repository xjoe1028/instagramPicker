package com.instagramPicker.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class RequestData {
	@JsonProperty("URL")
	private String url = "";

	@JsonProperty("TAG")
	private String tag = "";

	@JsonProperty("KEYWORD")
	private String keyword = "";

	@JsonProperty("REPEAT")  //0:false or 1:true
	private String repeat = "";
	
	@JsonProperty("REWARDS") 
	private List<Reward> rewards = new ArrayList<Reward>();
	
	@JsonProperty("CHEAT_LIST") 
	private List<Cheat> cheat_list = new ArrayList<Cheat>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public List<Reward> getRewards() {
		return rewards;
	}

	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}

	public List<Cheat> getCheat_list() {
		return cheat_list;
	}

	public void setCheat_list(List<Cheat> cheat_list) {
		this.cheat_list = cheat_list;
	}
	
}
