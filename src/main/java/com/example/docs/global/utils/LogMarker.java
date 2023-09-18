package com.example.docs.global.utils;


import lombok.Getter;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


@Getter
public enum LogMarker {
    SLACK("SLACK"),
    GENERAL("GENERAL"),
    SPECIAL("SPECIAL")
    ;

    private Marker marker;

    LogMarker(String name) {
        this.marker = MarkerFactory.getMarker(name);
    }
}
