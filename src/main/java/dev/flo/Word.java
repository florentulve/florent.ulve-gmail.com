package dev.flo;

import javax.json.bind.annotation.JsonbProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Word{

    @JsonbProperty
    private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}