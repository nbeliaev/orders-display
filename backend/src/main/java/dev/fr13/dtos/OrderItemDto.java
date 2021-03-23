package dev.fr13.dtos;

import java.util.Objects;

public class OrderItemDto {
    private int rowNumber;
    private String workplace;
    private String status;
    private String name;
    private int qnt;
    private String note;

    public OrderItemDto() {
    }

    public OrderItemDto(int rowNumber, String workplace, String status, String name, int qnt, String note) {
        this.rowNumber = rowNumber;
        this.workplace = workplace;
        this.status = status;
        this.name = name;
        this.qnt = qnt;
        this.note = note;
    }

    public OrderItemDto(int rowNumber, String workplace, String status, String name, int qnt) {
        this(rowNumber, workplace, status, name, qnt, "");
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

        OrderItemDto that = (OrderItemDto) o;

        if (rowNumber != that.rowNumber) return false;
        if (qnt != that.qnt) return false;
        if (!workplace.equals(that.workplace)) return false;
        if (!status.equals(that.status)) return false;
        if (!name.equals(that.name)) return false;
        return Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        int result = rowNumber;
        result = 31 * result + workplace.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + qnt;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "rowNumber=" + rowNumber +
                ", workplace='" + workplace + '\'' +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", qnt=" + qnt +
                ", note='" + note + '\'' +
                '}';
    }
}
