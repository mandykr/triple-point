create table point_event (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    action varchar(255),
    place_id varbinary(16),
    point integer not null,
    review_id varbinary(16),
    type varchar(255),
    user_id varbinary(16),
    primary key (id)
);

create table point (
    id varbinary(16) not null,
    created_date datetime(6),
    modified_date datetime(6),
    point integer not null,
    user_id varbinary(16),
    primary key (id)
);

create index idx_point_event_review_id on point_event(review_id);
create index idx_point_event_place_id on point_event(place_id);
create index idx_point_user_id on point(user_id);