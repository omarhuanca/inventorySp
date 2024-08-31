package bo.umss.app.inventorySp.business.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bo.umss.app.inventorySp.business.provider.model.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

	Provider findByName(String name);

	boolean existsByName(String name);
}
