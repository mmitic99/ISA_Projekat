package isa.FishingBookingApp.dto;

import java.util.List;

public class SearchFilterSort {
    private String sort;
    private List<String> types;
    private String search;

    public SearchFilterSort(String sort, List<String> types, String search) {
        this.sort = sort;
        this.types = types;
        this.search = search;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
