package dev.fr13.dtos;

public class ShopDto {
    private String uuid;
    private String name;
    private boolean active;
    private String client;

    public ShopDto() {
    }

    public ShopDto(String uuid, String name, boolean active, String client) {
        this.uuid = uuid;
        this.name = name;
        this.active = active;
        this.client = client;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopDto shopDto = (ShopDto) o;

        if (active != shopDto.active) return false;
        if (!uuid.equals(shopDto.uuid)) return false;
        if (!name.equals(shopDto.name)) return false;
        return client.equals(shopDto.client);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + client.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ShopDto{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", clientUuid='" + client + '\'' +
                '}';
    }
}
