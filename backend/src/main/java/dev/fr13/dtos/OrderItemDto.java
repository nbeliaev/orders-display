package dev.fr13.dtos;

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
}
