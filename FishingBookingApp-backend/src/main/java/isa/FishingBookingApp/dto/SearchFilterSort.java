package isa.FishingBookingApp.dto;

import java.util.List;

public class SearchFilterSort {
    private String sort;
    private List<String> types;

    public SearchFilterSort(String sort, List<String> types) {
        this.sort = sort;
        this.types = types;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
