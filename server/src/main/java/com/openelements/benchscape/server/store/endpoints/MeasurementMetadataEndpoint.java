package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import com.openelements.benchscape.server.store.services.MeasurementService;
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
@RequestMapping(V2 + "/measurement/metadata")
public class MeasurementMetadataEndpoint {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementMetadataEndpoint(@NonNull final MeasurementService measurementService) {
        this.measurementService = Objects.requireNonNull(measurementService, "measurementService must not be null");
    }

    @GetMapping
    public MeasurementMetadata getByMeasurementId(@RequestParam final String measurementId) {
        Objects.requireNonNull(measurementId, "measurementId must not be null");
        return measurementService.getMetadataForMeasurement(measurementId);
    }

}
