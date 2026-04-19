package org.example.app.batch;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table("batch")
public class Batch {
    private Integer modelID;
    private String batchNumber;
    private BigDecimal unitCost;
    private LocalDate deliveryDate;
    private Integer quantity;
}