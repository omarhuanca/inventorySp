package bo.umss.app.inventorySp.business.product.mapper;

import org.springframework.stereotype.Service;

import bo.umss.app.inventorySp.business.product.dto.ProductDto;
import bo.umss.app.inventorySp.business.product.model.Product;
import bo.umss.app.inventorySp.mapper.IMapper;

@Service
public class ProductMapper implements IMapper<Product, ProductDto>{

	@Override
	public ProductDto toDto(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product toEntity(ProductDto dto, boolean isNew) {
		// TODO Auto-generated method stub
		return null;
	}

}
