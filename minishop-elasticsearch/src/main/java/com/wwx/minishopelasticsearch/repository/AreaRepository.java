package com.wwx.minishopelasticsearch.repository;

import com.wwx.minishop.entity.Area;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AreaRepository extends ElasticsearchRepository<Area,Integer> {

    List<Area> queryAreaByAreaNameLike(String areaName);
}
