package bo.umss.app.inventorySp.business.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByCode(String code);

	boolean existsByCode(String code);
}
