package com.wwx.minishopelasticsearch.service.impl;

import com.wwx.minishop.entity.Area;
import com.wwx.minishopelasticsearch.repository.AreaRepository;
import com.wwx.minishopelasticsearch.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    public AreaServiceImpl(AreaRepository areaRepository){
        this.areaRepository = areaRepository;
    }

    @Override
    public List<Area> queryAreaWithName(String name) {
        return areaRepository.queryAreaByAreaNameLike(name);
    }
}
