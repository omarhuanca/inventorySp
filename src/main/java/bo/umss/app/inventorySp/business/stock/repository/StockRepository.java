package bo.umss.app.inventorySp.business.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.stock.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	Stock findByCode(String code);

	boolean existsByCode(String code);
}
