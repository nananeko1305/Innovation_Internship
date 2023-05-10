package API_Gateway;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

public class HelloFunctHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {{

}

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String responseBody = "{ \"message\": \"Hello!\" }";
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(headers)
                .withBody(responseBody);

        return response;
    }
}
