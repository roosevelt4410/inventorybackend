package co.com.bancounion.inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.com.bancounion.inventario.model.SaleItem;

public interface ISaleItemService {
	 List<SaleItem> findAll();
	 Optional<SaleItem> findById(UUID id);
	 SaleItem create(SaleItem customer);
	 void delete(UUID id);
}
