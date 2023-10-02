module com.openelements.benchscape.jmh.client {
    requires transitive com.openelements.benchscape.jmh.model;
    requires transitive jmh.core;
    requires static com.github.spotbugs.annotations;
    requires com.google.gson;
    requires java.net.http;
    requires java.management;
    requires jdk.management;
    requires info.picocli;

    exports com.openelements.benchscape.jmh.client.json;
    exports com.openelements.benchscape.jmh.client.jmh;
    exports com.openelements.benchscape.jmh.client;

    opens com.openelements.benchscape.jmh.client to info.picocli;
}