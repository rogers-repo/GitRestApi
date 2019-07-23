package com.roger.git.strategy;

import com.roger.git.exception.GitException;

public interface Strategy {
    public String createGitApiUrl(String gitUser,String repoName,String type) throws GitException;
}
