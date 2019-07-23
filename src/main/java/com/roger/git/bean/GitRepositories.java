package com.roger.git.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitRepositories {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("private")
    private boolean isPrivate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public GitRepositories(Integer id,String name,String fullName,boolean isPrivate){
        this.id=id;
        this.name=name;
        this.fullName=fullName;
        this.isPrivate=isPrivate;
    }

    public GitRepositories(){}

}
