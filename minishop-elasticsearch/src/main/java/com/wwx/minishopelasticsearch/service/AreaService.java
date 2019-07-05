package com.wwx.minishopelasticsearch.service;

import com.wwx.minishop.entity.Area;

import java.util.List;

public interface AreaService {

    List<Area> queryAreaWithName(String name);
}
