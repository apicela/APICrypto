package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import com.apicela.apicrypto.services.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/monitoring")
@Tag(name = "Monitoring Controller")
public class MonitoringController {
    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }


    @Operation(summary = "Save a new monitoring record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Monitoring record created",
                    content = @Content(schema = @Schema(implementation = MonitoringDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<Object> saveMonitoring(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Monitoring data to save",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MonitoringDTO.class))
            )
            @Valid MonitoringDTO monitoringDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(monitoringService.save(monitoringDTO));
    }

    @Operation(summary = "Get a monitoring record by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monitoring record found",
                    content = @Content(schema = @Schema(implementation = MonitoringDTO.class))),
            @ApiResponse(responseCode = "404", description = "Monitoring not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMonitoring(@PathVariable(value = "id") long id) {
        Optional<MonitoringDTO> monitoringOptional = monitoringService.findById(id);
        if (!monitoringOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monitoring not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(monitoringOptional.get());
    }
}
