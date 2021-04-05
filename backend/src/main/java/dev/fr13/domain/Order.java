package dev.fr13.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document("orders")
public class Order {
    @Id
    private String id;
    @Indexed
    private String uuid;
    private long timestamp;
    private String table;
    @DBRef
    private Shop shop;
    @DBRef
    private Client client;
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    public Order(String uuid, long timestamp, String table) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.table = table;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getShopUuid() {
        return shop.getUuid();
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Client getClient() {
        return client;
    }

    public String getClientUud() {
        return client.getUuid();
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

        if (timestamp != order.timestamp) return false;
        if (!id.equals(order.id)) return false;
        if (!uuid.equals(order.uuid)) return false;
        if (!table.equals(order.table)) return false;
        if (!shop.equals(order.shop)) return false;
        return client.equals(order.client);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + table.hashCode();
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
