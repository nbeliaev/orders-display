package dev.fr13.dtos;

public class WorkplaceDto {
    private String uuid;
    private String name;
    private boolean active;
    private String shop;
    private String client;

    public WorkplaceDto() {
    }

    public WorkplaceDto(String uuid, String name, boolean active) {
        this.uuid = uuid;
        this.name = name;
        this.active = active;
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

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
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

        WorkplaceDto that = (WorkplaceDto) o;

        if (active != that.active) return false;
        if (!uuid.equals(that.uuid)) return false;
        if (!name.equals(that.name)) return false;
        if (!shop.equals(that.shop)) return false;
        return client.equals(that.client);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + shop.hashCode();
        result = 31 * result + client.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WorkplaceDto{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", shop='" + shop + '\'' +
                '}';
    }
}
