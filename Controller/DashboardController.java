package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Service.DashboardService;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardStatsDto> getStats(@AuthenticationPrincipal User user) {
        DashboardStatsDto stats = dashboardService.getDashboardStats(user.getId());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DashboardStatsDto> getUserStats(@PathVariable String userId) {
        DashboardStatsDto stats = dashboardService.getDashboardStats(userId);
        return ResponseEntity.ok(stats);
    }
}