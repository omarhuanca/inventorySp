package bo.umss.app.inventorySp.mapper;

public interface IMapper<E, K> {

	K toDto(E entity);

	E toEntity(K dto, boolean isNew);
}
