package com.roger.git.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Maps with git api headers
 */
public class GitHeaderRelTag {
    @JsonProperty("rel")
    private String rel;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
