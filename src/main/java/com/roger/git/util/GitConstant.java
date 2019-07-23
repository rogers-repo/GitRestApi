package com.roger.git.util;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

/**
 * Maintains the constants for the application
 */
public class GitConstant {


    //Swagger - Constants
    public static final Contact SWAGGER_DEFAULT_CONTACT = new Contact("Roger D Dhas", "http://rogerdhas.com/",
            "rogerdhas@gmail.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
            .title("Git Repositories Microservices")
            .description("Microservices REST endpoint to get details about Git Repositories")
            .version("V1.0.0")
            .license("Roger 1.0")
            .licenseUrl("http://rogerdhas.com/licenses")
            .contact(SWAGGER_DEFAULT_CONTACT)
            .termsOfServiceUrl("http://rogerdhas.com/terms")
            .build();

    //Service Constants
    public static final String GIT_USRS_REPO_DETAILS = "https://api.github.com/users/GIT_REPO_USER_NME/repos";
    public static final String GIT_REPO_DETAILS = "https://api.github.com/repos/GIT_REPO_USER_NME/GIT_REPO_NME";
    public static final String REP_USR_NME = "GIT_REPO_USER_NME";
    public static final String REP_NME = "GIT_REPO_NME";
    public static final String PR_OPEN_DETAILS="/pulls?state=open";
    public static final String PR_CLOSED_DETAILS="/pulls?state=closed&since=";
    public static final String COMMIT_DETAILS="/commits?since=";
    public static final String CONTRIBUTORS_DETAILS="/contributors?since=";
    public static final String CONTRIBUTORS_TOP_DETAILS="/contributors?per_page=10&since=";

    public static final String PR_STATUS_OPEN="openpr";
    public static final String PR_STATUS_CLOSED="closedpr";
    public static final String COMMIT_HISTORY="30daysCommit";
    public static final String CONTRIBUTORS="contributors";

    //Error msgs
    public static final String REPO_EMPTY = "No Repository available";
    public static final String VALID_REPO = "Git Repository not found, please provide valid Git repository";
    public static final String INVALID_URL="Invalid Api url";


    public static final int RESPONSE_PER_PAGE=30;
}
