package com.triple.pointservice.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity(name = "point")
public class Point extends BaseEntity {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "userId", columnDefinition = "varbinary(16)")
    private UUID userId;
    private int point;

    protected Point() {
    }

    public Point(UUID id, UUID userId, int point) {
        this.id = id;
        this.userId = userId;
        this.point = point;
    }

    public static Point create(UUID id, UUID userId) {
        return new Point(id, userId, 0);
    }

    public void add(int point) {
        this.point += point;
    }

    public void sub(int point) {
        this.point -= point;
    }
}
