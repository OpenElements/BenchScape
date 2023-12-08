package com.openelements.benchscape.server.store.export;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.services.EnvironmentService;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(V2 + "/export")
public class ExportEndpoint {

    private final EnvironmentService environmentService;

    @Autowired
    public ExportEndpoint(final EnvironmentService environmentService) {
        this.environmentService = Objects.requireNonNull(environmentService, "environmentService must not be null");
    }

    @GetMapping(value = "/environments/csv", produces = "text/csv")
    public @ResponseBody byte[] getCsvExportOfEnvironments() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final List<Environment> all = environmentService.getAll();
            OutputStreamWriter osw = new OutputStreamWriter(baos);
            CsvExport.exportEnvironments(osw, all);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error exporting environments", e);
        }
    }
}
