package com.carinsurance.Car.Insurance.Application.repository;

import com.carinsurance.Car.Insurance.Application.model.CarInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarInsuranceRepository extends JpaRepository<CarInsurance, Long> {

    // Find by car number
    Optional<CarInsurance> findByCarNumber(String carNumber);

    // Find by insurance policy number
    Optional<CarInsurance> findByInsurancePolicyNumber(String insurancePolicyNumber);

    // Find by car owner ID
    Optional<CarInsurance> findByCarOwnerId(String carOwnerId);

    // Find all by company name
    List<CarInsurance> findByCompanyName(String companyName);

    // Find all by owner name
    List<CarInsurance> findByCarOwnerNameContainingIgnoreCase(String ownerName);

    // Check if car number exists
    boolean existsByCarNumber(String carNumber);

    // Check if policy number exists
    boolean existsByInsurancePolicyNumber(String insurancePolicyNumber);

    // Check if car owner ID exists
    boolean existsByCarOwnerId(String carOwnerId);

    // Find expired policies
    @Query("SELECT c FROM CarInsurance c WHERE c.policyEndDate < :currentDate")
    List<CarInsurance> findExpiredPolicies(@Param("currentDate") LocalDate currentDate);

    // Find active policies
    @Query("SELECT c FROM CarInsurance c WHERE c.policyStartDate <= :currentDate AND c.policyEndDate >= :currentDate")
    List<CarInsurance> findActivePolicies(@Param("currentDate") LocalDate currentDate);

    // Find policies expiring soon (within next 30 days)
    @Query("SELECT c FROM CarInsurance c WHERE c.policyEndDate BETWEEN :startDate AND :endDate")
    List<CarInsurance> findPoliciesExpiringSoon(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}
