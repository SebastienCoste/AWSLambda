package fr.sco.staticjo.lambda;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpLambdaConnection {

	private static final String URL_API_GATEWAY_PATH_FINDER = "https://gvct92f2jl.execute-api.us-east-2.amazonaws.com/Test/pathFinder";
	private final String USER_AGENT = "Mozilla/5.0";
	private HttpsURLConnection con  = null;
	
	public HttpLambdaConnection() throws MalformedURLException, IOException{
		String url = URL_API_GATEWAY_PATH_FINDER;
		URL obj = new URL(url);
		con = (HttpsURLConnection) obj.openConnection();
	}
	// HTTP POST request
		public String sendPost(String data) throws Exception {

			

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

//			String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + URL_API_GATEWAY_PATH_FINDER);
			System.out.println("Post parameters : " + data);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			return response.toString();

		}
}
