package com.estf.edoctorat.services;

import com.estf.edoctorat.dto.Sujet2Dto;
import com.estf.edoctorat.mappers.SujetMapper;
import com.estf.edoctorat.models.SujetModel;
import com.estf.edoctorat.repositories.SujetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PoleSujetService {
    @Autowired
    private SujetRepository sujetRepository;

    public Page<SujetModel> getAllSujets(int limit, int offset) {
        return sujetRepository.findAll(PageRequest.of(offset / limit, limit));
    }
}

