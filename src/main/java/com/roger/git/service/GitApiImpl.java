package com.roger.git.service;

import com.roger.git.bean.GitContributors;
import com.roger.git.bean.GitRepoDetails;
import com.roger.git.bean.GitRepoStats;
import com.roger.git.bean.GitRepositories;
import com.roger.git.exception.GitException;
import com.roger.git.exception.RepoUserNotFoundException;
import com.roger.git.strategy.GitContributorApi;
import com.roger.git.strategy.GitStatisticsApi;
import com.roger.git.strategy.StrategyContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import static com.roger.git.util.ApiResponseUtil.*;
import static com.roger.git.util.GitConstant.*;

/**
 * implementation of git api interface
 */
@Service
public class GitApiImpl implements GitApi {

    public static final Logger LOGGER= LogManager.getLogger(GitApiImpl.class);

    public static int responseCount=0;

    /**
     * Gets repositories using git api GET method
     * REST End point users/<git user>/repos
     *
     * Input param userName - Git username
     *
     * return - list of repositories
     */
    @Override
    public List<GitRepositories> getRepoNames(String userName) throws GitException, RepoUserNotFoundException {
        try {
            //replace to user name
            String url = GIT_USRS_REPO_DETAILS.replace(REP_USR_NME, userName);
            //call process method to hit Git api
            ResponseEntity<?> responseEntity = processRequest(url, HttpMethod.GET, getRepoTypRef());
            List<GitRepositories> repos = (List<GitRepositories>) responseEntity.getBody();
            if(CollectionUtils.isEmpty(repos)) {throw new RepoUserNotFoundException(REPO_EMPTY);}
            return repos;
        } catch (HttpClientErrorException hce) {
            LOGGER.error("GitApiImpl :: getRepositories HttpClientErrorException :: ", hce);
            throw new GitException(VALID_REPO);
        }
    }

    /**
     * Gets repositories Statistics using git api GET method
     * REST End point - multiple endpoints
     *
     * Based on the type will decide which operation to perform
     *
     * Since there is a rate limit of 60
     * will hit the api max 2 times to get the count by checking the header rel link
     *
     * Input param userName - Git username, repo name and type of operations
     *                               openpr
     *                               closedpr
     *                               30daysCommit
     *                               contributors
     *
     * return - count of requested
     */
    @Override
    public GitRepoStats getRepoStatistics(String userName, String repoName , String type) throws GitException, RepoUserNotFoundException {
        GitRepoStats openPr=new GitRepoStats();
        try {
            responseCount=0;
            //based on the strategy  gets the api url
            StrategyContext createGitApi=new StrategyContext (new GitStatisticsApi());
            String url=createGitApi.createAPI(userName,repoName,type);
            //call recessive the pr api with open status by reading the headers rel value
            processPrdDetails(url,true);
            openPr.setCount(responseCount);
        } catch (HttpClientErrorException hce) {
            LOGGER.error("GitApiImpl :: getRepoStatistics HttpClientErrorException :: ", hce);
            throw new GitException(VALID_REPO);
        }
        return openPr;
    }

    /**
     * Gets top 10 repository contributors using git api GET method
     * REST End point contributors?per_page=10&since=
     *
     * Input param userName - Git username and repo name
     *
     * return - list of contributors name
     */
    @Override
    public List<GitContributors> getContributorsNames(String userName, String repoName) throws GitException, RepoUserNotFoundException {
        try {
            StrategyContext createGitApi=new StrategyContext (new GitContributorApi());
            String url=createGitApi.createAPI(userName,repoName,"contributer");
            ResponseEntity<?> responseEntity = processRequest(url, HttpMethod.GET, getRepoContributorsTypRef());
            List<GitContributors> repos = (List<GitContributors>) responseEntity.getBody();
            if(CollectionUtils.isEmpty(repos)) {throw new RepoUserNotFoundException(REPO_EMPTY);}
            return repos;

        } catch (HttpClientErrorException hce) {
            LOGGER.error("GitApiImpl :: getRepositories HttpClientErrorException :: ", hce);
            throw new GitException(VALID_REPO);
        }
    }

    //call recessive git api by reading the headers rel value
    private void processPrdDetails(String url,boolean firstTime )
    {
        ResponseEntity<?> responseEntity = processRequest(url, HttpMethod.GET, getRepoPrTypRef());
        List<GitRepoDetails> prlist = (List<GitRepoDetails>) responseEntity.getBody();
        String relLink=getRelLink(responseEntity.getHeaders(),firstTime);
        if(firstTime && !"Not Found".equalsIgnoreCase(relLink))
        { responseCount +=calulatedCount;}
        else
        {responseCount +=prlist.size();}
        firstTime=false;
        if(!"Not Found".equalsIgnoreCase(relLink)){
            processPrdDetails(relLink,firstTime);
        }
    }

    private ParameterizedTypeReference getRepoPrTypRef(){
        return new ParameterizedTypeReference<List<GitRepoDetails>>() {};
    }

    private ParameterizedTypeReference getRepoTypRef(){
        return new ParameterizedTypeReference<List<GitRepositories>>() {};
    }

    private ParameterizedTypeReference getRepoContributorsTypRef(){
        return new ParameterizedTypeReference<List<GitContributors>>() {};
    }

    public List<String> processRepoRes(List<GitRepositories> repos){
        //Iterate list and return list of repo name
       return repos.stream().map(repo ->  repo.getName()).collect(Collectors.toList());
    }

}
