package com.openelements.jmh.store.store.endpoints;

import com.openelements.jmh.store.store.data.MeasurementMetadata;
import com.openelements.jmh.store.store.services.MeasurementService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/measurement/metadata")
public class MeasurementMetadataEndpoint {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementMetadataEndpoint(@NonNull final MeasurementService measurementService) {
        this.measurementService = Objects.requireNonNull(measurementService, "measurementService must not be null");
    }

    @GetMapping("/find")
    public MeasurementMetadata find(@RequestParam final String measurementId) {
        Objects.requireNonNull(measurementId, "measurementId must not be null");
        return measurementService.getMetadataForMeasurement(measurementId);
    }

}
