package com.github.xwine.end.test.service;

import com.github.xwine.end.mock.annotation.Mock;
import com.github.xwine.end.test.domain.Mantou;
import com.github.xwine.end.test.domain.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mock
public class HelloEnd {

    public List<Result<String>> fetch() {
        return null;
    }
}
