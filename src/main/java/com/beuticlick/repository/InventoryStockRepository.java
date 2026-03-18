package com.beuticlick.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beuticlick.entity.InventoryStock;

public interface InventoryStockRepository extends JpaRepository<InventoryStock, Long> {

	InventoryStock findByProductIdAndSalonId(Long productId, Long salonId);

}
