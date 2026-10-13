
package com.aurafit.aurafit_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.aurafit.aurafit_backend.entity.Workout;
import com.aurafit.aurafit_backend.entity.WorkoutCompletion;
import com.aurafit.aurafit_backend.repository.WorkoutCompletionRepository;
import com.aurafit.aurafit_backend.repository.WorkoutRepository;

@RestController
@RequestMapping("/workouts")

@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://aura-fit-360-frontend.vercel.app"
})
public class WorkoutController {

    private final WorkoutRepository workoutRepository;
    private final WorkoutCompletionRepository completionRepository;

    public WorkoutController(
            WorkoutRepository workoutRepository,
            WorkoutCompletionRepository completionRepository) {
        this.workoutRepository = workoutRepository;
        this.completionRepository = completionRepository;
    }

    // Get all workouts
    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    // Create a workout
    @PostMapping
    public Workout createWorkout(@RequestBody Workout workout) {
        return workoutRepository.save(workout);
    }

    // Get completed workouts for a user
    @GetMapping("/completed")
    public List<WorkoutCompletion> getCompletedWorkouts(
            @RequestParam String email) {

        return completionRepository.findByUserEmail(email);
    }

    // Record a workout completion
    @PostMapping("/completed")
    public WorkoutCompletion completeWorkout(
            @RequestParam Long workoutId,
            @RequestParam String email) {

        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email is required");
        }

        boolean workoutExists = workoutRepository.existsById(workoutId);

        if (!workoutExists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Workout not found");
        }

        WorkoutCompletion completion =
                new WorkoutCompletion(workoutId, email.trim());

        return completionRepository.save(completion);
    }
}
