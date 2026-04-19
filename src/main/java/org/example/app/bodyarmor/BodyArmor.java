package org.example.app.bodyarmor;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table("bodyarmor")
public class BodyArmor {

    @Id
    @Column("productID")
    private Integer productID;

    @Column("modelID")
    private Integer modelID;

    @Column("serialNumber")
    private String serialNumber;

    @Column("productionDate")
    private LocalDate productionDate;

    @Column("conditionStatus")
    private String conditionStatus;

    @Column("weight")
    private BigDecimal weight;

    @Column("size")
    private String size;

    @Column("inspectionDate")
    private LocalDate inspectionDate;
}