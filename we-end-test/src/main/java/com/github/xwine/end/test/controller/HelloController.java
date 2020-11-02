package com.github.xwine.end.test.controller;

import com.github.xwine.end.mock.ObjectMock;
import com.github.xwine.end.test.domain.Mantou;
import com.github.xwine.end.test.service.HelloEnd;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    HelloEnd helloEnd;

    @RequestMapping("fetch")
    public Object fetch() {
        return helloEnd.fetch();
    }


    @RequestMapping("hl")
    public Object heNan() {
        return ObjectMock.getObject(Mantou.class);
    }
}
