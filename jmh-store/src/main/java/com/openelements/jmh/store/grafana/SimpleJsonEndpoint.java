package com.openelements.jmh.store.grafana;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openelements.jmh.store.db.DataService;
import com.openelements.jmh.store.shared.Timeseries;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("grafana")
public class SimpleJsonEndpoint {

  //See https://grafana.com/grafana/plugins/grafana-simple-json-datasource/

  private final DataService dataService;
  
  @Autowired
  public SimpleJsonEndpoint(final DataService dataService) {
    this.dataService = Objects.requireNonNull(dataService);
  }

  @CrossOrigin
  @GetMapping("/")
  String getStatus() {
    return "OK";
  }

  @CrossOrigin
  @PostMapping("/search")
  String getSearch(@RequestBody final String jsonString) {
    final JsonArray result = new JsonArray();
    dataService.getAllBenchmarks().stream()
        .map(benchmark -> benchmark.name())
        .forEach(name -> result.add(name));
    return result.toString();
  }

  @CrossOrigin
  @PostMapping("/query")
  String getQuery(@RequestBody final String jsonString) {
    System.out.println("query: " + jsonString);
    final JsonObject jsonRoot = JsonParser.parseString(jsonString).getAsJsonObject();
    final JsonObject range = jsonRoot.getAsJsonObject("range");
    final String rangeFrom = range.get("from").getAsString();
    final String rangeTo = range.get("to").getAsString();
    final long intervalInMs = jsonRoot.get("intervalMs").getAsLong();
    final JsonArray targetsArray = jsonRoot.getAsJsonArray("targets");
    final List<String> targets = new ArrayList<>();
    targetsArray.forEach(jsonElement -> {
      final String target = jsonElement.getAsJsonObject()
          .getAsJsonPrimitive("target").getAsString();
      targets.add(target);
    });

    final JsonArray resultArray = new JsonArray();

    targets.forEach(target -> {
      final List<Timeseries> timeseries = dataService.getBenchmarkByName(target)
          .map(benchmark -> benchmark.id())
          .map(benchmarkId -> dataService.getAllTimeseriesForBenchmarks(benchmarkId))
          .orElseThrow(() -> new IllegalArgumentException("Benchmark not found"));

      final JsonObject valueResultObject = new JsonObject();
      valueResultObject.addProperty("target", target);
      final JsonArray valueDataPoints = new JsonArray();
      valueResultObject.add("datapoints", valueDataPoints);
      resultArray.add(valueResultObject);

      final JsonObject minResultObject = new JsonObject();
      minResultObject.addProperty("target", target + "-min");
      final JsonArray minDataPoints = new JsonArray();
      minResultObject.add("datapoints", minDataPoints);
      resultArray.add(minResultObject);

      final JsonObject maxResultObject = new JsonObject();
      maxResultObject.addProperty("target", target + "-max");
      final JsonArray maxDataPoints = new JsonArray();
      maxResultObject.add("datapoints", maxDataPoints);
      resultArray.add(maxResultObject);

      timeseries.forEach(point -> {
        final JsonArray valueJsonPoint = new JsonArray();
        valueJsonPoint.add(point.value());
        valueJsonPoint.add(point.timestamp().toEpochMilli());
        valueDataPoints.add(valueJsonPoint);

        final JsonArray minJsonPoint = new JsonArray();
        minJsonPoint.add(point.min());
        minJsonPoint.add(point.timestamp().toEpochMilli());
        minDataPoints.add(minJsonPoint);

        final JsonArray maxJsonPoint = new JsonArray();
        maxJsonPoint.add(point.max());
        maxJsonPoint.add(point.timestamp().toEpochMilli());
        maxDataPoints.add(maxJsonPoint);
      });

    });
    return resultArray.toString();
  }

  @CrossOrigin
  @PostMapping("/annotations")
  String getAnnotations(@RequestBody final String jsonString) {
    System.out.println("annotations: " + jsonString);
    final JsonObject jsonRoot = JsonParser.parseString(jsonString).getAsJsonObject();
    final JsonObject annotation = jsonRoot.getAsJsonObject("annotation");

    final JsonObject resultObject = new JsonObject();
    resultObject.add("annotation", annotation);
    resultObject.addProperty("time", System.currentTimeMillis());
    resultObject.addProperty("title", "UNKNOWN");

    final JsonArray resultArray = new JsonArray();
    resultArray.add(resultObject);

    return resultArray.toString();
  }

  @CrossOrigin
  @PostMapping("/tag-keys")
  String getTagKeys() {
    final JsonObject type = new JsonObject();
    type.addProperty("type", "string");
    type.addProperty("text", "Type");
    final JsonArray result = new JsonArray();
    result.add(type);
    return result.toString();
  }

  @CrossOrigin
  @PostMapping("/tag-values")
  String getTagValues(@RequestBody final String jsonString) {
    System.out.println("tag-values: " + jsonString);
    final JsonObject jsonRoot = JsonParser.parseString(jsonString).getAsJsonObject();
    final String tag = jsonRoot.get("key").getAsString();

    final JsonArray result = new JsonArray();
    if (Objects.equals("Type", tag)) {
      final JsonObject value = new JsonObject();
      value.addProperty("text", "only value");
      result.add(value);

      final JsonObject minAndMax = new JsonObject();
      minAndMax.addProperty("text", "include min and max");
      result.add(minAndMax);

      final JsonObject error = new JsonObject();
      error.addProperty("text", "error");
      result.add(error);

    }
    return result.toString();
  }
}
