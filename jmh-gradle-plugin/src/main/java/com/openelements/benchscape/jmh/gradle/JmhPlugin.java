package com.openelements.benchscape.jmh.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.jvm.tasks.Jar;

import static org.gradle.api.plugins.JavaPlugin.COMPILE_JAVA_TASK_NAME;
import static org.gradle.api.plugins.JavaPlugin.JAR_TASK_NAME;
import static org.gradle.api.plugins.JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME;
import static org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP;

public abstract class JmhPlugin implements Plugin<Project> {

    private static final String JMH_CLIENT = "com.open-elements.benchscape:jmh-client";

    @Override
    public void apply(Project project) {
        project.getPlugins().apply(JavaPlugin.class);

        Configuration runtimeClasspath = project.getConfigurations().getByName(RUNTIME_CLASSPATH_CONFIGURATION_NAME);
        Configuration jmhClientClasspath = project.getConfigurations().create("jmhClientClasspath");

        TaskProvider<JavaCompile> compileJava = project.getTasks().named(COMPILE_JAVA_TASK_NAME, JavaCompile.class);
        TaskProvider<Jar> jar = project.getTasks().named(JAR_TASK_NAME, Jar.class);

        project.getDependencies().add(jmhClientClasspath.getName(), JMH_CLIENT + ":" + getMyVersion());

        project.getTasks().register("runJmh", JmhTask.class, t -> {
            t.setDescription("Runs JMH test");
            t.setGroup(VERIFICATION_GROUP);

            t.getClasspath().from(jmhClientClasspath);
            t.getClasspath().from(jar);
            t.getClasspath().from(runtimeClasspath);

            t.getBenchmarkList().convention(
                    compileJava.flatMap(c -> c.getDestinationDirectory().file("META-INF/BenchmarkList")));

            t.getUpload().convention(true);
            t.getUrl().convention("https://backend.benchscape.cloud");
            t.getDestinationDir().convention(project.getLayout().getBuildDirectory().dir("jmh-results"));
        });
    }

    private String getMyVersion() {
        String pluginJarName = "/jmh-gradle-plugin-";
        String pluginJar = JmhPlugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return pluginJar.substring(
                pluginJar.lastIndexOf(pluginJarName) + pluginJarName.length(),
                pluginJar.lastIndexOf(".jar")
        );
    }
}
