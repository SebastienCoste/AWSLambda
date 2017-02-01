package fr.sco.staticjo.lambda;


import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Hello  implements RequestHandler<Integer, String>{
	
    public String myHandler(int myCount, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("received : " + myCount);
        return String.valueOf(myCount);
    }

	@Override
	public String handleRequest(Integer input, Context context) {
		// TODO Auto-generated method stub
		return myHandler(input, context);
	}
}
