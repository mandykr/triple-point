package com.triple.pointservice.infra;

import com.triple.pointservice.domain.event.PointEvent;
import com.triple.pointservice.domain.repository.PointEventRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointEventJpaRepository extends PointEventRepository, JpaRepository<PointEvent, Long> {
}
