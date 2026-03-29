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
		// If no stock record exists yet for this product, create one (upsert behaviour)
		InventoryStock stock = stockRepo.findByProductIdAndSalonId(productId, salonId);

		if (stock == null) {
			stock = new InventoryStock();
			stock.setProductId(productId);
			stock.setSalonId(salonId);
			stock.setQuantityAvailable(0);
		}

		stock.setQuantityAvailable(stock.getQuantityAvailable() + qty);
		stockRepo.save(stock);
	}

	public void useStock(Long productId, int qty, Long salonId) {
		InventoryStock stock = stockRepo.findByProductIdAndSalonId(productId, salonId);

		if (stock == null) {
			throw new RuntimeException("No stock record found for productId=" + productId + " salonId=" + salonId);
		}

		if (stock.getQuantityAvailable() < qty) {
			throw new RuntimeException("Insufficient stock for productId=" + productId + ". Available: "
					+ stock.getQuantityAvailable() + ", requested: " + qty);
		}

		stock.setQuantityAvailable(stock.getQuantityAvailable() - qty);
		stockRepo.save(stock);
	}
}
