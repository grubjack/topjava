package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

/**
 * Created by user on 22.07.2016.
 */
public class LocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
        return TimeUtil.parseLocalTime(source);
    }
}
