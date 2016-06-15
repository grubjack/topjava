package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");


        LocalDate date = LocalDate.parse(LocalDate.MAX + " 15:44", TimeUtil.DATE_TME_FORMATTER);
        System.out.println(date);
    }
}
