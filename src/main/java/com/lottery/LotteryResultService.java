package com.lottery;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LotteryResultService {
    
    @Autowired
    private LotteryResultRepository lotteryResultRepository;
    
    public LotteryResult saveLotteryResult(LotteryResult lotteryResult) {
        return lotteryResultRepository.save(lotteryResult);
    }
    
    public Optional<LotteryResult> findById(Long id) {
        return lotteryResultRepository.findById(id);
    }
    
    public List<LotteryResult> findAll() {
        return lotteryResultRepository.findAll();
    }
    
    public List<LotteryResult> findByLotteryType(String lotteryType) {
        return lotteryResultRepository.findByLotteryType(lotteryType);
    }
    
    public List<LotteryResult> findByDrawNumber(String drawNumber) {
        return lotteryResultRepository.findByDrawNumber(drawNumber);
    }
    
    public List<LotteryResult> findByDrawDate(String drawDate) {
        return lotteryResultRepository.findByDrawDate(drawDate);
    }
    
    public boolean existsByLotteryTypeAndDrawNumberAndDrawDate(String lotteryType, String drawNumber, String drawDate) {
        return lotteryResultRepository.existsByLotteryTypeAndDrawNumberAndDrawDate(lotteryType, drawNumber, drawDate);
    }
    
    public long count() {
        return lotteryResultRepository.count();
    }
    
    public void deleteById(Long id) {
        lotteryResultRepository.deleteById(id);
    }
    
    public void deleteAll() {
        lotteryResultRepository.deleteAll();
    }
}
