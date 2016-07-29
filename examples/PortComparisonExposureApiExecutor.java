package com.asi.partners.da.integrator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PortComparisonExposureApiExecutor {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String urlString = "https://sandbox.advisorsoftware.com/v1/portfolio_comparisons/exposures"; 
		String input = "{\r\n \"allocationsDisplayLevel\": \"1\",\r\n \"compareAllocations\": \"1\",\r\n \"compareSectors\": \"1\",\r\n \"compareGeos\": \"1\",\r\n \"portfolios\": [\r\n {\r\n \"portfolioName\": \"Portfolio A\",\r\n \"cashBalance\": \"20000\",\r\n \"holdings\": [\r\n {\r\n \"symbol\": \"IVV\",\r\n \"holdingValue\": \"40000\"\r\n },\r\n {\r\n \"symbol\": \"VWO\",\r\n \"holdingValue\": \"40000\"\r\n }\r\n ]\r\n },\r\n {\r\n \"portfolioName\": \"portfolio B\",\r\n \"cashBalance\": \"10000\",\r\n \"holdings\": [\r\n {\r\n \"symbol\": \"IVV\",\r\n \"holdingValue\": \"90000\"\r\n }\r\n ]\r\n },\r\n {\r\n \"portfolioName\": \"portfolio C\",\r\n \"cashBalance\": \"10000\",\r\n \"holdings\": [\r\n {\r\n \"symbol\": \"VWO\",\r\n \"holdingValue\": \"90000\"\r\n }\r\n ]\r\n }\r\n ]\r\n}";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "ASI_CLIENT client_id=xxxxx client_secret=xxxxxxxxxxxxxxxxxxx");

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
