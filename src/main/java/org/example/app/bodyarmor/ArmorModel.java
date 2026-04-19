package org.example.app.bodyarmor;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Data
@Table("armormodel")
public class ArmorModel {

    @Id
    @Column("modelID")
    private Integer modelID;

    @Column("manufacturerID")
    private Integer manufacturerID;

    @Column("modelName")
    private String modelName;

    @Column("protectionLevel")
    private String protectionLevel;

    @Column("v50Velocity")
    private BigDecimal v50Velocity;

    @Column("backfaceDeformation")
    private BigDecimal backfaceDeformation;

    @Column("SKU")
    private String SKU;

    @Column("warrantyYears")
    private Integer warrantyYears;
}