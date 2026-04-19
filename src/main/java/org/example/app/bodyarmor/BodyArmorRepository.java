package org.example.app.bodyarmor;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface BodyArmorRepository extends CrudRepository<BodyArmor, Integer> {

    @Query("SELECT * FROM bodyarmor ORDER BY productID")
    List<BodyArmor> findAllOrdered();

    @Query("SELECT * FROM bodyarmor WHERE modelID = :modelID")
    List<BodyArmor> findByModelID(@Param("modelID") Integer modelID);

    @Modifying
    @Query("UPDATE bodyarmor SET conditionStatus = :status, inspectionDate = :date WHERE productID = :id")
    void updateCondition(@Param("id") Integer id,
                         @Param("status") String status,
                         @Param("date") LocalDate date);
}