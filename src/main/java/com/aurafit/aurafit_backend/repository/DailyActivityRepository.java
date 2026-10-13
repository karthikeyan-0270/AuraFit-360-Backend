
package com.aurafit.aurafit_backend.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurafit.aurafit_backend.entity.DailyActivity;

public interface DailyActivityRepository
        extends JpaRepository<DailyActivity, Long> {

    Optional<DailyActivity> findByUserEmailAndActivityDate(
        String userEmail,
        LocalDate activityDate
    );
}
