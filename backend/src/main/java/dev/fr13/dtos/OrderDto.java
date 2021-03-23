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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        if (timestamp != orderDto.timestamp) return false;
        if (!uuid.equals(orderDto.uuid)) return false;
        return table.equals(orderDto.table);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + table.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "uuid='" + uuid + '\'' +
                ", timestamp=" + timestamp +
                ", table='" + table + '\'' +
                '}';
    }
}
