package com.openelements.jmh.maven;

import org.apache.maven.plugin.logging.Log;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a ThreadGroup to isolate execution and collect exceptions.
 */
class IsolatedThreadGroup
        extends ThreadGroup {

    private final Log log;

    private Throwable uncaughtException;

    private Lock exceptionLock = new ReentrantLock();

    public IsolatedThreadGroup(final Log log, final String name) {
        super(name);
        this.log = Objects.requireNonNull(log);
    }

    public void uncaughtException(final Thread thread, final Throwable throwable) {
        if (throwable instanceof ThreadDeath) {
            return; // harmless
        }
        exceptionLock.lock();
        try {
            if (uncaughtException == null) { // only remember the first one
                uncaughtException = throwable; // will be reported eventually
            }
        } finally {
            exceptionLock.unlock();
        }
        log.warn(throwable);
    }

    public Throwable getUncaughtException() {
        exceptionLock.lock();
        try {
            return uncaughtException;
        } finally {
            exceptionLock.unlock();
        }
    }
}