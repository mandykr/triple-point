package com.triple.pointservice.ui;

import com.triple.pointservice.application.PointService;
import com.triple.pointservice.application.dto.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/points")
public class PointRestController {
    private final PointService pointService;

    @GetMapping
    public ResponseEntity<PointResponse> findPoint(@RequestParam UUID userId) {
        PointResponse pointResponse = pointService.findPoint(userId);
        return ResponseEntity.ok(pointResponse);
    }
}
