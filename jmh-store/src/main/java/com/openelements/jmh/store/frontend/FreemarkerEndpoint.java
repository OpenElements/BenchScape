package com.openelements.jmh.store.frontend;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openelements.jmh.store.db.DataService;
import com.openelements.jmh.store.shared.BenchmarkDefinition;
import com.openelements.jmh.store.shared.Timeseries;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FreemarkerEndpoint {

  private final DataService dataService;
  
  @Autowired
  public FreemarkerEndpoint(final DataService dataService) {
    this.dataService = Objects.requireNonNull(dataService);
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String getIndex(final Model model) {
    Objects.requireNonNull(model);

    final List<BenchmarkDefinition> benchmarks = new ArrayList<>();
    dataService.getAllBenchmarks().forEach(benchmark -> {
      benchmarks.add(benchmark);
    });
    model.addAttribute("benchmarks", benchmarks);
    return "index";
  }

  @RequestMapping(value = "/graph", method = RequestMethod.GET)
  public String getGraph(final @RequestParam("id") Long id, final Model model) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(model);

    final BenchmarkDefinition benchmark = dataService.getBenchmarkById(id)
        .orElseThrow(
            () -> new IllegalArgumentException("No Benchmark with id '" + id + "' defined"));

    final List<Timeseries> values = dataService.getAllTimeseriesForBenchmarks(id);

    model.addAttribute("dataset", convertValues(values, v -> v.value(), "orange").toString());
    model.addAttribute("minDataset", convertValues(values, v -> v.min(), "#12121260").toString());
    model.addAttribute("maxDataset", convertValues(values, v -> v.max(), "#12121260").toString());

    model.addAttribute("benchmark", benchmark);
    return "graph";
  }

  private JsonObject convertValues(final List<Timeseries> values,
      final Function<Timeseries, Double> extractor, final String color) {
    Objects.requireNonNull(values);
    Objects.requireNonNull(extractor);
    Objects.requireNonNull(color);

    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    final List<JsonObject> jsonValues = values.stream()
        .map(value -> {
          final JsonObject jsonValue = new JsonObject();
          jsonValue.addProperty("x",
              dateTimeFormatter.format(value.timestamp().atZone(ZoneId.systemDefault())));
          jsonValue.addProperty("y", extractor.apply(value));
          return jsonValue;
        }).collect(Collectors.toList());

    final JsonObject main = new JsonObject();
    final JsonArray data = new JsonArray();
    jsonValues.forEach(v -> data.add(v));
    main.add("data", data);
    main.addProperty("fill", false);
    main.addProperty("tension", 0);
    main.addProperty("borderColor", color);
    return main;
  }
}
