package dev.fr13.dtos;

public class WorkplaceDto {
    private String uuid;
    private String name;
    private boolean active;

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

    @Override
    public String toString() {
        return "WorkplaceDto{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
