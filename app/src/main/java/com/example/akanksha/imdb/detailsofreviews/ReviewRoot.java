
package com.example.akanksha.imdb.detailsofreviews;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ReviewRoot {

    @Expose
    private Long id;
    @Expose
    private Long page;
    @Expose
    private ArrayList<Review> results;
    @SerializedName("total_pages")
    private Long totalPages;
    @SerializedName("total_results")
    private Long totalResults;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public ArrayList<Review> getResults() {
        return results;
    }


    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}
