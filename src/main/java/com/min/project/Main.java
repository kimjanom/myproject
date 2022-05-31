package com.min.project;

import com.min.project.dao.CrowlingDao;
import com.min.project.dao.TwitterDao;
import com.min.project.dto.TwitterDto;
import com.min.project.service.CrawlService;
import com.min.project.service.ThreedService;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@EnableJpaAuditing
@SpringBootApplication
public class Main {
    @Autowired
    static CrawlService crawlService = new CrawlService();

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
//        TwitterDto twitterDto = new TwitterDto();
//        twitterDto.setTwitId("asd");
//        twitterDto.setImageUrl("asd");
//        twitterDto.setUrl("asd");
//        twitterDto.setHash("[]");
//        twitterDto.setContect("asd");
//        SessionFactory factory = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(TwitterDto.class)
//                .addAnnotatedClass(CrawlService.class)
//                .addAnnotatedClass(TwitterDao.class)
//
//                .buildSessionFactory();
//        Session session = factory.openSession();
//        session.beginTransaction();
//        session.save(twitterDto);
//        session.getTransaction().commit();
//        ThreedService threedService = new ThreedService();
//        threedService.start();
//        String TwitterUrlApi = "https://twitter.com/i/api/2/search/adaptive.json?include_profile_interstitial_type=1&include_blocking=1&include_blocked_by=1&include_followed_by=1&include_want_retweets=1&include_mute_edge=1&include_can_dm=1&include_can_media_tag=1&include_ext_has_nft_avatar=1&skip_status=1&cards_platform=Web-12&include_cards=1&include_ext_alt_text=true&include_quote_count=true&include_reply_count=1&tweet_mode=extended&include_entities=true&include_user_entities=true&include_ext_media_color=true&include_ext_media_availability=true&include_ext_sensitive_media_warning=true&include_ext_trusted_friends_metadata=true&send_error_codes=true&simple_quoted_tweet=true&q=%EC%9E%91%EB%8C%80%EA%B8%B0&tweet_search_mode=live&count=20&query_source=typed_query&pc=1&spelling_corrections=1&ext=mediaStats%2ChighlightedLabel%2ChasNftAvatar%2CvoiceInfo%2Cenrichments%2CsuperFollowMetadata%2CunmentionInfo";
//        ExecutorService service = Executors.newFixedThreadPool(2);
//        service.submit(()->{
//            try {
//                crawlService.twitterCrawl(TwitterUrlApi);
//            }catch (IOException e){
//                System.out.println(e);
//            }
//        });
//        service.shutdown();
    }
    //        SpringApplication.run(Main.class, args);
//        System.out.printf("helloworld");
}
