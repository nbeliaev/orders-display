package dev.fr13.domain;

public class OrderItem {
    private long id;
    private int rowNumber;
    private Workplace workplace;
    private OrderItemStatus status;
    private String name;
    private int qnt;
    private String note;
    private Order order;

    public OrderItem() {
    }

    public OrderItem(int rowNumber, Workplace workplace, OrderItemStatus status, String name, int qnt, String note) {
        this.rowNumber = rowNumber;
        this.workplace = workplace;
        this.status = status;
        this.name = name;
        this.qnt = qnt;
        this.note = note;
    }

    public OrderItem(int rowNumber, Workplace workplace, OrderItemStatus status, String name, int qnt) {
        this(rowNumber, workplace, status, name, qnt, "");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public String getWorkplaceUuid() {
        return workplace.getUuid();
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public String getStatusName() {
        return status.getName();
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", rowNumber=" + rowNumber +
                ", workplace=" + workplace +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", qnt=" + qnt +
                ", note='" + note + '\'' +
                '}';
    }
}
