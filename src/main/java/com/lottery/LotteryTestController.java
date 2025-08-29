package com.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RestController
@RequestMapping("/test")
public class LotteryTestController implements CommandLineRunner {
    
    @Autowired
    private LotteryResultService lotteryResultService;
    
    @GetMapping("/status")
    public String getStatus() {
        return "🎯 Lottery Scraper is running!\n" +
               "🗄️  Database: MySQL (13.234.242.165:3306/a1)\n" +
               "👤 User: iDevice\n" +
               "🔗 Status: Connected\n" +
               "📊 Table: lottery_results_august";
    }
    
    @GetMapping("/database")
    public String testDatabase() {
        try {
            long currentCount = lotteryResultService.count();
            
            LotteryResult testResult = new LotteryResult();
            testResult.setLotteryType("TEST-JAYODA");
            testResult.setDrawNumber("9999");
            testResult.setDrawDate("27-08-2025");
            testResult.setFullResult("Test lottery result for database verification - Jayoda 9999");
            testResult.setScrapedAt(java.time.LocalDateTime.now());
            
            LotteryResult saved = lotteryResultService.saveLotteryResult(testResult);
            
            long newCount = lotteryResultService.count();
            
            return "✅ Database Test Successful!\n" +
                   "📊 Records before: " + currentCount + "\n" +
                   "📊 Records after: " + newCount + "\n" +
                   "🆔 New record ID: " + saved.getId() + "\n" +
                   "💾 Table 'lottery_results_august' working correctly!";
                   
        } catch (Exception e) {
            return "❌ Database Test Failed!\n" +
                   "Error: " + e.getMessage() + "\n" +
                   "Check database connection settings.";
        }
    }
    
    @GetMapping("/records")
    public String viewRecords() {
        try {
            long count = lotteryResultService.count();
            if (count == 0) {
                return "📭 No records found in database.\n" +
                       "Run /test/database first to create a test record.";
            }
            
            StringBuilder result = new StringBuilder();
            result.append("📊 Total Records: ").append(count).append("\n\n");
            
            var records = lotteryResultService.findAll();
            for (LotteryResult record : records) {
                result.append("ID: ").append(record.getId())
                      .append(" | Type: ").append(record.getLotteryType())
                      .append(" | Draw: ").append(record.getDrawNumber())
                      .append(" | Date: ").append(record.getDrawDate())
                      .append(" | Result: ").append(record.getFullResult())
                      .append("\n");
            }
            
            return result.toString();
            
        } catch (Exception e) {
            return "❌ Error viewing records: " + e.getMessage();
        }
    }
    
    @DeleteMapping("/clear")
    public String clearRecords() {
        try {
            long count = lotteryResultService.count();
            lotteryResultService.deleteAll();
            return "🧹 Cleared " + count + " records from database.";
        } catch (Exception e) {
            return "❌ Error clearing records: " + e.getMessage();
        }
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Starting Lottery Scraper with Database...");
        System.out.println("🗄️  Connecting to MySQL: 13.234.242.165:3306/a1");
        System.out.println("👤 Username: iDevice");
        System.out.println("🔑 Testing database connection...");
        
        try {
            long count = lotteryResultService.count();
            System.out.println("✅ Database connection successful!");
            System.out.println("📊 Current records in database: " + count);
            System.out.println("📋 Table: lottery_results_august");
        } catch (Exception e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            System.out.println("⚠️  Check your database settings and network connection");
        }
    }
}
