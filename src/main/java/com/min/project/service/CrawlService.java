package com.min.project.service;

import com.min.project.dao.TwitterDao;
import com.min.project.dto.TwitterDto;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CrawlService {
    String nextUrl = null;

    @Autowired
    TwitterDao twitterDao;

    public void twitterCrawl(String url) throws IOException {
        Connection connectionTwitterApi = Jsoup.connect(url);
        //트위터 커넥션
        Document TwitterHtml = connectionTwitterApi.header("accept", "*/*")
//                    .header("Accept-Encoding", "gzip, deflate, br")
//                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("authorization", "Bearer AAAAAAAAAAAAAAAAAAAAANRILgAAAAAAnNwIzUejRCOuH5E6I8xnZz4puTs%3D1Zv7ttfk8LF81IUq16cHjhLTvJu4FA33AGWWjCpTnA")
                .header("referer", "https://twitter.com/search?q=%EC%9E%91%EB%8C%80%EA%B8%B0&src=typed_query&f=live")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36")
                .header("x-csrf-token", "dac2a45364e499057ee881fe254c1d12")
                .header("x-guest-token", "1528614164419461120")
                .header("x-twitter-active-user", "yes")
                .header("x-twitter-client-language", "ko")
                .ignoreContentType(true)
                .get();
        JSONObject json = new JSONObject(TwitterHtml.text());
        JSONArray jsonArray = new JSONArray();
        ArrayList list = new ArrayList();
        String nextUrl = twitterNextUrlOb(json.getJSONObject("timeline").get("instructions"));
        System.out.println("다음 url:"+nextUrl.substring(7));
        json = (JSONObject) json.get("globalObjects");
        json = json.getJSONObject("tweets");
        Iterator i = json.keys();
        int count = 0;
        while (i.hasNext()) {
            String key = i.next().toString();
            list.add(key);
        }
        for (int j = 0; j < list.size(); j++) {
            System.out.println("이거다" + list.get(j));
            if (twitterDao.findByTwitId((String) list.get(j)) != null) {
                System.out.println("존재" + list.get(j));
                break;
            }
            TwitterDto twitterDto = new TwitterDto();
            try {
                System.out.println("----------------------------------------------");
                List hashList = new ArrayList();
                System.out.println("트위터 내용:" + json.getJSONObject((String) list.get(j)).get("full_text"));
                int len = json.getJSONObject((String) list.get(j)).getJSONObject("entities").getJSONArray("hashtags").length();
                for (int i1 = 0; i1 < len; i1++) {
                    JSONObject JsonHash = (JSONObject) json.getJSONObject((String) list.get(j)).getJSONObject("entities").getJSONArray("hashtags").get(i1);
                    hashList.add(JsonHash.get("text"));
                }
                System.out.println("트위터 해시:" + hashList.toString());
                JSONObject json1 = (JSONObject) json.getJSONObject((String) list.get(j)).get("extended_entities");
                System.out.println("트위터 이미지:" + json1.getJSONArray("media").getJSONObject(0).get("media_url"));
                System.out.println("트위터 url:" + json1.getJSONArray("media").getJSONObject(0).get("url"));
//                json.getJSONObject((String) list.get(j))
                twitterDto.setContect((String) json.getJSONObject((String) list.get(j)).get("full_text"));
                twitterDto.setTwitId((String) list.get(j));
                twitterDto.setHash(hashList.toString());
                twitterDto.setUrl((String) json1.getJSONArray("media").getJSONObject(0).get("url"));
                twitterDto.setImageUrl((String) json1.getJSONArray("media").getJSONObject(0).get("media_url"));
                twitterDao.save(twitterDto);
            } catch (JSONException e) {
                System.out.println(e);
            }
            count++;
            System.out.println("----------------------------------------------");
        }
        String nextTwiScrollUrl = "https://twitter.com/i/api/2/search/adaptive.json?include_profile_interstitial_type=1&include_blocking=1&include_blocked_by=1&include_followed_by=1&include_want_retweets=1&include_mute_edge=1&include_can_dm=1&include_can_media_tag=1&include_ext_has_nft_avatar=1&skip_status=1&cards_platform=Web-12&include_cards=1&include_ext_alt_text=true&include_quote_count=true&include_reply_count=1&tweet_mode=extended&include_entities=true&include_user_entities=true&include_ext_media_color=true&include_ext_media_availability=true&include_ext_sensitive_media_warning=true&include_ext_trusted_friends_metadata=true&include_ext_vibe_tag=true&send_error_codes=true&simple_quoted_tweet=true&q=%EC%9E%91%EB%8C%80%EA%B8%B0&count=20&query_source=typed_query&cursor=scroll%3A"+
                nextUrl.substring(7)+
                "&pc=1&spelling_corrections=1&ext=mediaStats%2ChighlightedLabel%2ChasNftAvatar%2CvoiceInfo%2Cenrichments%2CsuperFollowMetadata%2CunmentionInfo";
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
        }
        twitterCrawl(nextTwiScrollUrl);
        System.out.println("-------------------------------------------------------------크롤링 개수:" + count);
    }

    public String  twitterNextUrlOb(Object json) {
//        System.out.println("스크롤 url:" + jsonObject.getJSONArray("instructions").getJSONObject(0).getJSONObject("addEntries").getJSONArray("entries"));

        try {
            ArrayList list = new ArrayList();
            JSONObject jsonObject = (JSONObject) json;
            Iterator i = jsonObject.keys();
            while (i.hasNext()) {
                String key = i.next().toString();
//                System.out.println("키" + key);
                list.add(key);
            }
            while(true) {
                Object a = jsonObject.get((String) list.remove(list.size() - 1));
//                System.out.println("중간1:" + a);
                twitterNextUrlOb(a);
                if(list.size() == 0){
                    break;
                }
            }
        } catch ( NullPointerException|ClassCastException | JSONException ex1) {
            try {
                while(true) {
                    JSONArray jsonArray = (JSONArray) json;
                    Object a = jsonArray.remove(jsonArray.length() - 1);
//                    System.out.println("중간2:" + a);
                    twitterNextUrlOb(a);
                    if(jsonArray.length() == 0){
                        break;
                    }
                }

            } catch (ClassCastException | JSONException ex2) {
//                System.out.println("밸류:" + json.toString());
                if(json.toString().contains("scroll")){
                    nextUrl = json.toString();
                }

            }
        }
        return nextUrl;
    }

//    public void twitterNextUrlAr(JSONArray jsonArray) {
//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                twitterNextUrlOb(jsonArray.getJSONObject(i));
//            }
//        }catch (ClassCastException e){
//            for (int i = 0; i < jsonArray.length(); i++) {
//                twitterNextUrlAr((JSONArray) jsonArray.get(i));
//            }
//        }
//
//    }
}
