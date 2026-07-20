package com.carinsurance.Car.Insurance.Application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class CarInsuranceDTO {

    private long id;

    @NotBlank(message = "Car number is required")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$",
            message = "Car number must be in format: XX00XX0000")
    private String carNumber;

    @NotBlank(message = "Insurance policy number is required")
    private String insurancePolicyNumber;

    @NotBlank(message = "Car owner name is required")
    private String carOwnerName;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Car owner ID is required")
    private String carOwnerId;

    private LocalDate policyStartDate;

    private LocalDate policyEndDate;

    @Positive(message = "Premium amount must be positive")
    private double premiumAmount;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getInsurancePolicyNumber() {
        return insurancePolicyNumber;
    }

    public void setInsurancePolicyNumber(String insurancePolicyNumber) {
        this.insurancePolicyNumber = insurancePolicyNumber;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(String carOwnerId) {
        this.carOwnerId = carOwnerId;
    }

    public LocalDate getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(LocalDate policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public LocalDate getPolicyEndDate() {
        return policyEndDate;
    }

    public void setPolicyEndDate(LocalDate policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }
}
