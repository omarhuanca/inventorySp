package bo.umss.app.inventorySp.business.price.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.price.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

	Price findByCode(String code);

	boolean existsByCode(String code);
}
