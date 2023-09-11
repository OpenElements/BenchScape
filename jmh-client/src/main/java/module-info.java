module com.openelements.jmh.client {
  requires com.openelements.jmh.common;
  requires jmh.core;
  requires java.net.http;
  requires com.google.gson;
  requires java.management;
  requires jdk.management;
  requires static com.github.spotbugs.annotations;

  exports com.openelements.jmh.client;
  exports com.openelements.jmh.client.json;
  exports com.openelements.jmh.client.factory;
}