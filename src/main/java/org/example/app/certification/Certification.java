package org.example.app.certification;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("certification")
public class Certification {

    @Id
    @Column("certificationCode")
    private String certificationCode;

    @Column("standardName")
    private String standardName;
}