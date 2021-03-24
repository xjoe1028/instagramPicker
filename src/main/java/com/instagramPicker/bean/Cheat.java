package com.instagramPicker.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Cheat {
	@JsonProperty("CHEAT_REWARD")
	private String cheat_reward = "";

	@JsonProperty("CHEAT_NAME")
	private String cheat_name = "";

	public String getCheat_reward() {
		return cheat_reward;
	}

	public void setCheat_reward(String cheat_reward) {
		this.cheat_reward = cheat_reward;
	}

	public String getCheat_name() {
		return cheat_name;
	}

	public void setCheat_name(String cheat_name) {
		this.cheat_name = cheat_name;
	}
}
