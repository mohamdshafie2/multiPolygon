import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ClientWithResponseHandler {

    private static final String YOUR_API_KEY = "";
    private static String googleUrl = "https://maps.googleapis.com/maps/api/staticmap?" +
            "center=40.714728,-73.998672&zoom=12&size=400x400&maptype=terrain&key=" + YOUR_API_KEY;


    public final static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(googleUrl);

            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        return null;
                    }
                }

            };

            String responseBody = httpclient.execute(httpget, responseHandler);
            int counter=0;
            do {
                System.out.println(responseBody);
                responseBody = httpclient.execute(httpget, responseHandler);

                counter ++;
                if(counter>1){
                    break;
                }

            }while (responseBody==null || responseBody.isEmpty());


            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            httpclient.close();
        }
    }

}