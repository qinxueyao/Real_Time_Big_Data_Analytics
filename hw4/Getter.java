import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Getter {
	public static void main(String[] args) throws IOException{
		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("");
                cb.setOAuthConsumerSecret("");
                cb.setOAuthAccessToken("");
                cb.setOAuthAccessTokenSecret("");

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        List<Status> status = new ArrayList<Status>();
		try {
			status = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		BufferedWriter outputWriter = new BufferedWriter(new FileWriter("RecentTweets.txt"));
        for(Status st: status){
        	outputWriter.write(st.getUser().getName()+":  "+st.getText());
        	outputWriter.newLine();         
        }		
        outputWriter.flush();  
        outputWriter.close();        
	}
}