package fr.sco.staticjo.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.S3Event;

public class LambdaFunctionHandler implements RequestHandler<Integer, String> {

    @Override
    public String handleRequest(Integer myCount, Context context) {
            LambdaLogger logger = context.getLogger();
            logger.log("received : " + myCount);
            return String.valueOf(myCount);
        }

}
