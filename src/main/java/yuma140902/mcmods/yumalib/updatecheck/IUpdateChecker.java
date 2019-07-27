package yuma140902.mcmods.yumalib.updatecheck;

public interface IUpdateChecker {
	void checkForUpdates();
	boolean hasNewVersionAvailable();
	String getAvailableNewVersion();
	String getNewVersionUrl();
	String getModDisplayName();
	String getNotificationMessageForChatGui();
}
