package dev.fr13.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shops")
public class Shop {
    @Id
    private String id;
    @Indexed
    private String uuid;
    private String name;
    private boolean active;
    @DBRef
    private Client client;

    public Shop() {
    }

    public Shop(String id, String uuid, String name, boolean active, Client client) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.active = active;
        this.client = client;
    }

    public Shop(String uuid, String name, boolean active, Client client) {
        this("", uuid, name, active, client);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getClientUuid() {
        return client.getUuid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (!id.equals(shop.id)) return false;
        if (!uuid.equals(shop.uuid)) return false;
        if (!name.equals(shop.name)) return false;
        return client.equals(shop.client);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + client.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", client=" + client +
                '}';
    }
}