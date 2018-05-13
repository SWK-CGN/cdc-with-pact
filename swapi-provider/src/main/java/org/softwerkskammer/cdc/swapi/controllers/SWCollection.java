package org.softwerkskammer.cdc.swapi.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@JsonPropertyOrder({"count", "items"})
public class SWCollection<T> {

    private final List<T> items;

    SWCollection(final List<T> items) {
        this.items = items != null ? items : emptyList();
    }

    @JsonProperty("count")
    public int getCount() {
        return items.size();
    }

    @JsonProperty("items")
    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

}
