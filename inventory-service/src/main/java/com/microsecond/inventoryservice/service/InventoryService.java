package com.microsecond.inventoryservice.service;

import com.microsecond.inventoryservice.dto.InventoryResponseDto;

import java.util.List;

public interface InventoryService {

    public List<InventoryResponseDto> isInStock(List<String> skuCode);
}
