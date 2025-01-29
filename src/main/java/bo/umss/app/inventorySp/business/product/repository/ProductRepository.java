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

	@Query(nativeQuery = true, value = "SELECT pp.prd_id, pp.prd_code, pp.prd_description, pp.prd_st_id, pp.prd_pr_id, pp.prd_sc_pr_id, pp.prd_ln_id, pp.prd_prv_id FROM prd_product pp WHERE pp.prd_description ILIKE %:criteria%")
	List<Product> searchByDescription(@Param("criteria") String criteria);
}
