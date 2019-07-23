package com.roger.git.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Maps with git repo
 */
public class GitRepoDetails {
    @JsonProperty("number")
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
