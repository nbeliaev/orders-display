package dev.fr13.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "workplaces")
public class Workplace {
    @Id
    private String id;
    @Indexed
    private String uuid;
    private String name;
    private boolean active;
    @DBRef
    private Shop shop;
    @DBRef
    private Client client;

    public Workplace() {
    }

    public Workplace(String uuid, String name, boolean active) {
        this.uuid = uuid;
        this.name = name;
        this.active = active;
    }

    public Workplace(String uuid, String name) {
        this(uuid, name, true);
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getShopUuid() {
        return shop.getUuid();
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

        Workplace workplace = (Workplace) o;

        if (active != workplace.active) return false;
        if (!id.equals(workplace.id)) return false;
        if (!uuid.equals(workplace.uuid)) return false;
        if (!name.equals(workplace.name)) return false;
        if (!shop.equals(workplace.shop)) return false;
        return client.equals(workplace.client);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + shop.hashCode();
        result = 31 * result + client.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Workplace{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", shop=" + shop +
                '}';
    }
}
