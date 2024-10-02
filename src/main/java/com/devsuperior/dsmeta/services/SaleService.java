package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SalesBySellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	
	LocalDate minDateFmt;
	LocalDate maxDateFmt;

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleMinDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
		minDateFmt = parseDate(minDate, today.minusYears(1L));		
		maxDateFmt = parseDate(maxDate, today);
		
		Page<Sale> resultReport = repository.searchByDateAndName(minDateFmt, maxDateFmt, name, pageable);
		return resultReport.map(x -> new SaleMinDTO(x));
	}

	public List<SalesBySellerDTO> getSummary(String minDate, String maxDate) {
		minDateFmt = parseDate(minDate, today.minusYears(1L));		
		maxDateFmt = parseDate(maxDate, today);
		
		List<Seller> list = repository.searchTotalBySeller(minDateFmt, maxDateFmt);
		List<SalesBySellerDTO> resultSummary = list.stream().map(x -> new SalesBySellerDTO(x)).collect(Collectors.toList());
		
		return resultSummary;
	}
	
	public LocalDate parseDate(String date, LocalDate defaultDate){
		if(date != null && !date.isEmpty()){
			return LocalDate.parse(date, formatter);
		} else {
			return defaultDate;
		}
	}
}
