package dev.fr13.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private long id;
    private String uuid;
    private long timestamp;
    private String table;
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    public Order(String uuid, long timestamp, String table) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.table = table;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", timestamp=" + timestamp +
                ", table='" + table + '\'' +
                '}';
    }
}
