
package com.aurafit.aurafit_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurafit.aurafit_backend.entity.WorkoutCompletion;

public interface WorkoutCompletionRepository
        extends JpaRepository<WorkoutCompletion, Long> {

    List<WorkoutCompletion> findByUserEmail(String userEmail);
}

