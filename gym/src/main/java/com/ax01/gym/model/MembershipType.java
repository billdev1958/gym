package com.ax01.gym.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 *
 * @author bill
 */

@Entity
@Table(name = "cat_membership_type")
public class MembershipType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 155, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 255)
    private String description; 

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;
    
    @Column(name = "cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal cost;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
    
    public MembershipType() {
    }

    public MembershipType(Integer id, String name, String description, Integer durationDays, BigDecimal cost, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationDays = durationDays;
        this.cost = cost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }
    
    public BigDecimal getCost() {
        return cost;
    }
    
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}