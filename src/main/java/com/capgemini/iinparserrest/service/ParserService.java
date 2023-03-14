package com.capgemini.iinparserrest.service;

import com.capgemini.iinparserrest.domain.exception.PanFormatException;
import com.capgemini.iinparserrest.util.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.ConfigurationException;
import java.io.IOException;

@Slf4j
@Service
public class ParserService {


    public String parse(String pan) {
        try {
            FormatUtil formatter = new FormatUtil();
            String formattedPan = formatter.format(pan);
            log.info(formattedPan);
            return formattedPan;
        } catch (PanFormatException | IOException | ConfigurationException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
