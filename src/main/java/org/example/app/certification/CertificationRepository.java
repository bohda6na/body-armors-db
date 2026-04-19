package org.example.app.certification;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface CertificationRepository extends CrudRepository<Certification, String> {

    @Query("SELECT * FROM certification ORDER BY certificationCode")
    List<Certification> findAllOrdered();

    @Query("""
        SELECT * FROM armormodelcertification
        WHERE modelID = :modelID AND certificationCode = :code
        """)
    ArmorModelCertification findLink(@Param("modelID") Integer modelID,
                                     @Param("code") String code);

    @Modifying
    @Query("""
        INSERT INTO armormodelcertification (modelID, certificationCode, verificationDate, isValid)
        VALUES (:modelID, :code, :date, :valid)
        ON DUPLICATE KEY UPDATE verificationDate = VALUES(verificationDate), isValid = VALUES(isValid)
        """)
    void upsertCertification(@Param("modelID") Integer modelID,
                              @Param("code") String code,
                              @Param("date") LocalDate date,
                              @Param("valid") boolean valid);
}