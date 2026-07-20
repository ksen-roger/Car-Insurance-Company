package com.carinsurance.Car.Insurance.Application.service;

import com.carinsurance.Car.Insurance.Application.dto.CarInsuranceDTO;
import com.carinsurance.Car.Insurance.Application.exception.DuplicateResourceException;
import com.carinsurance.Car.Insurance.Application.exception.ResourceNotFoundException;
import com.carinsurance.Car.Insurance.Application.model.CarInsurance;
import com.carinsurance.Car.Insurance.Application.repository.CarInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarInsuranceService {

    @Autowired
    private CarInsuranceRepository repository;

    // Create new insurance
    public CarInsuranceDTO createInsurance(CarInsuranceDTO dto) {

        // Check for duplicates
        if (repository.existsByCarNumber(dto.getCarNumber())) {
            throw new DuplicateResourceException(
                    "Car number already exists: " + dto.getCarNumber());
        }

        if (repository.existsByInsurancePolicyNumber(dto.getInsurancePolicyNumber())) {
            throw new DuplicateResourceException(
                    "Insurance policy number already exists: " + dto.getInsurancePolicyNumber());
        }

        if (repository.existsByCarOwnerId(dto.getCarOwnerId())) {
            throw new DuplicateResourceException(
                    "Car owner ID already exists: " + dto.getCarOwnerId());
        }

        CarInsurance entity = convertToEntity(dto);
        CarInsurance saved = repository.save(entity);
        return convertToDTO(saved);
    }

    // Get all insurances
    public List<CarInsuranceDTO> getAllInsurances() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get insurance by ID
    public CarInsuranceDTO getInsuranceById(long id) {
        CarInsurance entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Insurance not found with id: " + id));
        return convertToDTO(entity);
    }

    // Get insurance by car number
    public CarInsuranceDTO getInsuranceByCarNumber(String carNumber) {
        CarInsurance entity = repository.findByCarNumber(carNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Insurance not found with car number: " + carNumber));
        return convertToDTO(entity);
    }

    // Get insurance by policy number
    public CarInsuranceDTO getInsuranceByPolicyNumber(String policyNumber) {
        CarInsurance entity = repository.findByInsurancePolicyNumber(policyNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Insurance not found with policy number: " + policyNumber));
        return convertToDTO(entity);
    }

    // Get insurance by car owner ID
    public CarInsuranceDTO getInsuranceByCarOwnerId(String carOwnerId) {
        CarInsurance entity = repository.findByCarOwnerId(carOwnerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Insurance not found with car owner ID: " + carOwnerId));
        return convertToDTO(entity);
    }

    // Get insurances by company name
    public List<CarInsuranceDTO> getInsurancesByCompany(String companyName) {
        return repository.findByCompanyName(companyName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Search by owner name
    public List<CarInsuranceDTO> searchByOwnerName(String ownerName) {
        return repository.findByCarOwnerNameContainingIgnoreCase(ownerName)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get expired policies
    public List<CarInsuranceDTO> getExpiredPolicies() {
        return repository.findExpiredPolicies(LocalDate.now())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get active policies
    public List<CarInsuranceDTO> getActivePolicies() {
        return repository.findActivePolicies(LocalDate.now())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get policies expiring soon (next 30 days)
    public List<CarInsuranceDTO> getPoliciesExpiringSoon() {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);

        return repository.findPoliciesExpiringSoon(today, thirtyDaysLater)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Update insurance
    public CarInsuranceDTO updateInsurance(long id, CarInsuranceDTO dto) {
        CarInsurance existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Insurance not found with id: " + id));

        // Check for duplicates (excluding current record)
        if (!existing.getCarNumber().equals(dto.getCarNumber()) &&
                repository.existsByCarNumber(dto.getCarNumber())) {
            throw new DuplicateResourceException(
                    "Car number already exists: " + dto.getCarNumber());
        }

        if (!existing.getInsurancePolicyNumber().equals(dto.getInsurancePolicyNumber()) &&
                repository.existsByInsurancePolicyNumber(dto.getInsurancePolicyNumber())) {
            throw new DuplicateResourceException(
                    "Insurance policy number already exists: " + dto.getInsurancePolicyNumber());
        }

        // Update fields
        existing.setCarNumber(dto.getCarNumber());
        existing.setInsurancePolicyNumber(dto.getInsurancePolicyNumber());
        existing.setCarOwnerName(dto.getCarOwnerName());
        existing.setCompanyName(dto.getCompanyName());
        existing.setCarOwnerId(dto.getCarOwnerId());
        existing.setPolicyStartDate(dto.getPolicyStartDate());
        existing.setPolicyEndDate(dto.getPolicyEndDate());
        existing.setPremiumAmount(dto.getPremiumAmount());

        CarInsurance updated = repository.save(existing);
        return convertToDTO(updated);
    }

    // Delete insurance
    public void deleteInsurance(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Insurance not found with id: " + id);
        }
        repository.deleteById(id);
    }

    // Convert Entity to DTO
    private CarInsuranceDTO convertToDTO(CarInsurance entity) {
        CarInsuranceDTO dto = new CarInsuranceDTO();
        dto.setId(entity.getId());
        dto.setCarNumber(entity.getCarNumber());
        dto.setInsurancePolicyNumber(entity.getInsurancePolicyNumber());
        dto.setCarOwnerName(entity.getCarOwnerName());
        dto.setCompanyName(entity.getCompanyName());
        dto.setCarOwnerId(entity.getCarOwnerId());
        dto.setPolicyStartDate(entity.getPolicyStartDate());
        dto.setPolicyEndDate(entity.getPolicyEndDate());
        dto.setPremiumAmount(entity.getPremiumAmount());
        return dto;
    }

    // Convert DTO to Entity
    private CarInsurance convertToEntity(CarInsuranceDTO dto) {
        CarInsurance entity = new CarInsurance();
        entity.setCarNumber(dto.getCarNumber());
        entity.setInsurancePolicyNumber(dto.getInsurancePolicyNumber());
        entity.setCarOwnerName(dto.getCarOwnerName());
        entity.setCompanyName(dto.getCompanyName());
        entity.setCarOwnerId(dto.getCarOwnerId());
        entity.setPolicyStartDate(dto.getPolicyStartDate());
        entity.setPolicyEndDate(dto.getPolicyEndDate());
        entity.setPremiumAmount(dto.getPremiumAmount());
        return entity;
    }
}
