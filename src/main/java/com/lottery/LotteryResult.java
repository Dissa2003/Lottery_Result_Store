package com.lottery;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lottery_results_august")
public class LotteryResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "lottery_type", nullable = false, length = 100)
    private String lotteryType;
    
    @Column(name = "draw_number", nullable = false, length = 50)
    private String drawNumber;
    
    @Column(name = "draw_date", nullable = false, length = 20)
    private String drawDate;
    
    @Column(name = "full_result", nullable = false, length = 500)
    private String fullResult;
    
    @Column(name = "scraped_at", nullable = false)
    private LocalDateTime scrapedAt;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    public LotteryResult() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLotteryType() {
        return lotteryType;
    }
    
    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }
    
    public String getDrawNumber() {
        return drawNumber;
    }
    
    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }
    
    public String getDrawDate() {
        return drawDate;
    }
    
    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }
    
    public String getFullResult() {
        return fullResult;
    }
    
    public void setFullResult(String fullResult) {
        this.fullResult = fullResult;
    }
    
    public LocalDateTime getScrapedAt() {
        return scrapedAt;
    }
    
    public void setScrapedAt(LocalDateTime scrapedAt) {
        this.scrapedAt = scrapedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "LotteryResult{" +
                "id=" + id +
                ", lotteryType='" + lotteryType + '\'' +
                ", drawNumber='" + drawNumber + '\'' +
                ", drawDate='" + drawDate + '\'' +
                ", fullResult='" + fullResult + '\'' +
                ", scrapedAt=" + scrapedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
