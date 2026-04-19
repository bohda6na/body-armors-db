package org.example.app.batch;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BatchRepository extends CrudRepository<Batch, Integer> {

    @Query("SELECT * FROM batch ORDER BY modelID, batchNumber")
    List<Batch> findAllOrdered();

    @Modifying
    @Query("""
        INSERT INTO batch (modelID, batchNumber, unitCost, deliveryDate, quantity)
        VALUES (:modelID, :batchNumber, :unitCost, :deliveryDate, :quantity)
        ON DUPLICATE KEY UPDATE unitCost = VALUES(unitCost)
        """)
    void insertBatch(@Param("modelID") Integer modelID,
                     @Param("batchNumber") String batchNumber,
                     @Param("unitCost") BigDecimal unitCost,
                     @Param("deliveryDate") LocalDate deliveryDate,
                     @Param("quantity") Integer quantity);
}