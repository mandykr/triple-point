package com.triple.pointservice.infra;

import com.triple.pointservice.domain.Point;
import com.triple.pointservice.domain.repository.PointRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PointJpaRepository extends PointRepository, JpaRepository<Point, UUID> {
}
