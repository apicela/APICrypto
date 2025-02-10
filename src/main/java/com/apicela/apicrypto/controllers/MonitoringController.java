package com.apicela.apicrypto.controllers;

import com.apicela.apicrypto.dtos.MonitoringDTO;
import com.apicela.apicrypto.services.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {
    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public ResponseEntity<Object> saveEquipment(@RequestBody @Valid MonitoringDTO monitoringDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(monitoringService.save(monitoringDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipment(@PathVariable(value = "id") long id) {
        Optional<MonitoringDTO> monitoringOptional = monitoringService.findById(id);
        if (!monitoringOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Monitoring not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(monitoringOptional.get());
    }
}
