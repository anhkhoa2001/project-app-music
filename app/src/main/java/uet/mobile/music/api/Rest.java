package uet.mobile.music.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class Rest {

	private static HttpURLConnection 	httpCon = null;
	private static URL 					url;

	private static final String hostURL_Tro = "http://192.168.1.4:8080";
	private static final String hostURL_Nha = "http://192.168.1.70:8080";
	private static final String LocalhostURL = "http://192.168.0.13:3000";
	

	//tạo kết nối tới api
	public static void setup(String request) {
		try {
			url = new URL(hostURL_Tro + request);
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setUseCaches(false);
            httpCon.setReadTimeout(15 * 1000); // 15 seconds to timeout
			httpCon.setRequestProperty( "Content-Type", "application/json" );
			httpCon.setRequestProperty("Accept", "application/json");
		} catch (Exception e) {
			Log.v("donate","REST SETUP ERROR" + e.getMessage());
		}
	}

	public static String readDataFromServer(InputStream inputStream) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream));
			stringBuilder = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}

			reader.close();
		} catch (Exception e) {
			Log.v("Donate","GET REQUEST ERROR" + e.getMessage());
		}
		return stringBuilder.toString();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	//method get
	public static String get(String url) {
		String data = null;
		try {
			setup(url);
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);
			httpCon.connect();
			int HttpResult = httpCon.getResponseCode();
			//trả về mã 200 bắt đầu đọc dữ liệu
			if(HttpResult == HttpURLConnection.HTTP_OK) {
				data = readDataFromServer(httpCon.getInputStream());
			}
		} catch (Exception e) {
			Log.v("Donate","GET REQUEST ERROR" + e.getMessage());
		}

        return data;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////
	public static String delete(String url) {
		String data = null;
		try {
			setup(url);
			httpCon.setRequestMethod("DELETE");
			httpCon.connect();

			Log.v("Donate", "DELETE REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());

			int HttpResult = httpCon.getResponseCode();
			if(HttpResult == HttpURLConnection.HTTP_OK) {
				data = readDataFromServer(httpCon.getInputStream());
			}
		} catch (Exception e) {
			Log.v("Donate","DELETE REQUEST ERROR" + e.getMessage());
		}

		return data;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////
	public static String post(String url, String json) {

        OutputStreamWriter writer = null;
        String data = null;

        try {
            setup(url);
            httpCon.setRequestMethod("POST");
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.connect();

            Log.v("Donate", "POST REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());

            // đọc dữ liệu json vào trong body của post request
            writer = new OutputStreamWriter(httpCon.getOutputStream());
            writer.write(json);
            writer.close();

            int HttpResult = httpCon.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK) {
				data = readDataFromServer(httpCon.getInputStream());
            }
        } catch (Exception e) {
            Log.v("Donate","POST REQUEST ERROR" + e.getMessage());
        }

        return data;
	}
}