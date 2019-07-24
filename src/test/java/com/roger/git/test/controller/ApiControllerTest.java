package com.roger.git.test.controller;


import com.roger.git.app.App;
import com.roger.git.bean.GitContributors;
import com.roger.git.bean.GitRepoStats;
import com.roger.git.bean.GitRepositories;
import com.roger.git.service.GitApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ApiControllerTest {
   private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @MockBean
    private GitApi service;

    @Test
    public void openPrCountTest() throws Exception {
        GitRepoStats stats=new GitRepoStats();
        stats.setCount(10);
        Mockito.when( service.getRepoStatistics(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(stats);


        this.mockMvc.perform(get("/api/statistics/gituser/gitrepo?type=openpr")
                .header(HttpHeaders.AUTHORIZATION,getUser())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.count", is(10)))
                .andReturn();
    }

    @Test
    public void closedPrCountTest() throws Exception {
        GitRepoStats stats=new GitRepoStats();
        stats.setCount(20);
        Mockito.when( service.getRepoStatistics(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(stats);

        this.mockMvc.perform(get("/api/statistics/gituser/gitrepo?type=closedpr")
                .header(HttpHeaders.AUTHORIZATION,getUser())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.count", is(20)))
                .andReturn();
    }

    @Test
    public void CommitIn30DaysTest() throws Exception {
        GitRepoStats stats=new GitRepoStats();
        stats.setCount(3);
        Mockito.when( service.getRepoStatistics(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(stats);

        this.mockMvc.perform(get("/api/statistics/gituser/gitrepo?type=30daysCommit")
                .header(HttpHeaders.AUTHORIZATION,getUser())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.count", is(3)))
                .andReturn();
    }

    @Test
    public void contributorsCountTest() throws Exception {
        GitRepoStats stats=new GitRepoStats();
        stats.setCount(3);
        Mockito.when( service.getRepoStatistics(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(stats);

        this.mockMvc.perform(get("/api/statistics/gituser/gitrepo?type=contributors")
                .header(HttpHeaders.AUTHORIZATION,getUser())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.count", is(3)))
                .andReturn();
    }

    @Test
    public void getGitRepoTest() throws Exception {
        GitRepositories repo1=new GitRepositories(100,"repo1","test/repo",true);
        GitRepositories repo2=new GitRepositories(101,"repo2","test/repo2",true);
        List<GitRepositories> repos=new ArrayList<>();
        repos.add(repo1);
        repos.add(repo2);
        Mockito.when( service.getRepoNames(Mockito.anyString())).thenReturn(repos);
        this.mockMvc.perform(get("/api/repolist/gituser")
                .header(HttpHeaders.AUTHORIZATION,getUser())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].name", is("repo1")))
                .andExpect(jsonPath("$[1].name", is("repo2")))
                .andExpect(jsonPath("$[0].*", hasSize(4)))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();
    }


    @Test
    public void getContributorsTest() throws Exception {
        GitContributors Contributor1=new GitContributors("Contributor1");
        GitContributors Contributor2=new GitContributors("Contributor2");
        List<GitContributors> Contributors=new ArrayList<>();
        Contributors.add(Contributor1);
        Contributors.add(Contributor2);
        Mockito.when( service.getContributorsNames(Mockito.anyString(),Mockito.anyString())).thenReturn(Contributors);
        this.mockMvc.perform(get("/api/contributors/gituser/gitapi")
                .header(HttpHeaders.AUTHORIZATION,getUser())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].login", is("Contributor1")))
                .andExpect(jsonPath("$[1].login", is("Contributor2")))
                .andExpect(jsonPath("$[0].*", hasSize(1)))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();
    }

    private String getUser()
    {
       return "Basic " + Base64Utils.encodeToString("gituser:gituser".getBytes());
    }





}
