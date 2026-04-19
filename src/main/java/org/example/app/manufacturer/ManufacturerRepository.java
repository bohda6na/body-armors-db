package org.example.app.manufacturer;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ManufacturerRepository extends CrudRepository<Manufacturer, Integer> {

    @Query("SELECT * FROM manufacturer ORDER BY brandName")
    List<Manufacturer> findAllOrdered();
}