package yuma140902.mcmods.yumalib.updatecheck;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Checks updates by downloading release catalog TSV file from specified url
 * @author yuma140902
 *
 */
public class UpdateChecker {
	/**
	 * 
	 * @param modDisplayName Your mod name to show
	 * @param modVersionsTsvUrl URL to the release catalog TSV file. The records must be separated by a TAB character. All of CR, LF, and CRLF are supported. <br>
	 * Example:
	 * 1.0.0 (TAB) https://foo.com/bar/1.0.0 <br>
	 * 1.1.0 (TAB) https://foo.com/bar/1.1.0 <br>
	 * 1.2.0 (TAB) https://foo.com/bar/1.2.0 <br>
	 * 1.2.1 (TAB) https://foo.com/bar/1.2.1 <br>
	 * 2.0.0 (TAB) https://foo.com/bar/2.0.0 <br>
	 * recommended (TAB) 1.2.1 <br>
	 * latest (TAB) 2.0.0 <br>
	 * @param updateChannel The update channel to check
	 * @param currentModVersion The current version of your mod
	 * @param homepage Your mod's homepage url
	 * @param showDetailedLog whether to show detailed log
	 */
	public UpdateChecker(String modDisplayName, String modVersionsTsvUrl, EnumUpdateChannel updateChannel, String currentModVersion, String homepage, boolean showDetailedLog) {
		this.modDisplayName = modDisplayName;
		this.versionsTsvUrl = modVersionsTsvUrl;
		this.updateChannel = updateChannel;
		this.currentVersion = currentModVersion;
		this.availableNewVersion = currentModVersion;
		this.homePage = homepage;
		this.logger = LogManager.getLogger(modDisplayName + " - UpdateCheck");
		this.showDetailedLog = showDetailedLog;
	}
	
	private final String homePage;
	private final String versionsTsvUrl;
	private final EnumUpdateChannel updateChannel;

	private final String modDisplayName;
	private final String currentVersion;
	private String availableNewVersion;
	private HashMap<String, String> versions = null;
	private final Logger logger;
	private final boolean showDetailedLog;
	
	@SuppressWarnings("null")
	private String getFromUrl(String urlStr) {
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
	
	
	private static HashMap<String, String> getVersionsTable(String tsv){
		HashMap<String, String> hashMap = new HashMap<>();
		
		if(tsv == null || tsv.isEmpty()) return hashMap;
		
		String[] lines = tsv.split("[\\n\\r]");
		for (String line : lines) {
			String[] tmp = line.split("\\t");
			if(tmp.length < 2) continue;
			hashMap.put(tmp[0], tmp[1]);
		}
		
		return hashMap;
	}
	
	/**
	 * Check for updates
	 */
	public void checkForUpdates() {
		String versionsTsv = getFromUrl(versionsTsvUrl);
		if(versionsTsv == null || versionsTsv.isEmpty()) return;
		
		if(showDetailedLog) {
			logger.info("versionsTsv:");
			logger.info(versionsTsv);
		}
		
		this.versions = getVersionsTable(versionsTsv);
		
		String newestVersionChannel = this.updateChannel.toString();
		
		if(versions.keySet().contains(newestVersionChannel)) {
			String newestVersion = versions.get(newestVersionChannel);
			if(Version3.isLaterThan(newestVersion, currentVersion)) {
				this.availableNewVersion = newestVersion;
				return;
			}
		}
	}
	
	/**
	 * @return If there is a new version available
	 */
	public boolean hasNewVersionAvailable() {
		return Version3.isLaterThan(availableNewVersion, currentVersion);
	}
	
	/**
	 * @return new version. If there are no new version, current version
	 */
	public String getAvailableNewVersion() {
		return availableNewVersion;
	}
	
	/**
	 * @return Download link of the new version. If there are no new version, your homepage url
	 */
	public String getNewVersionUrl() {
		if(versions == null) return homePage;
		
		if(versions.keySet().contains(availableNewVersion)) {
			return versions.get(availableNewVersion);
		}
		else return homePage;
	}
	
	public String getModDisplayName() {
		return modDisplayName;
	}
}
