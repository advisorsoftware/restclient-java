package com.asi.partners.da.integrator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * @author Surabhi
 *
 */
public class MonthlyDepositApiExecutor {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String urlString = "https://sandbox.advisorsoftware.com/v1/goal_solver/monthly_deposit";
		String input = "{\r\n\t\"accountValue\": \"5000\",\r\n\t\"accountReturn\": \"6\",\r\n\t\"goals\": [{\r\n\t\t\"goalName\": \"Rainy Day Fund\",\r\n\t\t\"goalAmount\": \"25000\",\r\n\t\t\"timeHorizon\": \"10\",\r\n\t\t\"contributionAmount\": \"1000\",\r\n\t\t\"contributionFrequency\": \"Annual\",\r\n\t\t\"contributionStartYear\": \"2016\",\r\n\t\t\"contributionEndYear\": \"2025\"\r\n\t}]\r\n}\r";

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
