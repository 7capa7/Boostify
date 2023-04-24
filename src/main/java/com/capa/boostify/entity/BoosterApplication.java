package com.capa.boostify.entity;

import com.capa.boostify.utils.BoosterApplicationStatus;
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
@Table(name = "Booster_Application")
public class BoosterApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne()
    User user;
    @OneToOne()
    BoosterDetails boosterDetails;
    @Enumerated(EnumType.STRING)
    private BoosterApplicationStatus boosterApplicationStatus;
}
