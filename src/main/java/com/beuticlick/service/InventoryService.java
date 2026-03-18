package com.beuticlick.service;

import org.springframework.stereotype.Service;

import com.beuticlick.entity.InventoryStock;
import com.beuticlick.repository.InventoryStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryStockRepository stockRepo;

	public void addStock(Long productId, int qty, Long salonId) {
		InventoryStock stock = stockRepo.findByProductIdAndSalonId(productId, salonId);

		stock.setProductId(productId);
		stock.setSalonId(salonId);
		stock.setQuantityAvailable(stock.getQuantityAvailable() + qty);

		stockRepo.save(stock);
	}

	public void useStock(Long productId, int qty, Long salonId) {
		InventoryStock stock = stockRepo.findByProductIdAndSalonId(productId, salonId);

		if (stock.getQuantityAvailable() < qty) {
			throw new RuntimeException("Low stock");
		}

		stock.setQuantityAvailable(stock.getQuantityAvailable() - qty);
		stockRepo.save(stock);
	}
}