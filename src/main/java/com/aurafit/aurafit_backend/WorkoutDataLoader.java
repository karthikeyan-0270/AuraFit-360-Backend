
package com.aurafit.aurafit_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aurafit.aurafit_backend.entity.Workout;
import com.aurafit.aurafit_backend.repository.WorkoutRepository;

@Configuration
public class WorkoutDataLoader {

    @Bean
    CommandLineRunner loadWorkouts(WorkoutRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Workout(
                    "Cardio Blast", "Cardio", 30, "Beginner",
                    "🏃", "Improve stamina with a cardio session."
                ));

                repository.save(new Workout(
                    "Strength Training", "Strength", 40, "Intermediate",
                    "🏋️", "Build strength with a full-body workout."
                ));

                repository.save(new Workout(
                    "Morning Yoga", "Yoga", 20, "Beginner",
                    "🧘", "Improve flexibility and relax your body."
                ));

                repository.save(new Workout(
                    "HIIT Workout", "Cardio", 25, "Advanced",
                    "⚡", "A high-intensity interval workout."
                ));

                repository.save(new Workout(
                    "Core Training", "Strength", 15, "Beginner",
                    "💪", "Focus on your abdominal and core muscles."
                ));

                repository.save(new Workout(
                    "Relaxing Stretch", "Yoga", 15, "Beginner",
                    "🌿", "A gentle stretching and recovery session."
                ));

                System.out.println("Six AuraFit workouts inserted!");
            }
        };
    }
}
