package com.openelements.jmh.store.frontend;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.openelements.jmh.store.db.DataService;
import com.openelements.jmh.store.frontend.model.TimeseriesTableModel;
import com.openelements.jmh.store.shared.BenchmarkDefinition;
import com.openelements.jmh.store.shared.Timeseries;
import com.openelements.jmh.store.util.Formatter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
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

  @RequestMapping(value = "timeseries/graph", method = RequestMethod.GET)
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

    return "timeseries/graph";
  }

  @RequestMapping(value = "timeseries/table", method = RequestMethod.GET)
  public String getDataPoints(final @RequestParam("id") Long id, final Model model,
      final HttpServletRequest request) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(model);
    Objects.requireNonNull(request);

    final BenchmarkDefinition benchmark = dataService.getBenchmarkById(id)
        .orElseThrow(
            () -> new IllegalArgumentException("No Benchmark with id '" + id + "' defined"));

    final List<TimeseriesTableModel> timeseries = dataService.getAllTimeseriesForBenchmarks(
            benchmark.id())
        .stream().map(dataPoint -> {
          final String dateAndTime = Formatter.toShortReadableForm(request, dataPoint.timestamp());
          final String value = Formatter.format2Dec(request, dataPoint.value());
          final String error = Formatter.format2Dec(request, dataPoint.error());
          final String min = Formatter.format2Dec(request, dataPoint.min());
          final String max = Formatter.format2Dec(request, dataPoint.max());
          final String processorCount = Optional.ofNullable(dataPoint.availableProcessors())
              .map(v -> Integer.toString(v)).orElse("UNKNOWN");
          final String memorySize = Optional.ofNullable(dataPoint.memory())
              .map(v -> Formatter.humanReadableBytes(v)).orElse("UNKNOWN");
          final String jvmVersion = Optional.ofNullable(dataPoint.jvmVersion()).orElse("UNKNOWN");
          return new TimeseriesTableModel(dateAndTime, value, error, min, max, processorCount,
              memorySize,
              jvmVersion);
        }).collect(Collectors.toList());

    timeseries.stream().forEach(dataPoint -> {
      System.out.println();
    });

    model.addAttribute("benchmarkName", benchmark.name());
    model.addAttribute("unit", benchmark.unit());
    model.addAttribute("dataPoints", timeseries);

    return "timeseries/table";
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
