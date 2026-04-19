package org.example.app.bodyarmor;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ArmorModelRepository extends CrudRepository<ArmorModel, Integer> {

    @Query("SELECT * FROM armormodel ORDER BY modelName")
    List<ArmorModel> findAllOrdered();

    @Query("SELECT * FROM armormodel WHERE modelID = :id")
    ArmorModel findByModelID(@Param("id") Integer id);
}