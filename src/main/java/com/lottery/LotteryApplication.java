package com.lottery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class LotteryApplication {
    
    @Autowired
    private LotteryResultService lotteryResultService;
    
    private static final String LOTTERY_URL = "https://results.govdoc.lk/lottery/dlb/jayoda";
    private static final int TIMEOUT = 15000;
    
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LotteryApplication.class, args);
        LotteryApplication app = context.getBean(LotteryApplication.class);
        app.runLotteryScraper();
        System.exit(0);
    }
    
    public void runLotteryScraper() {
        System.out.println("🎯 Sri Lanka Jayoda Lottery Scraper Starting...");
        System.out.println("================================================");
        System.out.println("🌐 Target Website: " + LOTTERY_URL);
        System.out.println("🏛️  Government Lottery Board - Development Lottery Board");
        System.out.println("💾 Database Storage: Enabled with Hibernate");
        System.out.println();
        
        try {
            System.out.println("📡 Connecting to Sri Lanka Lottery Results...");
            Document document = fetchWebPage(LOTTERY_URL);
            
            if (document != null) {
                System.out.println("📋 Page Title: " + document.title());
                System.out.println("================================================");
                
                extractAndStoreLotteryResults(document);
                
                System.out.println("\n📄 General Page Content Summary:");
                System.out.println("================================================");
                String pageText = document.text();
                System.out.println("Total characters extracted: " + pageText.length());
                
                if (pageText.length() > 300) {
                    System.out.println("Content preview: " + pageText.substring(0, 300) + "...");
                } else {
                    System.out.println("Content: " + pageText);
                }
                
                System.out.println("\n✅ Lottery scraping and database storage completed successfully!");
                
            } else {
                System.out.println("❌ Failed to fetch the lottery results page");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error occurred during lottery scraping:");
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Document fetchWebPage(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            connection.timeout(TIMEOUT);
            connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            Document document = connection.get();
            System.out.println("✅ Successfully connected to Sri Lanka Lottery Results");
            return document;
            
        } catch (Exception e) {
            System.out.println("❌ Failed to connect to: " + url);
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    private void extractAndStoreLotteryResults(Document document) {
        System.out.println("\n🎰 Jayoda Lottery Results:");
        System.out.println("================================================");
        
        try {
            String pageText = document.text();
            extractAndStoreJayodaResults(pageText);
            
            Elements lotteryElements = document.select("div, p, span, td");
            boolean foundResults = false;
            
            for (Element element : lotteryElements) {
                String text = element.text().trim();
                if (text.contains("Jayoda") && text.contains("-") && text.contains("Jayoda")) {
                    System.out.println("🎯 Found Result: " + text);
                    foundResults = true;
                }
            }
            
            if (!foundResults) {
                System.out.println("ℹ️  No structured lottery results found in HTML elements");
                System.out.println("🔍 This is common with government websites that use custom layouts");
            }
            
        } catch (Exception e) {
            System.out.println("⚠️  Error while parsing lottery results: " + e.getMessage());
        }
    }
    
    private void extractAndStoreJayodaResults(String pageText) {
        System.out.println("🔍 Searching for Jayoda lottery patterns...");
        
        String pattern = "Jayoda\\s+(\\d{4})\\s+-\\s+Jayoda\\s+(\\d{2}-\\d{2}-\\d{4})";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(pageText);
        
        int resultCount = 0;
        int storedCount = 0;
        
        while (matcher.find()) {
            String drawNumber = matcher.group(1);
            String drawDate = matcher.group(2);
            String fullMatch = matcher.group(0);
            
            System.out.println("🎯 Found Lottery Result:");
            System.out.println("   📊 Draw Number: " + drawNumber);
            System.out.println("   📅 Draw Date: " + drawDate);
            System.out.println("   📋 Full Result: " + fullMatch);
            
            resultCount++;
            
            try {
                LotteryResult result = new LotteryResult();
                result.setLotteryType("Jayoda");
                result.setDrawNumber(drawNumber);
                result.setDrawDate(drawDate);
                result.setFullResult(fullMatch);
                result.setScrapedAt(java.time.LocalDateTime.now());
                
                lotteryResultService.saveLotteryResult(result);
                System.out.println("   💾 Stored in database successfully!");
                storedCount++;
                
            } catch (Exception e) {
                System.out.println("   ❌ Failed to store in database: " + e.getMessage());
            }
            
            System.out.println();
        }
        
        if (resultCount == 0) {
            System.out.println("ℹ️  No Jayoda lottery results found in the expected format");
            System.out.println("🔍 The website may have changed its layout or content structure");
        } else {
            System.out.println("📊 Total lottery results found: " + resultCount);
            System.out.println("💾 Total results stored in database: " + storedCount);
        }
    }
}
