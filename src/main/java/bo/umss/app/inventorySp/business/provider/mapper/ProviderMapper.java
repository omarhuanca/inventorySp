package bo.umss.app.inventorySp.business.provider.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.provider.dto.ProviderDto;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.provider.service.ProviderService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class ProviderMapper implements IMapper<Provider, ProviderDto> {

	private final ProviderService service;

	public ProviderMapper(ProviderService service) {
		this.service = service;
	}

	@Override
	public ProviderDto toDto(Provider entity) {
		return ProviderDto.at(entity.getId(), entity.getName());
	}

	@Override
	public Provider toEntity(ProviderDto dto, boolean isNew) {
		if (isNew) {
			return Provider.at(dto.getName());
		} else {
			Provider recover = service.findById(dto.getId());
			recover.setName(StringUtils.upperCase(dto.getName()));

			return recover;
		}
	}
}
