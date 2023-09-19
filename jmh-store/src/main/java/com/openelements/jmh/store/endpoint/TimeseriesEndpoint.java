package com.openelements.jmh.store.endpoint;

import com.openelements.jmh.store.db.DataService;
import com.openelements.jmh.store.shared.Timeseries;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // Base path for all endpoints
@Api(tags = "Benchmark API") // Swagger API tags
public class TimeseriesEndpoint {

    private final DataService dataService;

    @Autowired
    public TimeseriesEndpoint(final DataService dataService) {
        this.dataService = Objects.requireNonNull(dataService);
    }

    @ApiOperation("Get timeseries")
    @CrossOrigin
    @GetMapping("/timeseries/{id}")
    List<Timeseries> getTimeseries(@PathVariable final Long id) {
        Objects.requireNonNull(id);
        return dataService.getAllTimeseriesForBenchmarks(id);
    }
}
