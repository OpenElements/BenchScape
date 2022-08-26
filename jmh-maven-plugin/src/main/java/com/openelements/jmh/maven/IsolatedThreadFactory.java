package com.openelements.jmh.maven;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;

public class IsolatedThreadFactory implements ThreadFactory {

    private final ThreadGroup threadGroup;

    private final ClassLoader contextClassloader;

    public IsolatedThreadFactory(final ThreadGroup threadGroup, final ClassLoader contextClassloader) {
        this.threadGroup = Objects.requireNonNull(threadGroup);
        this.contextClassloader = Objects.requireNonNull(contextClassloader);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup, r, "JmhUploaderThread");
        thread.setContextClassLoader(contextClassloader);
        return thread;
    }
}
