package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by user on 29.06.2016.
 */
public class TimerRule implements TestRule {
    private static final Logger LOG = LoggerFactory.getLogger(TimerRule.class);

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.nanoTime();
                statement.evaluate();
                long endTime = System.nanoTime();
                LOG.info("That took " + ((endTime - startTime) / 1000000) + " milliseconds");
            }
        };
    }
}
