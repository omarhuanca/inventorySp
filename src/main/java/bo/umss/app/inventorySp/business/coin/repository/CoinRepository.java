package bo.umss.app.inventorySp.business.coin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.coin.model.Coin;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long>{

	Coin findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
