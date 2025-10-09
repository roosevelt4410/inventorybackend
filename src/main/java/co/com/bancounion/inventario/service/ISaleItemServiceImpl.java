package co.com.bancounion.inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import co.com.bancounion.inventario.model.SaleItem;

import co.com.bancounion.inventario.repository.ISaleItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ISaleItemServiceImpl implements ISaleItemService{

	private final ISaleItemRepository repository;

    public ISaleItemServiceImpl(ISaleItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SaleItem> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<SaleItem> findById(UUID id) {
        return repository.findById(id);
    }


    @Override
    public SaleItem create(SaleItem saleitem) {
        return repository.save(saleitem);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("sale item no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

}
