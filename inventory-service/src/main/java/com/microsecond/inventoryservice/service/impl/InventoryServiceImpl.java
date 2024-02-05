package com.microsecond.inventoryservice.service.impl;

import com.microsecond.inventoryservice.dto.InventoryResponseDto;
import com.microsecond.inventoryservice.repository.InventoryRepository;
import com.microsecond.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<InventoryResponseDto> isInStock(List<String> skuCode) {
        return  inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                        InventoryResponseDto.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                        ).toList();

    }
}
