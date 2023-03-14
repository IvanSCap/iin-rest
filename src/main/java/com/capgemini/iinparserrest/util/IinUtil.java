package com.capgemini.iinparserrest.util;

import com.capgemini.iinparserrest.model.IinRange;
import lombok.extern.slf4j.Slf4j;

import static com.capgemini.iinparserrest.domain.constants.AppConstants.NO_PATTERN;

@Slf4j
public class IinUtil {

    public boolean matches(String pan, IinRange range) {
        if (pan.length() != range.getPanLength()) {
            return false;
        }
        String prefix = pan.substring(0, range.getPrefixLength());
        return prefix.length() == range.getPrefixLength() && isPrefixInRange(prefix, range);
    }

    private boolean isPrefixInRange(String prefix, IinRange range) {
        int prefixValue = Integer.parseInt(prefix);
        boolean isInRange = prefixValue >= range.getPrefixStart() && prefixValue <= range.getPrefixEnd();
        if(isInRange){
            log.debug("Matches with {}", range.getName());
        }
        return isInRange;
    }

    public String formatPan(String pan, IinRange range) {
        if(range.getFormatString().equals(NO_PATTERN)){
            return pan;
        }
        String[] groups = range.getFormatString().split(" ");
        int symbolIndex = 0;
        for (int i = 0; i < groups.length; i++) {
            groups[i] = pan.substring(symbolIndex, symbolIndex + groups[i].length());
            symbolIndex += groups[i].length();
        }
        return String.join(" ", groups);
    }
}