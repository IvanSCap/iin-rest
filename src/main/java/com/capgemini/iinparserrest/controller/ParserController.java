package com.capgemini.iinparserrest.controller;

import com.capgemini.iinparserrest.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParserController {

    private final ParserService parserService;

    @GetMapping("/format")
    public String formatPan(@RequestParam String pan){
        return parserService.parse(pan);
    }

}
