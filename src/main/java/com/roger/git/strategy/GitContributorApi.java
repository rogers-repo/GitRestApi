package com.roger.git.strategy;

import com.roger.git.exception.GitException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.roger.git.util.GitConstant.*;
import static com.roger.git.util.GitConstant.CONTRIBUTORS_TOP_DETAILS;


/**
 * Returns the Contributor git api endpoint
 */
public class GitContributorApi  implements Strategy {

    @Override
    public String createGitApiUrl(String gitUser,String repoName,String type) throws GitException {
        DateTimeFormatter dateFormatter=  DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return GIT_REPO_DETAILS.replace(REP_USR_NME, gitUser).replace(REP_NME,repoName) + CONTRIBUTORS_TOP_DETAILS + dateFormatter.format(LocalDate.now().minusDays(30));

    }
}
