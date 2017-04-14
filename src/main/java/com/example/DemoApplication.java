package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    TweetRepository tweetRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setJSONStoreEnabled(true)
                .setOAuthConsumerKey("R33O6pfj63NFIcm2y0ymHEFK0")
                .setOAuthConsumerSecret("km9oCA8bnYxdR3IWZmhSusiSVpvLSB7M88vsUPZ4G3za6qB7RY")
                .setOAuthAccessToken("2569195398-PiNhNfTGo17jVkwh7qqX3eB2B3xNRx8o1NEcw45")
                .setOAuthAccessTokenSecret("Gwp4E2exADjQAazELOfZ7Q7wne7l7r7hT71KgjUGhanrF");
        Twitter unauthenticatedTwitter = new TwitterFactory(cb.build()).getInstance();
//First param of Paging() is the page number, second is the number per page (this is capped around 200 I think.
        Paging paging = new Paging(1, 100);
        List<Status> statuses = unauthenticatedTwitter.getUserTimeline("realDonaldTrump",paging);
        for (Status s: statuses
             ) {
            Tweet tweet = new Tweet();
            tweet.setId(s.getId());
            tweet.setText(s.getText());
            tweet.setStartDate(s.getCreatedAt());
            tweetRepository.save(tweet);
        }
    }
}
