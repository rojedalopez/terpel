/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos.pushy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import java.util.Map;

public class PushyAPI {
    public static ObjectMapper mapper = new ObjectMapper();
    
    // Insert your Secret API Key here
    public static final String SECRET_API_KEY = "6cd74182bfbac4df532eb59e3148bbff269e4832af4b8f8888654ace4b6e6954";
    public static final String SECRET_API_KEY_ENTURNEX = "403f57652d1fee37ea80541da9033f78faa826ffd617a3d2c4fa714667120595";

    public static void sendPush(PushyPushRequest req, int app) throws Exception {
        // Get custom HTTP client
        HttpClient client = new DefaultHttpClient();
        
        String secreto = "";                
        if(app==1){
            secreto = SECRET_API_KEY;
        }else{
            secreto = SECRET_API_KEY_ENTURNEX;
        }
        // Create POST request
        HttpPost request = new HttpPost("https://api.pushy.me/push?api_key=" + secreto);

        // Set content type to JSON
        request.addHeader("Content-Type", "application/json");

        // Convert post data to JSON
        String json = mapper.writeValueAsString(req);

        // Send post data as string
        request.setEntity(new StringEntity(json));

        // Execute the request
        HttpResponse response = client.execute(request, new BasicHttpContext());

        // Get response JSON as string
        String responseJSON = EntityUtils.toString(response.getEntity());

        // Convert JSON response into HashMap
        Map<String, Object> map = mapper.readValue(responseJSON, Map.class);

        // Got an error?
        if (map.containsKey("error")) {
            // Throw it
            throw new Exception(map.get("error").toString());
        }
    }
}
