package com.capgemini.iinparserrest.util;


import com.capgemini.iinparserrest.domain.exception.PanFormatException;
import com.capgemini.iinparserrest.model.IinRange;
import lombok.extern.slf4j.Slf4j;

import javax.naming.ConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.capgemini.iinparserrest.domain.constants.AppConstants.CONFIG_FILE;
import static com.capgemini.iinparserrest.domain.constants.AppConstants.END_RANGE_COLUMN_INDEX;
import static com.capgemini.iinparserrest.domain.constants.AppConstants.ISSUER_NAME_COLUMN_INDEX;
import static com.capgemini.iinparserrest.domain.constants.AppConstants.LENGTH_COLUMN_INDEX;
import static com.capgemini.iinparserrest.domain.constants.AppConstants.PREFIX_COLUMN_INDEX;
import static com.capgemini.iinparserrest.domain.constants.AppConstants.START_RANGE_COLUMN_INDEX;
import static com.capgemini.iinparserrest.domain.constants.AppConstants.TYPE_COLUMN_INDEX;

@Slf4j
public class FormatUtil {

    private final List<IinRange> iinRanges;
    private final IinUtil iinUtil;

    public FormatUtil() throws IOException, ConfigurationException {
        this.iinRanges = readConfigFile();
        iinRanges.sort(Collections.reverseOrder());
        iinUtil = new IinUtil();
    }

    public String format(String pan) throws PanFormatException {
        Optional<IinRange> matchingRange = iinRanges.stream()
                .filter(range -> iinUtil.matches(pan, range))
                .findFirst();
        return matchingRange.map(range -> iinUtil.formatPan(pan, range))
                .orElseThrow(() -> new PanFormatException("Unsupported PAN format: " + pan));
    }

    private static List<IinRange> readConfigFile() throws IOException, ConfigurationException {
        InputStream inputStream = FormatUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        if (inputStream == null) {
            throw new ConfigurationException("Configuration file not found");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(line -> line.split(","))
                    .filter(parts -> !parts[ISSUER_NAME_COLUMN_INDEX].contains("Issuer Name"))
                    .map(parts -> new IinRange(
                            parts[ISSUER_NAME_COLUMN_INDEX],
                            Integer.parseInt(parts[START_RANGE_COLUMN_INDEX]),
                            Integer.parseInt(parts[END_RANGE_COLUMN_INDEX]),
                            Integer.parseInt(parts[LENGTH_COLUMN_INDEX]),
                            Integer.parseInt(parts[PREFIX_COLUMN_INDEX]),
                            parts[TYPE_COLUMN_INDEX]
                    ))
                    .collect(Collectors.toList());
        }
    }
}