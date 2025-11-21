package com.ax01.gym.model;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author bill
 */

@Entity
@Table(name = "client_membership")
public class ClientMembership {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_membership_type", nullable = false)
    private MembershipType membershipType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; 

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; 

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public ClientMembership() {
    }

    public ClientMembership(UUID id, User user, MembershipType membershipType, LocalDate startDate, LocalDate endDate, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.user = user;
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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