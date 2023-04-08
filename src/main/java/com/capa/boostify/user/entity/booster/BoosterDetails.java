package com.capa.boostify.user.entity.booster;

import com.capa.boostify.user.utils.Division;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Booster_Details")
public class BoosterDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private Division highestDivision;
    @Enumerated(EnumType.STRING)
    private Division actualDivision;
    private Integer inGameHours;
    private Year firstSeasonYear;
}
