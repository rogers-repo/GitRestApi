package com.roger.git.util;

import com.roger.git.bean.ApiCustomResponse;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import static com.roger.git.util.GitConstant.RESPONSE_PER_PAGE;


/**
 * Utility class
 */
public class ApiResponseUtil {

    public static int calulatedCount=0;

    public static final Logger LOGGER= LogManager.getLogger(ApiResponseUtil.class);

    public static ApiCustomResponse apiResponse(String message)
    {
       return new ApiCustomResponse(LocalDateTime.now(), HttpStatus.OK.value(),message);
    }


    /**
     * Reads the header Link from http headers
     * and extract the last page to be consumed
     * and calculate the number of records
     */
    public static String getRelLink(HttpHeaders headers, boolean calculateCount ){
        calulatedCount=0;
        String relString="Not Found";
        if(headers.containsKey("Link"))
        {
            try {
                String[] linkHeaders = headers.get("Link").get(0).replace("<", "").replace(">", "").split(",");
                String[] nextRels=linkHeaders[1].split(";");
                if(!nextRels[1].trim().equals("rel=\"first\"") && calculateCount) {
                    int page=Integer.parseInt(nextRels[0].substring(nextRels[0].lastIndexOf("=") + 1));
                    if(page>1) {calulatedCount=RESPONSE_PER_PAGE * (page-1);}
                    relString=nextRels[0].trim();
                }
            }
            catch (IndexOutOfBoundsException er)
            {
                LOGGER.error("ApiResponseUtil :: getRelLink IndexOutOfBoundsException :: ", er);
            }
        }
        return relString;
    }

    /**
     * Sets ssl to the rest template and extract api response
     */
    public static  ResponseEntity<?> processRequest(String url, HttpMethod httpMethod, ParameterizedTypeReference typRef){
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate.exchange(url,HttpMethod.GET, null, typRef);
    }
}
