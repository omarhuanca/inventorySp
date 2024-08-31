package bo.umss.app.inventorySp.business.changePrice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.changePrice.model.ChangePrice;

@Repository
public interface ChangePriceRepository extends JpaRepository<ChangePrice, Long> {

}
