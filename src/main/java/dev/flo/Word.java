package dev.flo;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Word{

    @JsonProperty
    private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}