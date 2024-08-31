package bo.umss.app.inventorySp.business.provider.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.provider.dto.ProviderDto;
import bo.umss.app.inventorySp.business.provider.model.Provider;
import bo.umss.app.inventorySp.business.provider.service.ProviderService;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class ProviderMapper implements IMapper<Provider, ProviderDto> {

	@Autowired
	private ProviderService service;

	@Override
	public ProviderDto toDto(Provider entity) {
		return ProviderDto.at(entity.getName(), entity.getPhoneNumber());
	}

	@Override
	public Provider toEntity(ProviderDto dto, boolean isNew) {
		if (isNew) {
			return Provider.at(dto.getName(), dto.getPhoneNumber());
		} else {
			Provider recover = service.findByName(dto.getName());
			recover.setPhoneNumber(dto.getPhoneNumber());

			return recover;
		}
	}
}
