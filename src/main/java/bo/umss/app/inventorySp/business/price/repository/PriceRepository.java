package bo.umss.app.inventorySp.business.price.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.price.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

	Optional<Price> findByValue(Double value);
}
