module jmh.store.data.test {
    requires jmh.core;
    requires jdk.unsupported;
    requires com.google.gson;
    requires org.junit.jupiter.api;
    requires jmh.store.data;

    // exports com.openelements.jmh.store.data.test.jmh_generated to jmh.core;
    exports com.openelements.jmh.store.data.test to org.junit.platform.commons;
}