package com.lambo.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class OwnerResponse {
    private List<OwnerDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
