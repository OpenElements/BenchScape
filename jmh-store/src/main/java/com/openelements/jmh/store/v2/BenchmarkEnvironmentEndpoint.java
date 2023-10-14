package com.openelements.jmh.store.v2;

import com.openelements.jmh.store.v2.data.Environment;
import com.openelements.jmh.store.v2.services.EnvironmentService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/environment")
public class BenchmarkEnvironmentEndpoint {

    private final EnvironmentService environmentService;


    @Autowired
    public BenchmarkEnvironmentEndpoint(EnvironmentService environmentService) {
        this.environmentService = Objects.requireNonNull(environmentService, "environmentService must not be null");
    }

    @GetMapping("/all")
    public List<Environment> getAll() {
        return environmentService.getAll();
    }

    @GetMapping("/find")
    public Environment find(@RequestParam final String id) {
        return environmentService.find(id);
    }

    @PostMapping
    public Environment save(@RequestParam final Environment environment) {
        return environmentService.save(environment);
    }

    @DeleteMapping
    public void delete(@RequestParam final String id) {
        environmentService.delete(id);
    }
}
