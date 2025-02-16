package com.estf.edoctorat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estf.edoctorat.models.ConfigModel;
import com.estf.edoctorat.repositories.ConfigRepository;

@Service
public class ConfigService {
    @Autowired
    private ConfigRepository configRepository;

    public ConfigModel getBaseConfig() {
        return configRepository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("No configuration found"));
    }

    public ConfigModel updateConfig(ConfigModel config) {
        return configRepository.save(config);
    }
}
