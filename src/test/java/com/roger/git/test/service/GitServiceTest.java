package com.roger.git.test.service;

import com.roger.git.bean.GitContributors;
import com.roger.git.bean.GitRepoStats;
import com.roger.git.bean.GitRepositories;
import com.roger.git.service.GitApiImpl;
import com.roger.git.service.GitApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GitApiImpl.class})
public class GitServiceTest {

    @Autowired
    private GitApi service;

    public static final Logger LOGGER = LogManager.getLogger(GitServiceTest.class);

    @Test
    public void getReposTest() throws Exception {
        List<GitRepositories> repos = service.getRepoNames("rogers-repo");
        Assert.assertEquals(1, repos.size());
        Assert.assertEquals("GitRestApi",repos.get(0).getName());
    }

    @Test
    public void openPrTest() throws Exception {
        GitRepoStats openPr = service.getRepoStatistics("codecombat","codecombat","openpr");
            Assert.assertEquals(47, openPr.getCount());
    }

    @Test
    public void closedPrTest() throws Exception {
        GitRepoStats closedPr = service.getRepoStatistics("codecombat","codecombat","closedpr");
        Assert.assertEquals(3503, closedPr.getCount());
    }

    @Test
    public void commitCountTest() throws Exception {
        GitRepoStats totalCommits = service.getRepoStatistics("rogers-repo","GitRestApi","30daysCommit");
        Assert.assertEquals(10, totalCommits.getCount());
    }

    @Test
    public void contributorsCountTest() throws Exception {
        GitRepoStats totalContributors = service.getRepoStatistics("codecombat","codecombat","contributors");
        Assert.assertEquals(381, totalContributors.getCount());
    }

    @Test
    public void contributorsNamesTest() throws Exception {
        List<GitContributors>  totalContributors = service.getContributorsNames("rogers-repo","GitRestApi");
        Assert.assertEquals(1, totalContributors.size());
    }
}