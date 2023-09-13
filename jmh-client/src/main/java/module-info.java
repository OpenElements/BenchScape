module com.openelements.jmh.client {
    requires transitive com.openelements.benchscape.common;
    requires transitive jmh.core;
    requires static com.github.spotbugs.annotations;
    requires com.google.gson;
    requires java.net.http;
    requires java.management;
    requires jdk.management;

    exports com.openelements.jmh.client;
    exports com.openelements.jmh.client.json;
    exports com.openelements.jmh.client.jmh;
}