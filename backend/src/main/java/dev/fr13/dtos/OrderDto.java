package dev.fr13.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDto {
    private String uuid;
    private long timestamp;
    private String table;
    private List<OrderItemDto> items = new ArrayList<>();

    public OrderDto() {
    }

    public OrderDto(String uuid, long timestamp, String table) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.table = table;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<OrderItemDto> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
