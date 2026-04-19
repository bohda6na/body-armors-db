package org.example.app.manufacturer;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("manufacturer")
public class Manufacturer {

    @Id
    @Column("manufacturerID")
    private Integer manufacturerID;

    @Column("brandName")
    private String brandName;

    @Column("countryOfOrigin")
    private String countryOfOrigin;
}