package com.wwx.minishop.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(indexName = "area")
public class Area implements Serializable {

    @Id
    private Integer areaId;

    private String areaName;

    private String areaDesc;

    private Date createTime;

    private Date LastEditTime;

    private Integer priority;
}
