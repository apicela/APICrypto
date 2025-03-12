package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.models.dtos.MonitoringDTO;
import com.apicela.apicrypto.models.dtos.UpdateMonitoringDTO;
import com.apicela.apicrypto.models.responses.DefaultApiResponse;
import com.apicela.apicrypto.services.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/monitoring")
@Log4j2
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
    public Mono<ResponseEntity<Object>> saveMonitoring(@RequestBody @Valid MonitoringDTO monitoringDTO) {
        return monitoringService.save(monitoringDTO)
                .map( it -> {
                    DefaultApiResponse<MonitoringDTO> response = new DefaultApiResponse<>(
                            "Monitoring record created successfully",
                            it,
                            HttpStatus.CREATED.value()
                    );
                    log.info("{}", response);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }

    @Operation(summary = "Get a monitoring record by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monitoring record found",
                    content = @Content(schema = @Schema(implementation = MonitoringDTO.class))),
            @ApiResponse(responseCode = "404", description = "Monitoring not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Object>> getMonitoring(@PathVariable(value = "id") long id) {
        return monitoringService.findById(id).map(it -> {
            DefaultApiResponse<MonitoringDTO> response = new DefaultApiResponse<>(
                    "ok",
                    it,
                    HttpStatus.OK.value()
            );
            log.info("{}", response);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteMonitoring(@PathVariable(value = "id") long id) {
        return monitoringService.deleteById(id).then(Mono.fromCallable(() -> {
            DefaultApiResponse<Void> response = new DefaultApiResponse<>(
                    "ok",
                    HttpStatus.OK.value()
            );
            log.info("Monitoring with ID {} deleted successfully", id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }));
    }

    @Operation(summary = "Update a monitoring record by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monitoring record updated",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Monitoring record not updated",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> getMonitoring(@PathVariable(value = "id") long id,
                                                      @RequestBody @Valid UpdateMonitoringDTO updateMonitoringDTO) {
        return monitoringService.update(id, updateMonitoringDTO).map( it -> {
                    DefaultApiResponse<Void> response = new DefaultApiResponse<>(
                            "ok",
                            HttpStatus.OK.value()
                    );
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                });

    }
}
