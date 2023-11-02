package com.backend.projectapi.service.impl;

import com.backend.projectapi.repository.SlikeRepository;
import com.backend.projectapi.service.SlikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlikaServiceImpl implements SlikaService {

    @Autowired
    SlikeRepository slikeRepo;

}
