package common;



import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;

public class TwitterBridge {
	public static String getTwitterStatus(String tweetUrl) {
		long tweetId = Long.parseLong(tweetUrl.substring(tweetUrl
				.lastIndexOf('/') + 1));
		String tweet = "";
		TwitterFactory tf = new TwitterFactory();
		Twitter t = tf.getInstance();
		Status st;
		try {
			st = t.showStatus(tweetId);
			String s = st.getText();
			URLEntity[] urls = st.getURLEntities();
			if (urls != null) {
				for (URLEntity url : urls) {
					s = s.substring(0, url.getStart()) + "<a href=\""
							+ url.getURL() + "\">" + url.getDisplayURL()
							+ "</a>" + s.substring(url.getEnd());
				}
			}
			s = st.getUser().getScreenName() + ": " + s;
			tweet = s;
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return tweet;
	}
}
