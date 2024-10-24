package bo.umss.app.inventorySp.business.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.provider.model.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

	Provider findByName(String name);

	boolean existsByName(String name);
}
