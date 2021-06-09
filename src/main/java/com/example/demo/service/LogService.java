package com.example.demo.service;

import com.example.demo.model.entity.Log;
import com.example.demo.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Log save(Log log){
        return logRepository.save(log);
    }
}
