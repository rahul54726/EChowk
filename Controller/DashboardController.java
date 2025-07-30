package com.EChowk.EChowk.Controller;

import com.EChowk.EChowk.Service.DashboardService;
import com.EChowk.EChowk.dto.DashboardStatsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{userId}") // ✅ Add missing `/`
    public ResponseEntity<?> getStatus(@PathVariable String userId){
        DashboardStatsDto stats = dashboardService.getUserStats(userId);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
