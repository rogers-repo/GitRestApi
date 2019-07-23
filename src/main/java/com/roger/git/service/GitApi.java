package com.roger.git.service;

import com.roger.git.bean.GitContributors;
import com.roger.git.bean.GitRepoStats;
import com.roger.git.bean.GitRepositories;
import com.roger.git.exception.GitException;
import com.roger.git.exception.RepoUserNotFoundException;

import java.util.List;

/**
 * Contracts for git services
 */
public interface GitApi {

    List<GitRepositories> getRepoNames(String userName) throws GitException, RepoUserNotFoundException;
    GitRepoStats getRepoStatistics(String userName,String repoName,String type) throws GitException, RepoUserNotFoundException;
    List<GitContributors> getContributorsNames(String userName, String repoName) throws GitException, RepoUserNotFoundException;
}
