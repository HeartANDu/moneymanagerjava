package com.example.moneymanager.models;

import com.example.moneymanager.listeners.entity.TimestampedEntityListener;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@EntityListeners(TimestampedEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;
    @JsonProperty("created_at")
    protected Instant cratedAt;
    @JsonProperty("updated_at")
    protected Instant updatedAt;
}
