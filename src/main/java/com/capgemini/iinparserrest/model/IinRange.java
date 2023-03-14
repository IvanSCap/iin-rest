package com.capgemini.iinparserrest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class IinRange implements Comparable<IinRange> {
    private final String name;
    private final Integer panLength;
    private final Integer prefixLength;
    private final Integer prefixStart;
    private final Integer prefixEnd;
    private final String formatString;

    @Override
    public int compareTo(IinRange o) {
        return prefixLength.compareTo(o.getPrefixLength());
    }
}