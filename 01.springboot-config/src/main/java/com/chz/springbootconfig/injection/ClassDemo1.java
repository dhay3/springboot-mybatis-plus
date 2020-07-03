package com.chz.springbootconfig.injection;

import org.springframework.stereotype.Component;

@Component
public class ClassDemo1 {
    private String name = "ClassDemo1";

    public ClassDemo1() {
        System.out.println(name);
    }
}
