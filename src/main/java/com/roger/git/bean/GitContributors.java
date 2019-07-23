package com.roger.git.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Maps with Contributors response
 */
public class GitContributors {

    @JsonProperty("login")
    private String contributorsName;

    public String getContributorsName() {
        return contributorsName;
    }

    public void setContributorsName(String contributorsName) {
        this.contributorsName = contributorsName;
    }

    public GitContributors(){};

    public GitContributors(String contributorsName){this.contributorsName=contributorsName;}
}
