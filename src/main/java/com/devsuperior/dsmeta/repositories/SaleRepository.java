package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	@Query(value = "SELECT sale FROM Sale sale "
			+ "JOIN FETCH sale.seller s "
			+ "WHERE sale.date "
			+ "BETWEEN :minDate AND :maxDate "
			+ "AND UPPER(s.name) LIKE UPPER(CONCAT('%', :name, '%'))",
			countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
	public Page<Sale> searchByDateAndName(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

	@Query("SELECT DISTINCT seller "
		+ "FROM Seller seller "
		+ "JOIN FETCH seller.sales s "
		+ "WHERE s.date BETWEEN :minDate AND :maxDate "
		+ "ORDER BY seller.name ASC")
	public List<Seller> searchTotalBySeller(LocalDate minDate, LocalDate maxDate);
}
