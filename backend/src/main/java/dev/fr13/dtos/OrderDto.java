package dev.fr13.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDto {
    private String uuid;
    private String client;
    private String shop;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        if (!uuid.equals(orderDto.uuid)) return false;
        if (!client.equals(orderDto.client)) return false;
        return shop.equals(orderDto.shop);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + shop.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "uuid='" + uuid + '\'' +
                ", client='" + client + '\'' +
                ", timestamp=" + timestamp +
                ", table='" + table + '\'' +
                '}';
    }
}
