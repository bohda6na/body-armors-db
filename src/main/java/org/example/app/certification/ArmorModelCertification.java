package org.example.app.certification;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Data
@Table("armormodelcertification")
public class ArmorModelCertification {
    private Integer modelID;
    private String certificationCode;
    private LocalDate verificationDate;
    private Boolean isValid;
}