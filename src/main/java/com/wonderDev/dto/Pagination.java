package com.wonderDev.dto;

public class Pagination {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public Pagination(int page, int size, long totalElements, int totalPages) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
}