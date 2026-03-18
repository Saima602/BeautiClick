package com.beuticlick.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beuticlick.entity.BillingEntity;

public interface BillingRepo extends JpaRepository<BillingEntity, Long> {

}
