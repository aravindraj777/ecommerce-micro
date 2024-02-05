package com.microsecond.inventoryservice.service.impl;

import com.microsecond.inventoryservice.repository.InventoryRepository;
import com.microsecond.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @Override
    public boolean isInStock(String skuCode) {
        return  inventoryRepository.findBySkuCode(skuCode).isPresent();

    }
}
