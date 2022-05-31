package com.min.project.controller;

import com.min.project.config.auth.JwtTokenProvider;
import com.min.project.dao.CrowlingDao;
import com.min.project.dao.TwitterDao;
import com.min.project.dao.UploadDao;
import com.min.project.dto.CrowlingDto;
import com.min.project.dto.TwitterDto;
import com.min.project.dto.UploadDto;
import com.min.project.dto.UserDto;
import com.min.project.form.UserForm;
import com.min.project.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class VueController {
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "C:/Users/e2on/IdeaProjects/myproject/chromedriver.exe";
    //    @Autowired
//    CookieCsrfTokenRepository cookieCsrfTokenRepository;
    @Autowired
    ThreedService threedService;
    @Autowired
    CrawlService crawlService;
    @Autowired
    TwitterDao twitterDao;
    @Autowired
    CrowlingDao crowlingDao;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    UploadDao uploadDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    UploadServiceImpl uploadService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
//    @GetMapping("/vue/board")
//    public List<UploadForm> getBoardList() {
//        System.out.println("getBoardList 도착");
//        List<UploadForm> list = uploadService.getUploadlist();
//        return list;
//    }

    @GetMapping("/getsession")
    public String getSession(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();
        System.out.println("세션:" + request.getSession());
        System.out.println("세션id:" + session.getId());
        String a = String.valueOf(session.getId());
        System.out.println("authentication: " + authentication.getDetails());
        return a;
    }

    //    @GetMapping("/vue/loginerror")
//    public ResponseEntity<Object> error(){
//        System.out.println("에러");
//        return new ResponseEntity( HttpStatus.NOT_FOUND);
//    }
//    @GetMapping("/vue")
//    public String vueLogin(){
//
//        return
//    }
//    @PostMapping("/vue/login")
//    public ResponseEntity vueLogin(UserDto userDto, HttpSession session) {
//
//        String email = userDto.getEmail();
//        String password = userDto.getPassword();
//
//        UserForm userForm = userService.authenticate(email, password);
//        if (userForm == null) {
//            System.out.println("아이디나 패스워드가 틀림");
//            return null;
//        }
//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(email, password);
//        System.out.print("토큰 체크 :"+ token);
//        Authentication authentication = authenticationManager.authenticate(token);
////
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
//                SecurityContextHolder.getContext());
//        userForm.setToken(RequestContextHolder.currentRequestAttributes().getSessionId());
//        System.out.println("세션을 확인해보자:"+ HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
//        System.out.println("세션을 확인해보자:"+ SecurityContextHolder.getContext());
//        return ResponseEntity.ok(userForm);
//    }
    @PostMapping("/vue/login")
    public ResponseEntity vueLogin(UserDto userDto, HttpSession session, HttpServletResponse resp) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        UserForm userForm = userService.authenticate(email, password);
        if (userForm == null) {
            System.out.println("아이디나 패스워드가 틀림");
            return ResponseEntity.ok("error");
        } else {
            String a = jwtTokenProvider.createToken(userForm.getEmail(), userForm.getRole());
//            Cookie cookie = new Cookie("X-AUTH-TOKEN", a);
//            cookie.setHttpOnly(true);
////            cookie.setDomain();
//            resp.addCookie(cookie);
            //https://stackoverflow.com/questions/62261030/unable-to-set-cookies-with-spring-boot-on-the-serve-side
            //읽어보고 쫌더 깔끔하게 바꾸자
            CookieGenerator cookieGenerator = new CookieGenerator();
            cookieGenerator.setCookieHttpOnly(true);
            cookieGenerator.setCookieName("X-AUTH-TOKEN");
            cookieGenerator.addCookie(resp, a);
            System.out.println("크리에이트 토큰" + a);
            HashMap<String, String> data = new HashMap<>();
            data.put("email", userForm.getEmail());
            data.put("Role", userForm.getRole());
            data.put("name", userForm.getUsername());
            data.put("token", a);
//            data.put("resp",resp);
            return ResponseEntity.ok(data);
        }
    }

    @PostMapping("/vue/musicTableDesc")
    public List vueTable() {
        System.out.println("/vue/musicTable 도착");
        List upLoadList = uploadService.getVueUploadlist();
        System.out.println("/vue/musicTable 도착" + upLoadList);
        return upLoadList;
    }

    @PostMapping("/vue/board")
    public List vueBoard() {
        List upLoadList = uploadService.getUploadlist();
        return upLoadList;
    }

    @PostMapping("/vue/album/detail")
    public List albumTable(@RequestParam("id") String id1) {

        Long id = Long.parseLong(id1);
        System.out.println("111" + id);
        uploadDao.updateVisit(id);
        UploadDto uploadDto = uploadDao.findById(id).get();
        List list = new ArrayList<>();
        list.add(uploadDto.getId());
        list.add(uploadDto.getName());
        list.add(uploadDto.getFileOriginalName());
        list.add(uploadDto.getCategory());
        list.add(uploadDto.getViews());
        list.add(uploadDto.getContent());
        LocalDateTime date = uploadDto.getUploadDate();
        list.add(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        String musicRoot = "http://localhost:8081/audioLink/+" + uploadDto.getId() + ".mp3";
        list.add(musicRoot);
        String imageRoot = "http://localhost:8081/imageLink/" + uploadDto.getId() + ".com";
        list.add(imageRoot);
        return list;
    }

    //    @PostMapping("/vue/jsonHandle")
//    public void json(@RequestBody HashMap<String, Object> param){
////        System.out.println("param :"+param);
//        ArrayList file = (ArrayList) param.get("file");
////         List frame_index = (List) file.get("frame_index");
//        HashMap list = (HashMap) file.get(0);
//        ArrayList frames = (ArrayList) list.get("frames");
////        -------------------------------------------------------
//        HashMap frames_hash = (HashMap) frames.get(0);
//        String frame_index = (String) frames_hash.get("frame_index");
//        ArrayList objectsArray = (ArrayList) frames_hash.get("objects");
//        ArrayList persons = ();
//        System.out.println("frame_index :"+frames_hash.get("frame_index"));
//        System.out.println("frame_index :"+frames_hash.get("objects"));
//        System.out.println("frame_index :"+frames_hash.get("persons"));
////        System.out.println("frame_index :"+frame_index);
//    }
    @PostMapping(value = "/vue/register")
    public boolean register(UserForm form) {
        System.out.println("ㅂㅂ" + form.getPassword());
        boolean a = userService.register(form);
        if (a) {
            return true;
        }
        return false;
    }


    //    @ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "서버 오류")
//    })
    @PostMapping("/vue/emailConfirm")
    public ResponseEntity<?> emailConfirm(@RequestParam("email") String email) throws Exception {
        System.out.println("이메일 테스트" + email);
        String confirm = emailService.sendSimpleMessage(email);
//        String epw =emailService.getEPW();
//        return ResponseEntity.status(200).body(BaseResponseBody.of(200, confirm));
        return ResponseEntity.status(200).body(confirm);
    }

    @PostMapping("/vue/crawling")
    public ArrayList crol(@RequestParam("url") String url) throws IOException {
        ArrayList Turl = null;
//            String q = "스칼라"; // 검색어
//
//            Document doc = Jsoup.connect("https://ac.search.naver.com/nx/ac")
//                    .header("origin", "http://www.naver.com")
//                    .header("referer", "https://www.naver.com/")
//                    .header("accept-encoding", "gzip, deflate, sdch, br")
//                    .data("con", "1")
////                    .data("_callback", "")    // _callback 파라미터를 비우면 JSON이 리턴된다!
//                    .data("rev", "4")
//                    .data("r_enc", "UTF-8")
//                    .data("q", q)             // 임의로 몇개의 파라미터와 헤더를 생략했다.
//                    .data("st", "100")        // 각 파라미터가 무엇을 뜻하는지를 확인해 적절하게 사용하는 것도 좋지만
//                    .data("q_enc", "UTF-8")   // 비정상적인 요청으로 감지해 아이디나 아이피가 밴 될 우려도 있으므로
//                    .data("r_format", "json") // 특별한 이유가 없다면 모두 포함하는 것이 좋다.
//                    .data("t_koreng", "1")
//                    .data("ans", "2")
//                    .data("run", "2")
//                    .ignoreContentType(true) // HTML Document가 아니므로 Response의 컨텐트 타입을 무시한다.
//                    .get();
//            System.out.println(doc.text());
//            List<String> result = new ArrayList<>();
//
//// org.json 라이브러리를 사용해 결과를 파싱한다.
//            JSONObject jsonObject = new JSONObject(doc.text());

//            JSONArray items = (JSONArray) ((JSONArray) jsonObject.get("items")).get(0);
//            for(int i=0; i<items.length(); i++) {
//                String item = (String) (((JSONArray) items.get(i)).get(0));
//                result.add(item);
//            }

//        }
        try {
            //1.수집 대상 URL
            String TwitterUrl = "https://twitter.com/search?q=%EC%9E%91%EB%8C%80%EA%B8%B0&src=recent_search_click";
            String TwitterUrlApi = "https://twitter.com/i/api/2/search/adaptive.json?include_profile_interstitial_type=1&include_blocking=1&include_blocked_by=1&include_followed_by=1&include_want_retweets=1&include_mute_edge=1&include_can_dm=1&include_can_media_tag=1&include_ext_has_nft_avatar=1&skip_status=1&cards_platform=Web-12&include_cards=1&include_ext_alt_text=true&include_quote_count=true&include_reply_count=1&tweet_mode=extended&include_entities=true&include_user_entities=true&include_ext_media_color=true&include_ext_media_availability=true&include_ext_sensitive_media_warning=true&include_ext_trusted_friends_metadata=true&send_error_codes=true&simple_quoted_tweet=true&q=%EC%9E%91%EB%8C%80%EA%B8%B0&tweet_search_mode=live&count=20&query_source=typed_query&pc=1&spelling_corrections=1&ext=mediaStats%2ChighlightedLabel%2ChasNftAvatar%2CvoiceInfo%2Cenrichments%2CsuperFollowMetadata%2CunmentionInfo";
            String TwitterScrollApi = "https://twitter.com/i/api/2/search/adaptive.json?include_profile_interstitial_type=1&include_blocking=1&include_blocked_by=1&include_followed_by=1&include_want_retweets=1&include_mute_edge=1&include_can_dm=1&include_can_media_tag=1&include_ext_has_nft_avatar=1&skip_status=1&cards_platform=Web-12&include_cards=1&include_ext_alt_text=true&include_quote_count=true&include_reply_count=1&tweet_mode=extended&include_entities=true&include_user_entities=true&include_ext_media_color=true&include_ext_media_availability=true&include_ext_sensitive_media_warning=true&include_ext_trusted_friends_metadata=true&send_error_codes=true&simple_quoted_tweet=true&q=%EC%9E%91%EB%8C%80%EA%B8%B0&tweet_search_mode=live&count=20&query_source=typed_query&cursor=scroll%3AthGAVUV0VFVBaCgNSt0NvdsioWgMDR1Y3Z7rIqEnEV5P95FYCJehgHREVGQVVMVDUBFQ4VAAA%3D&pc=1&spelling_corrections=1&ext=mediaStats%2ChighlightedLabel%2ChasNftAvatar%2CvoiceInfo%2Cenrichments%2CsuperFollowMetadata%2CunmentionInfo";
            String DCURL = "https://gall.dcinside.com/board/lists/?id=tree&page=1";
            String NaverURL = "https://news.naver.com/main/mainNews.naver?sid1=105&date=%2000:00:00&page=1";
            String DaumURL = "https://news.daum.net/api/harmonydic/contents/news.json?category=digital&approved=true&page=1&pageSize=20&pagesToShow=10&range=1";
            //2. Connection생성
//            Connection connectionDC = Jsoup.connect(DCURL);
            Connection conn = Jsoup.connect(NaverURL);
            Connection connectionDaum = Jsoup.connect(DaumURL);
            //3.HTML 파싱
//            Document DCHtml = connectionDC.get();
            Document DaumHtml = connectionDaum.ignoreContentType(true).get();
            Document html = conn
                    .ignoreContentType(true)
                    .get();
            // 4. 요소 탐색
            // 4-1. Attribute 탐색userdto
            //트위터 크롤링--------------------------------------
            System.out.println("뭐냐 "+url);
            Turl = crawlService.twitterCrawl(url);
            System.out.println("다음 url1:"+Turl);
//            threedService.start();
            //트위터------------------------------------
            // --------
            // dc 데이터 분석---------------------
//            int cout =0;
//            for (int a = 1; a <= 10; a++) {
//                DCURL = "https://gall.dcinside.com/board/lists/?id=tree&page="+a;
//                Connection connectionDC = Jsoup.connect(DCURL);
//                Document DCHtml = connectionDC.get();
//                Elements elements = DCHtml.getElementsByTag("tbody");
//                for (Element element : elements) {
//                    Elements elements2 = element.getElementsByTag("tr");
////                System.out.println("디시 요소의요소"+elements2.select("td.gall_writer"));
//                    for (Element element1 : elements2) {
//                        System.out.println("디시 제목" + element1.select("td.gall_tit").select("a").text());
//                        System.out.println("디시 url" + element1.select("td.gall_tit").select("a").attr("href"));
//                        System.out.println("디시 이름" + element1.select("td.gall_writer").select("em").text());
//                        cout++;
//
////                    System.out.println("디시 요소의요소"+element1.getElementsByClass("gall_writer"));
//                    }
//                }
//                Thread.sleep(5000);
//            }
//            System.out.println("dc 게시물 : "+ cout);
            //dc --------------------------
            Elements fileblocks = html.getElementsByTag("body");
            ArrayList<String> arrayList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(fileblocks.text());
            arrayList.add((String) jsonObject.get("airsResult"));
            jsonObject = new JSONObject(arrayList.get(0));
            arrayList.clear();
            arrayList.add(String.valueOf(jsonObject.get("result")));
            jsonObject = new JSONObject(arrayList.get(0));
            arrayList.clear();
            JSONArray arrayList1 = (JSONArray) jsonObject.get("105");
            for (int b = 0; b < arrayList1.length(); b++) {
                CrowlingDto crowlingDto = new CrowlingDto();
//                System.out.println("어레이"+arrayList1.get(b));
                JSONObject forDB = (JSONObject) arrayList1.get(b);
                crowlingDto.setMedia((String) forDB.get("officeName"));
                crowlingDto.setTitle((String) forDB.get("title"));
                crowlingDto.setContect((String) forDB.get("summary"));
                crowlingDto.setImageUrl((String) forDB.get("imageUrl"));
                crowlingDto.setUrl("https://n.news.naver.com/mnews/article/" + forDB.get("officeId") + "/" + forDB.get("articleId") + "/" + "?sid=" + forDB.get("sectionId"));
//                crowlingDao.save(crowlingDto);
            }
            //다음 크롤링 데이터
            JSONObject DaumJsonObject = new JSONObject(DaumHtml.getElementsByTag("body").text());
            JSONArray DaumJArray = (JSONArray) DaumJsonObject.get("list");
            for (int b = 0; b < DaumJArray.length(); b++) {
//                System.out.println("다음 리스트:"+DaumJArray.get(b));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return Turl;
    }

    @PostMapping("/vue/getData")
    public List getData() {
        List<TwitterDto> data = (List) twitterDao.findAll();
        List<TwitterDto> List = new ArrayList<>();
        for (TwitterDto dto : data) {
            List.add(dto);
        }
        return List;
    }

//    @PostMapping("/vue/croling")
//    public void crol() {
//        // WebDriver 경로 설정
//        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//
//        // 2. WebDriver 옵션 설정
//        ChromeOptions options = new ChromeOptions();
////        options.addArguments("--start-maximized");
//        options.addArguments("headless");
//        options.addArguments("--disable-popup-blocking");
//        WebDriver driver = new ChromeDriver(options);
//        WebDriver driver2 = new ChromeDriver(options);
//        String url = "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=105#&date=%2000:00:00&page=1";
//        try {
//            driver.get(url);
//            int count = 0;
//            for(int a = 1; a<5;a++){
//                for(int b=1;b<6;b++){
//                    CrowlingDto crowlingDto = new CrowlingDto();
//                    String imgUrl = driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[2]/div/div/div[6]/ul["+a+"]/li["+b+"]/dl/dt[1]/a/img")).getAttribute("src");
//                    String hrefUrl = driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[2]/div/div/div[6]/ul["+a+"]/li["+b+"]/dl/dt[2]/a")).getAttribute("href");
//                    String title = driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[2]/div/div/div[6]/ul["+a+"]/li["+b+"]/dl/dt[2]/a")).getAttribute("text");
//                    String context = driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[2]/div/div/div[6]/ul["+a+"]/li["+b+"]/dl/dd/span[1]")).getText();
//                    String media = driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[2]/div/div/div[6]/ul["+a+"]/li["+b+"]/dl/dd/span[2]")).getText();
//                    System.out.println("이미지 url:"+imgUrl+"href URL:"+hrefUrl+"제목:"+title+"내용:"+context+"미디어:"+media);
//                    driver2.get(hrefUrl);
//                    String writter=driver2.findElement(By.xpath("//*[@id=\"ct\"]/div[1]/div[3]/div[2]/a/em")).getText();
//                    String text=driver2.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/div[1]/div[2]/div[1]/div")).getAttribute("innerText");
//                    System.out.println("글쓴이:"+writter);
//                    System.out.println("텍스트:"+text);
//                    Thread.sleep(5000);
//                    count++;
////                    crowlingDto.setImageUrl(imgUrl);
////                    crowlingDto.setUrl(hrefUrl);
////                    crowlingDto.setTitle(title);
////                    crowlingDto.setContect(context);
////                    crowlingDto.setMedia(media);
////                    crowlingDao.save(crowlingDto);
//                }
//            }
//            System.out.println("총"+count+"개의 게시물을 크롤링 했습니다");
//            System.out.println("------------------------");
////            WebElement element = driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[2]/div/div/div[6]/ul[2]/li[4]/dl/dt[1]/a/img"));
////            String imgUrl = element.getAttribute("src");
////            System.out.println(element.toString());
////            System.out.println(imgUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        driver.close();
//    }
}
