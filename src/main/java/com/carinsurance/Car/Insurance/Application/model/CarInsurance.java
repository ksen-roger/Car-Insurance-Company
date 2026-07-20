package com.carinsurance.Car.Insurance.Application.model;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "car_insurance")
public class CarInsurance {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "car_number", nullable = false, unique = true, length = 20)
    private String carNumber;

    @Column(name = "insurance_policy_number", nullable = false, unique = true, length = 50)
    private String insurancePolicyNumber;

    @Column(name = "car_owner_name", nullable = false, length = 100)
    private String carOwnerName;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "car_owner_id", nullable = false, unique = true, length = 50)
    private String carOwnerId;

    @Column(name = "policy_start_date")
    private LocalDate policyStartDate;

    @Column(name = "policy_end_date")
    private LocalDate policyEndDate;

    @Column(name = "premium_amount")
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