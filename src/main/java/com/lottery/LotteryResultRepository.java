package com.lottery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LotteryResultRepository extends JpaRepository<LotteryResult, Long> {
    
    List<LotteryResult> findByLotteryType(String lotteryType);
    
    List<LotteryResult> findByDrawNumber(String drawNumber);
    
    List<LotteryResult> findByDrawDate(String drawDate);
    
    boolean existsByLotteryTypeAndDrawNumberAndDrawDate(String lotteryType, String drawNumber, String drawDate);
}
