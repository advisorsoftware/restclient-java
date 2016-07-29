package com.asi.partners.da.integrator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PortRiskAnalysisApiExecutor {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String urlString = "https://sandbox.advisorsoftware.com/v1/portfolio_risk_analysis"; 
		String input = "{\r\n \"portfolios\": [\r\n {\r\n \"targetPortfolioFlag\": true,\r\n \"portfolioName\": \"target portfolio\",\r\n \"cashBalance\" : 0,\r\n \"holdings\": [\r\n {\r\n \"symbol\": \"MSFT\",\r\n \"holdingValue\": 2000\r\n }\r\n ]\r\n },\r\n {\r\n \"targetPortfolioFlag\": false,\r\n \"portfolioName\": \"some portfolio\",\r\n \"cashBalance\" : 500,\r\n \"holdings\": [\r\n {\r\n \"symbol\": \"IBM\",\r\n \"holdingValue\": 1500\r\n }\r\n ]\r\n }\r\n ]\r\n}";
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
