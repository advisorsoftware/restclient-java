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
public class RestApiExecutor {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	  String urlString = "https://sandbox.advisorsoftware.com:443/v1/mcs";
	  String input = "{\r\n\t\"startDate\": \"2015-01-01\",\r\n\t\"timePeriod\": 10,\r\n\t\"simulations\": 1000,"
				+ "\r\n\t\"rebalFrequency\": \"quarterly\",\r\n\t\"taxStatus\": \"tax\",\r\n\t\"taxRates\": {\r\n\t\t\"incomeRate\": 1,\r\n\t\t\"ltcgRate\": 1\r\n\t},\r\n\t\"portfolios\": [{\r\n\t\t\"name\": \"Current Portfolio\",\r\n\t\t\"cashBalance\": 10000,\r\n\t\t\"holdings\": [{\r\n\t\t\t\"lotId\": \"Lot-1\",\r\n\t\t\t\"symbol\": \"IBM\",\r\n\t\t\t\"purchaseDate\": \"2014-12-01\",\r\n\t\t\t\"quantity\": 100,\r\n\t\t\t\"currentValue\": 500,\r\n\t\t\t\"costBasis\": 1200\r\n\t\t}],\r\n\t\t\"incomes\": [{\r\n\t\t\t\"name\": \"Income1\",\r\n\t\t\t\"startMonth\": 1,\r\n\t\t\t\"startYear\": 2015,\r\n\t\t\t\"endMonth\": 12,\r\n\t\t\t\"endYear\": 2025,\r\n\t\t\t\"amount\": 1000,\r\n\t\t\t\"inflationSensitivity\": \"false\",\r\n\t\t\t\"frequency\": \"annual\"\r\n\t\t}],\r\n\t\t\"goals\": [{\r\n\t\t\t\"name\": \"Retirement\",\r\n\t\t\t\"startMonth\": 1,\r\n\t\t\t\"startYear\": 2020,\r\n\t\t\t\"endMonth\": 12,\r\n\t\t\t\"endYear\": 2025,\r\n\t\t\t\"amount\": 1000,\r\n\t\t\t\"inflationSensitivity\": \"false\",\r\n\t\t\t\"frequency\": \"annual\"\r\n\t\t}]\r\n\t}]\r\n}";

	  executeAPI(urlString, input);
	}
	
	/**
	 * Execute Rest API
	 * @param urlString
	 * @param input
	 */
	public static void executeAPI(String urlString, String input) {
		try {

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", getAuthorizationHeader());

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

	/**
	 * Create Authorization Header
	 * @return
	 */
	private static String getAuthorizationHeader() {
		return "ASI_CLIENT client_id=bfee7e17 client_secret=c24df3edd308a7a40b4cae0a306a55d4";
	}

}
