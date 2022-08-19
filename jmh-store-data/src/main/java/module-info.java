module jmh.store.data {
    exports com.openelements.jmh.store.data;
    exports com.openelements.jmh.store.data.factory;
    exports com.openelements.jmh.store.data.runner;

    opens com.openelements.jmh.store.data to com.google.gson;

    requires jmh.core;
    requires com.google.gson;
    requires java.management;
    requires jdk.management;
}