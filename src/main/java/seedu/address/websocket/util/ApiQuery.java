package seedu.address.websocket.util;

import seedu.address.commons.core.LogsCenter;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiQuery {
    public static URL url;
    private Logger logger;

    /**
     * Generate an instance of an API query with the given url
     * @param url of the query
     */
    public ApiQuery(String url){
        URL generated_url = UrlUtil.generateUrl(url);
        this.logger = LogsCenter.getLogger(this.getClass());

        if(generated_url == null){
            this.url = null;
        } else {
            this.url = generated_url;
        }
    }

    /**
     * Executes the query
     * @return QueryResult
     */
    public QueryResult execute(){

        Integer responseCode = null;
        String output = "";

        if(this.url == null){
            output = "Malformed URL Exception";
            return new QueryResult(responseCode, output);
        }

        HttpsURLConnection conn = establishHttpsConnection(this.url);
        if(conn == null){
            output = "Unable to establish connection";
            conn.disconnect();
            return new QueryResult(responseCode, output);
        } else {
            try{
                responseCode = conn.getResponseCode();
                if(responseCode != 200){
                    output = "HTTP response Error!";
                    conn.disconnect();
                    return new QueryResult(responseCode, output);
                } else {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(this.url.openStream()))) {
                        String line;
                        while((line = br.readLine()) != null) {
                            output += line;
                            logger.fine(line);
                        }
                    } catch (IOException ioe) {
                        output = "Unable to read response";
                        conn.disconnect();
                        return new QueryResult(responseCode, output);
                    }
                }
            } catch (IOException ioe){
                output = "Connection error";
                conn.disconnect();
                return new QueryResult(responseCode, output);
            }
        }
        conn.disconnect();
        return new QueryResult(responseCode, output);
    }

    /**
     * Establish the HTTPS connection
     * @param url of the connection
     * @return HttpsURLConnection
     */
    private HttpsURLConnection establishHttpsConnection(URL url){
        HttpsURLConnection conn;
        try{
            conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            return conn;
        } catch(IOException ioe){
            System.out.println("Failed to establish connection with " + url.toString());
            return null;
        }
    }
}
