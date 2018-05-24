package code;

import java.io.*;
import java.net.*;
import org.json.*;

public class CLIPUtilities {
	public static String getHTML(URL url) throws Exception {
	      StringBuilder result = new StringBuilder();
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }
	public static void putHTML(URL url, JSONObject content) throws Exception{
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		OutputStreamWriter out = new OutputStreamWriter(
		    httpCon.getOutputStream());
		out.write(content.toString());
		out.close();
		httpCon.getInputStream();
	}
}
