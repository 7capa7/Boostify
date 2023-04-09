package com.capa.boostify.boostOrder.entity;

import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.utils.Division;
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
@Table(name = "Boosting_Order")
public class BoostingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private boolean isFinished;
    @ManyToOne
    private User user;
    @ManyToOne
    private User booster;
    private String accountNickname;
    private String accountPassword;
    @Enumerated(EnumType.STRING)
    private Division actualDivision;
    @Enumerated(EnumType.STRING)
    private Division expectedDivision;
}
