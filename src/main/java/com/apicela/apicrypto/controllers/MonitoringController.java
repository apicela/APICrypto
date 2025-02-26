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
        log.info("{}", monitoringDTO);
        System.out.println(monitoringDTO);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(monitoringService.save(monitoringDTO)));
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
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(monitoringService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteMonitoring(@PathVariable(value = "id") long id) {
        monitoringService.deleteById(id);
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Monitoring " + id + " deleted with successful!"));
    }
}
