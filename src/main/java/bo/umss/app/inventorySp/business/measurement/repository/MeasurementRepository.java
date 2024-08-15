package bo.umss.app.inventorySp.business.measurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.umss.app.inventorySp.business.measurement.model.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

	Measurement findByCode(String potentialCode);

	boolean existsByCode(String potentialCode);
}
