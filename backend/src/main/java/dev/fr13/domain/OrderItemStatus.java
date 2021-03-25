package dev.fr13.domain;

public enum OrderItemStatus {
    NEW("new"),
    IN_PROCESS("in-process"),
    COMPLETED("completed"),
    EMPTY("");

    private final String name;

    OrderItemStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OrderItemStatus getStatusByName(String name) {
        switch (name) {
            case "new":
                return NEW;
            case "":
                return EMPTY;
            case "in-process":
                return IN_PROCESS;
            case "completed":
                return COMPLETED;
            default:
                throw new IllegalArgumentException(String.format("Invalid order status name %s", name));
        }
    }
}
