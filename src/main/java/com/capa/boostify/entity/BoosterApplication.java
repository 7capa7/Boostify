package com.capa.boostify.entity;

import com.capa.boostify.utils.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "booster_application")
public class BoosterApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne()
    User user;
    @OneToOne()
    BoosterDetails boosterDetails;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
}
