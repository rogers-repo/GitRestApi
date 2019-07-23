package com.roger.git.strategy;

import com.roger.git.exception.GitException;


/**
 * Strategy Context
 */
public class StrategyContext {

    private Strategy restApiContext;

    public StrategyContext (Strategy restApiContext){
        this.restApiContext=restApiContext;
    }

    public String createAPI(String gitUser,String repoName,String apiType) throws GitException {
        return restApiContext.createGitApiUrl(gitUser,repoName,apiType);
    }
}
