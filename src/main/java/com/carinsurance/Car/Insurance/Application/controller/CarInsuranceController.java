package com.carinsurance.Car.Insurance.Application.controller;

import com.carinsurance.Car.Insurance.Application.dto.CarInsuranceDTO;
import com.carinsurance.Car.Insurance.Application.service.CarInsuranceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@CrossOrigin(origins = "*")
public class CarInsuranceController {

    @Autowired
    private CarInsuranceService service;

    // Create new insurance
    @PostMapping
    public ResponseEntity<CarInsuranceDTO> createInsurance(
            @Valid @RequestBody CarInsuranceDTO dto) {
        CarInsuranceDTO created = service.createInsurance(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get all insurances
    @GetMapping
    public ResponseEntity<List<CarInsuranceDTO>> getAllInsurances() {
        List<CarInsuranceDTO> insurances = service.getAllInsurances();
        return ResponseEntity.ok(insurances);
    }

    // Get insurance by ID
    @GetMapping("/{id}")
    public ResponseEntity<CarInsuranceDTO> getInsuranceById(@PathVariable long id) {
        CarInsuranceDTO insurance = service.getInsuranceById(id);
        return ResponseEntity.ok(insurance);
    }

    // Get insurance by car number
    @GetMapping("/car/{carNumber}")
    public ResponseEntity<CarInsuranceDTO> getInsuranceByCarNumber(
            @PathVariable String carNumber) {
        CarInsuranceDTO insurance = service.getInsuranceByCarNumber(carNumber);
        return ResponseEntity.ok(insurance);
    }

    // Get insurance by policy number
    @GetMapping("/policy/{policyNumber}")
    public ResponseEntity<CarInsuranceDTO> getInsuranceByPolicyNumber(
            @PathVariable String policyNumber) {
        CarInsuranceDTO insurance = service.getInsuranceByPolicyNumber(policyNumber);
        return ResponseEntity.ok(insurance);
    }

    // Get insurance by car owner ID
    @GetMapping("/owner/{carOwnerId}")
    public ResponseEntity<CarInsuranceDTO> getInsuranceByCarOwnerId(
            @PathVariable String carOwnerId) {
        CarInsuranceDTO insurance = service.getInsuranceByCarOwnerId(carOwnerId);
        return ResponseEntity.ok(insurance);
    }

    // Get insurances by company name
    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<CarInsuranceDTO>> getInsurancesByCompany(
            @PathVariable String companyName) {
        List<CarInsuranceDTO> insurances = service.getInsurancesByCompany(companyName);
        return ResponseEntity.ok(insurances);
    }

    // Search by owner name
    @GetMapping("/search")
    public ResponseEntity<List<CarInsuranceDTO>> searchByOwnerName(
            @RequestParam String ownerName) {
        List<CarInsuranceDTO> insurances = service.searchByOwnerName(ownerName);
        return ResponseEntity.ok(insurances);
    }

    // Get expired policies
    @GetMapping("/expired")
    public ResponseEntity<List<CarInsuranceDTO>> getExpiredPolicies() {
        List<CarInsuranceDTO> insurances = service.getExpiredPolicies();
        return ResponseEntity.ok(insurances);
    }

    // Get active policies
    @GetMapping("/active")
    public ResponseEntity<List<CarInsuranceDTO>> getActivePolicies() {
        List<CarInsuranceDTO> insurances = service.getActivePolicies();
        return ResponseEntity.ok(insurances);
    }

    // Get policies expiring soon
    @GetMapping("/expiring-soon")
    public ResponseEntity<List<CarInsuranceDTO>> getPoliciesExpiringSoon() {
        List<CarInsuranceDTO> insurances = service.getPoliciesExpiringSoon();
        return ResponseEntity.ok(insurances);
    }

    // Update insurance
    @PutMapping("/{id}")
    public ResponseEntity<CarInsuranceDTO> updateInsurance(
            @PathVariable long id,
            @Valid @RequestBody CarInsuranceDTO dto) {
        CarInsuranceDTO updated = service.updateInsurance(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete insurance
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable long id) {
        service.deleteInsurance(id);
        return ResponseEntity.noContent().build();
    }
}
