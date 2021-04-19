package dev.fr13.dtos;

public class ClientDto {
    private String uuid;
    private String name;
    private boolean active;

    public ClientDto() {
    }

    public ClientDto(String uuid, String name, boolean active) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientDto clientDto = (ClientDto) o;

        if (!uuid.equals(clientDto.uuid)) return false;
        return name.equals(clientDto.name);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
