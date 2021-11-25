package com.example.moneymanager.listeners.entity;

import com.example.moneymanager.models.TimestampedEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class TimestampedEntityListener {
    @PreUpdate
    @PrePersist
    public void setTimestamps(TimestampedEntity entity) {
        Instant now = Instant.now();
        entity.setUpdatedAt(now);

        if (entity.getId() == null) {
            entity.setCreatedAt(now);
        }
    }
}
