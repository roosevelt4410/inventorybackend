package co.com.bancounion.inventario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.bancounion.inventario.model.Sale;

public interface ISaleRepository extends JpaRepository<Sale, UUID> {

}
