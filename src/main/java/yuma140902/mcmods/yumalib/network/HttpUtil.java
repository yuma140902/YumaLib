package yuma140902.mcmods.yumalib.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtil {
	
	private static final Logger logger = LogManager.getLogger("YumaLib - HttpUtil");
	
	@SuppressWarnings("null")
	public static String getFromUrl(String urlStr) {
		boolean hasError = false;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");
			conn.connect();
		
			is = conn.getInputStream();
			isr = new InputStreamReader(is, "UTF8");
			br = new BufferedReader(isr);
		
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}
		}
		catch(RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			logger.warn(e);
			hasError = true;
		}
		finally {
			try {
				is.close();
			}
			catch (Exception e) {
				logger.warn(e);
				hasError = true;
			}
			try {
				isr.close();
			}
			catch (Exception e) {
				logger.warn(e);
				hasError = true;
			}
			try {
				br.close();
			}
			catch (Exception e) {
				logger.warn(e);
				hasError = true;
			}
		}
		
		return hasError ? null : sb.toString();
	}
}
