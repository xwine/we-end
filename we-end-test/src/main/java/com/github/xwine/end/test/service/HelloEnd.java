package com.github.xwine.end.test.service;

import org.springframework.stereotype.Service;

@Service
public class HelloEnd {

    public String fetch() {
        return "i can't be mock";
    }
}
