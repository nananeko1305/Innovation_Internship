package com.innovation.getInnovation;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GetInnovations implements RequestHandler<String, String> {

	public String handleRequest(String name, Context context){
		return "Hello World";
	}

}
