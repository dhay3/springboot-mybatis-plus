package com.chz.springbootmybatisplusdruid.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class Perm {
    private Integer pId;
    private String perm;
}
