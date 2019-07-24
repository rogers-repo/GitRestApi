package com.roger.git.strategy;

import com.roger.git.exception.GitException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.roger.git.util.GitConstant.*;


/**
 * Returns the appropriate git api endpoint
 */
public class GitStatisticsApi implements Strategy {


    @Override
    public String createGitApiUrl(String gitUser,String repoName,String type) throws GitException {

        DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String url = GIT_REPO_DETAILS.replace(REP_USR_NME, gitUser).replace(REP_NME,repoName);
        if(PR_STATUS_OPEN.equalsIgnoreCase(type)) {
            url += PR_OPEN_DETAILS;
        }
        else if(PR_STATUS_CLOSED.equalsIgnoreCase(type))
        {
            url += PR_CLOSED_DETAILS + dateFormatter.format(LocalDate.now());
        }
        else if (COMMIT_HISTORY.equalsIgnoreCase(type))
        {
            url +=  COMMIT_DETAILS + dateFormatter.format(LocalDate.now().minusDays(30));
        }
        else if (CONTRIBUTORS.equalsIgnoreCase(type))
        {
            url +=  CONTRIBUTORS_DETAILS + dateFormatter.format(LocalDate.now().minusDays(30));
        }
        else
        {
            throw new GitException(INVALID_URL);
        }
        return url;
    }
}
