package dev.fr13.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private long id;
    private String uuid;
    private long timestamp;
    private String table;
    private Shop shop;
    private Client client;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (!uuid.equals(order.uuid)) return false;
        if (!shop.equals(order.shop)) return false;
        return client.equals(order.client);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + uuid.hashCode();
        result = 31 * result + shop.hashCode();
        result = 31 * result + client.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", timestamp=" + timestamp +
                ", table='" + table + '\'' +
                ", shop=" + shop +
                ", client=" + client +
                '}';
    }
}
