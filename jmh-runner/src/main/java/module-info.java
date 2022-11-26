module com.openelements.jmh.runner {
  requires com.openelements.jmh.common;
  requires jmh.core;
  requires java.net.http;
  requires com.google.gson;
  requires java.management;
  requires jdk.management;
  exports com.openelements.jmh.runner;
}