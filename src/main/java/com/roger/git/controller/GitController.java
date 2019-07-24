package com.roger.git.controller;

import com.roger.git.exception.GitException;
import com.roger.git.exception.RepoUserNotFoundException;
import com.roger.git.service.GitApi;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**Rest Controller to get repositories,
 *                        number of open pr,
 *                        pr closed in current days,
 *                        number of commits in last 30 days,
 *                        number of committers in last 30 days
 *                        top 10 committers in last 30 days*/
@RestController
@Api(value = "Git Api",tags = {"GitAPI"})
@SwaggerDefinition(tags={@Tag(name="GitAPI",description = "Gets git repositories and counts based on git user")})
@RequestMapping("/api")
@Validated
public class GitController {

    public static final Logger LOGGER= LogManager.getLogger(GitController.class);

    @Autowired
    GitApi git;


    /**
     * GET method to get git repositories based on gituser
     *
     * Input param Repository - git username    *
     * return - list of git repositories
     */
    @GetMapping ("/repolist/{username}")
    @ApiOperation(value = "Get repositories available for a user",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved repositories list"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "The resource you were looking for is not found")
    }
    )
    public ResponseEntity<?> getAllRepos(final @PathVariable(value = "username") @NotBlank @Size(max = 39, message = "Git username length must be less than 39 character")  String repoUserName) throws GitException,RepoUserNotFoundException {
        try {
            return ResponseEntity.ok().body(git.getRepoNames(repoUserName));
        } catch (GitException ge){
            LOGGER.error("GitController :: getAllRepos GitException :: ",ge.getMessage());
            throw new GitException(ge.getMessage());
        } catch(RepoUserNotFoundException re){
            LOGGER.error("GitController :: getAllRepos RepoUserNotFoundException :: ",re.getMessage());
            throw new RepoUserNotFoundException(re.getMessage());
        }
    }

    /**
     * GET method to get top 10 contributors name list in 30 days
     *
     * Input param Contributors - git username and git repository as pathvariable  *
     * return - list of top 10 contributors
     */
    @GetMapping ("/contributors/{username}/{reponame}")
    @ApiOperation(value = "Get top 10 Contributors Names list",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved top 10 Contributors in a repositories as list"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "The resource you were looking for is not found")
    }
    )
    public ResponseEntity<?> getContributorsNamesList(final @PathVariable(value = "username") @NotBlank @Size(max = 39, message = "Git username length must be less than 39 character") String repoUserName,final @PathVariable(value = "reponame",required = true) @NotBlank @Size(max = 100, message = "Git repository length must be less than 100 character")  String repoName) throws GitException,RepoUserNotFoundException {

        try {
            return ResponseEntity.ok().body(git.getContributorsNames(repoUserName,repoName));
        } catch (GitException ge){
            LOGGER.error("GitController :: getContributorsNamesList GitException :: ",ge.getMessage());
            throw new GitException(ge.getMessage());
        } catch(RepoUserNotFoundException re){
            LOGGER.error("GitController :: getContributorsNamesList RepoUserNotFoundException :: ",re.getMessage());
            throw new RepoUserNotFoundException(re.getMessage());
        }
    }

    /**
     * GET method to get count for different operations like
     *                         number of open pr,
     *                         pr closed in current days,
     *                         number of commits in last 30 days,
     *                         number of committers in last 30 days
     *
     * Input param RepoStatistics - git username,git repository as pathvariables
     *                              and operation type as query string
     *                          openpr,
     *                          closedpr,
     *                          30daysCommit,
     *                          contributors
     * return - count of requested operation
     */
    @GetMapping("/statistics/{username}/{reponame}")
    @ApiOperation(value = "Get counts of any one of the operations ( open/closed Pr, commits in 30 days, contributors)",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved count"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "The resource you were looking for is not found")
    }
    )
    public ResponseEntity<?> getGitApiStats(final @PathVariable(value = "username")@NotBlank @Size(max = 39, message = "Git username length must be less than 39 character")  String repoUserName,final @PathVariable(value = "reponame") @NotBlank @Size(max = 100, message = "Git repository length must be less than 100 character") String repoName,  @RequestParam(required = true) @NotBlank @Size(min= 1, max = 20, message = "Type length must be between 1 and 20") String type) throws GitException,RepoUserNotFoundException    {
        try {
            return ResponseEntity.ok().body(git.getRepoStatistics(repoUserName,repoName,type));
        } catch (GitException ge){
            LOGGER.error("GitController :: getGitApiStats GitException :: ",ge.getMessage());
            throw new GitException(ge.getMessage());
        } catch(RepoUserNotFoundException re){
            LOGGER.error("GitController :: getGitApiStats RepoUserNotFoundException :: ",re.getMessage());
            throw new RepoUserNotFoundException(re.getMessage());
        }
    }


}
