package com.openelements.jmh.store.endpoint;

import com.openelements.jmh.store.db.DataService;
import com.openelements.jmh.store.shared.Timeseries;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeseriesEndpoint {

  private final DataService dataService;

  @Autowired
  public TimeseriesEndpoint(final DataService dataService) {
    this.dataService = Objects.requireNonNull(dataService);
  }

  @CrossOrigin
  @GetMapping("/timeseries")
  @ResponseBody
  List<Timeseries> getTimeseries(@RequestParam final Long benchmarkId) {
    Objects.requireNonNull(benchmarkId);
    return dataService.getAllTimeseriesForBenchmarks(benchmarkId);
  }
}
