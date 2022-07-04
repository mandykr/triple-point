package com.triple.pointservice.domain.event;

import com.triple.pointservice.domain.BaseEntity;
import com.triple.pointservice.domain.Review;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

import static com.triple.pointservice.domain.event.PointEventAction.DELETE;

@Getter
@Entity(name = "point_event")
public class PointEvent extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PointEventType type;

    @Enumerated(EnumType.STRING)
    private PointEventAction action;

    @Column(name = "reviewId", columnDefinition = "varbinary(16)")
    private UUID reviewId;

    @Column(name = "userId", columnDefinition = "varbinary(16)")
    private UUID userId;

    @Column(name = "placeId", columnDefinition = "varbinary(16)")
    private UUID placeId;

    private int point;

    protected PointEvent() {
    }

    public PointEvent(PointEventType type,
                      PointEventAction action,
                      UUID reviewId,
                      UUID userId,
                      UUID placeId,
                      int point) {
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.userId = userId;
        this.placeId = placeId;
        this.point = point;
    }

    public static PointEvent create(Review review, PointEventAction action, PointEventType type, int point) {
        return new PointEvent(type, action, review.getReviewId(), review.getUserId(), review.getPlaceId(), point);
    }

    public boolean isDeleteOrEmpty() {
        return action == DELETE ||
                EmptyPointEvent.isEmpty(this);
    }
}
