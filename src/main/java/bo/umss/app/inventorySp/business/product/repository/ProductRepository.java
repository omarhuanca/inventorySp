package bo.umss.app.inventorySp.business.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByCode(String code);

	boolean existsByCode(String code);

	@Query("SELECT p FROM Product p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :criteria, '%'))")
	List<Product> searchByDescription(@Param("criteria") String criteria);
	
	@Query("SELECT p FROM Product p JOIN p.line l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :nameLine, '%'))")
	List<Product> filterByNameLine(@Param("nameLine") String nameLine);
}
