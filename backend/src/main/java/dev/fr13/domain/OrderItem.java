package dev.fr13.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class OrderItem {
    private int rowNumber;
    @DBRef
    private Workplace workplace;
    private OrderItemStatus status;
    private String name;
    private int qnt;
    private String note;

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

    public OrderItem(int rowNumber, Workplace workplace, String name, int qnt) {
        this(rowNumber, workplace, OrderItemStatus.NEW, name, qnt, "");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (rowNumber != orderItem.rowNumber) return false;
        if (qnt != orderItem.qnt) return false;
        if (!workplace.equals(orderItem.workplace)) return false;
        if (status != orderItem.status) return false;
        if (!name.equals(orderItem.name)) return false;
        return note.equals(orderItem.note);
    }

    @Override
    public int hashCode() {
        int result = rowNumber;
        result = 31 * result + workplace.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + qnt;
        result = 31 * result + note.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                ", rowNumber=" + rowNumber +
                ", workplace=" + workplace +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", qnt=" + qnt +
                ", note='" + note + '\'' +
                '}';
    }
}
