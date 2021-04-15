package dev.fr13.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class Client {
    @Id
    private String id;
    @Indexed
    private String uuid;
    private String name;
    private boolean active;

    public Client() {
    }

    public Client(String id, String uuid, String name, boolean active) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.active = active;
    }

    public Client(String uuid, String name) {
        this("", uuid, name, true);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!id.equals(client.id)) return false;
        if (!uuid.equals(client.uuid)) return false;
        return name.equals(client.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
