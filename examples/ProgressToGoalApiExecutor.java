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
public class ProgressToGoalApiExecutor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String urlString = "https://sandbox.advisorsoftware.com:443/v1/progress_to_goal";
		String input = "{ \"accountValue\": 10000, \"accountReturn\": 0.06, \"goals\": [ { \"goalName\": \"Savings\", \"goalAmount\": 1000000, \"timeHorizon\": 20, \"contributionAmount\": 1000, \"contributionFrequency\": \"Annual\", \"contributionStartYear\": 2016, \"contributionEndYear\": 2025 } ]}";

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

