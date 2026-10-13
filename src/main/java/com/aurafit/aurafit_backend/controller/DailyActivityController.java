
package com.aurafit.aurafit_backend.controller;

import java.time.LocalDate;
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

import com.aurafit.aurafit_backend.entity.DailyActivity;
import com.aurafit.aurafit_backend.repository.DailyActivityRepository;

@RestController
@RequestMapping("/daily-activity")
@CrossOrigin(origins = "http://localhost:5173")
public class DailyActivityController {

    private final DailyActivityRepository activityRepository;

    public DailyActivityController(
            DailyActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // Get today's activity for a user
    @GetMapping
    public DailyActivity getTodayActivity(
            @RequestParam String email) {

        validateEmail(email);

        LocalDate today = LocalDate.now();

        return activityRepository
                .findByUserEmailAndActivityDate(email.trim(), today)
                .orElseGet(() -> {
                    DailyActivity activity = new DailyActivity();
                    activity.setUserEmail(email.trim());
                    activity.setActivityDate(today);
                    activity.setSteps(0);
                    activity.setWaterMl(0);
                    return activity;
                });
    }

    // Get all activity records for a user
    @GetMapping("/history")
    public List<DailyActivity> getActivityHistory(
            @RequestParam String email) {

        validateEmail(email);

        return activityRepository.findAll().stream()
                .filter(activity ->
                        activity.getUserEmail().equalsIgnoreCase(email.trim()))
                .sorted((a, b) ->
                        b.getActivityDate().compareTo(a.getActivityDate()))
                .toList();
    }

    // Save or update today's activity
    @PostMapping
    public DailyActivity saveActivity(
            @RequestParam String email,
            @RequestBody DailyActivity request) {

        validateEmail(email);

        if (request.getSteps() < 0 || request.getSteps() > 200000) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Steps must be between 0 and 200000");
        }

        if (request.getWaterMl() < 0 || request.getWaterMl() > 20000) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Water intake must be between 0 and 20000 ml");
        }

        String userEmail = email.trim();
        LocalDate today = LocalDate.now();

        DailyActivity activity = activityRepository
                .findByUserEmailAndActivityDate(userEmail, today)
                .orElseGet(DailyActivity::new);

        activity.setUserEmail(userEmail);
        activity.setActivityDate(today);
        activity.setSteps(request.getSteps());
        activity.setWaterMl(request.getWaterMl());

        return activityRepository.save(activity);
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email is required");
        }
    }
}
