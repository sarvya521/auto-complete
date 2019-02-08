package com.ac.api;

import com.ac.svc.impl.AutoCompleteSvcFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController("/api/")
public class CommonApiController {

    @Autowired
    private AutoCompleteSvcFacade autoCompleteSvcFacade;

    @GetMapping("search/{type}")
    public List search(@PathVariable(value = "type")String type, @RequestParam(value = "key")String key) {
        return autoCompleteSvcFacade.search(type, key);
    }
}
