
package com.aurafit.aurafit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurafit.aurafit_backend.entity.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
