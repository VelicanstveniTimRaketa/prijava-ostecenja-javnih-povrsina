package com.backend.projectapi.service.impl;

import com.backend.projectapi.repository.LokacijeRepository;
import com.backend.projectapi.service.LokacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LokacijaServiceImpl implements LokacijaService {

    @Autowired
    LokacijeRepository lokacijeRepo;
}
