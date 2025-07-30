package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Service.DashboardService;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping("{userId}")
    public ResponseEntity<?> getStatus(@PathVariable String userId){
        DashboardStatsDto stats = dashboardService.getUserStats(userId);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
