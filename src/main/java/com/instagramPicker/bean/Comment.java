package com.instagramPicker.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment  implements Comparable<Comment>{
	@JsonProperty("COUNT")
	private String count = "";

	@JsonProperty("IMG")
	private String img = "";

	@JsonProperty("NAME")
	private String name = "";

	@JsonProperty("CONTENT")
	private String content = "";

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	 @Override
	    public int compareTo(Comment c) {
	        return c.getCount().compareTo(this.getCount());
	    }
}
