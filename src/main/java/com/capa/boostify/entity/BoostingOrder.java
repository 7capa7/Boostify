package com.capa.boostify.entity;

import com.capa.boostify.utils.Division;
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
@Table(name = "boosting_order")
public class BoostingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private boolean isFinished;
    @ManyToOne
    private User user;
    @ManyToOne
    private User booster;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private Division actualDivision;
    @Enumerated(EnumType.STRING)
    private Division expectedDivision;
}
