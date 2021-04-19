package dev.fr13.util;

import dev.fr13.domain.Order;
import dev.fr13.domain.OrderItem;
import dev.fr13.domain.OrderItemStatus;
import dev.fr13.domain.Workplace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Обработка статусов и номеров строк блюд заказа")
class OrderItemsProcessorTest {
    private final Workplace workplace1 = new Workplace("dummyUuid1", "dummyWorkplace1");
    private final Workplace workplace2 = new Workplace("dummyUuid2", "dummyWorkplace2");

    private Order order;

    private final OrderItemsProcessor processor = new OrderItemsProcessor();

    @BeforeEach
    void receiveOrder() {
        order = initNewOrder();
        processor.setStatusesAndRowsNumbersInNewOrder(order);
    }

    @Test
    @DisplayName("в новом заказе должен установить номера строк и статус Новый для всех строк заказа")
    void shouldSetStatusesAndRowsNumbersInNewOrder() {
        var items = order.getItems();
        assertThat(items).hasSize(3);
        assertThat(items.get(0).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(1).getRowNumber()).isNotZero();
        assertThat(items.get(2).getWorkplace()).isEqualTo(workplace2);
    }

    @Test
    @DisplayName("должен сохранить текущие статусы и номера строк")
    void shouldKeepCurrentStatusesAndRowsNumbers() {
        startCookingFirstOrderItem(order);
        var newOrder0 = initNewOrder();
        processor.refillStatusesAndRowsNumbers(order, newOrder0);

        var items = newOrder0.getItems();
        assertThat(items).hasSize(3);
        assertThat(items.get(0).getStatus()).isEqualTo(OrderItemStatus.IN_PROCESS);
        assertThat(items.get(0).getRowNumber()).isZero();
        assertThat(items.get(1).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(1).getRowNumber()).isEqualTo(1);
        assertThat(items.get(2).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(2).getRowNumber()).isEqualTo(2);
    }

    @Test
    @DisplayName("должен сохранить текущие статусы и номера строк и добавить новую строку со статусом Новый")
    void shouldKeepCurrentStatusesAndRowsNumbersAndAddNewRowWithStatusNew() {
        startCookingFirstOrderItem(order);
        var newOrder0 = initNewOrder();
        processor.refillStatusesAndRowsNumbers(order, newOrder0);

        var newOrder1 = initNewOrder();
        addNewOrderItemWithEmptyStatus(newOrder1);
        processor.refillStatusesAndRowsNumbers(newOrder0, newOrder1);

        var items = newOrder1.getItems();
        assertThat(items).hasSize(4);
        assertThat(items.get(0).getStatus()).isEqualTo(OrderItemStatus.IN_PROCESS);
        assertThat(items.get(0).getRowNumber()).isZero();

        assertThat(items.get(1).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(1).getRowNumber()).isEqualTo(1);

        assertThat(items.get(2).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(2).getRowNumber()).isEqualTo(2);

        assertThat(items.get(3).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(3).getRowNumber()).isEqualTo(3);
    }

    @Test
    @DisplayName("должен разбить строку заказа на строки со статусом Новый и текущим статусом и пронумеровать новую строку")
    void shouldSplitOrderItemOnTwoRowsWithStatusesNewAndCurrentAndSetNumberForNewRow() {
        startCookingFirstOrderItem(order);
        var newOrder0 = initNewOrder();
        processor.refillStatusesAndRowsNumbers(order, newOrder0);

        var newOrder1 = initNewOrder();
        var item = newOrder1.getItems().get(0);
        var oldQnt = item.getQnt();
        var newQnt = 5;
        item.setQnt(oldQnt + newQnt);

        processor.refillStatusesAndRowsNumbers(newOrder0, newOrder1);

        var items = newOrder1.getItems();
        assertThat(items).hasSize(4);
        assertThat(items.get(0).getStatus()).isEqualTo(OrderItemStatus.IN_PROCESS);
        assertThat(items.get(0).getRowNumber()).isZero();
        assertThat(items.get(0).getQnt()).isEqualTo(oldQnt);
        assertThat(items.get(0).getWorkplace()).isEqualTo(workplace1);

        assertThat(items.get(1).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(1).getRowNumber()).isEqualTo(1);
        assertThat(items.get(1).getWorkplace()).isEqualTo(workplace1);

        assertThat(items.get(2).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(2).getRowNumber()).isEqualTo(2);
        assertThat(items.get(2).getWorkplace()).isEqualTo(workplace2);

        assertThat(items.get(3).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(3).getRowNumber()).isEqualTo(3);
        assertThat(items.get(3).getQnt()).isEqualTo(newQnt);
        assertThat(items.get(3).getWorkplace()).isEqualTo(workplace1);
    }

    @Test
    @DisplayName("должен добавить комментарий к существующей строке")
    void shouldAddNoteForCurrentRow() {
        startCookingFirstOrderItem(order);
        var newOrder0 = initNewOrder();
        processor.refillStatusesAndRowsNumbers(order, newOrder0);

        var newOrder1 = initNewOrder();
        var item = newOrder1.getItems().get(0);
        var oldQnt = item.getQnt();
        var newQnt = 3;
        item.setQnt(oldQnt - newQnt);

        processor.refillStatusesAndRowsNumbers(newOrder0, newOrder1);

        var items = newOrder1.getItems();
        assertThat(items).hasSize(3);
        assertThat(items.get(0).getStatus()).isEqualTo(OrderItemStatus.IN_PROCESS);
        assertThat(items.get(0).getRowNumber()).isZero();
        assertThat(items.get(0).getQnt()).isEqualTo(oldQnt - newQnt);
        assertThat(items.get(0).getNote()).contains("reduce");
        assertThat(items.get(0).getWorkplace()).isEqualTo(workplace1);

        assertThat(items.get(1).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(1).getRowNumber()).isEqualTo(1);
        assertThat(items.get(0).getWorkplace()).isEqualTo(workplace1);

        assertThat(items.get(2).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(2).getRowNumber()).isEqualTo(2);
        assertThat(items.get(2).getWorkplace()).isEqualTo(workplace2);
    }

    @Test
    @DisplayName("должен удалить строку со статусом Новый")
    void shouldDeleteRowWithStatusNew() {
        startCookingFirstOrderItem(order);
        var newOrder0 = initNewOrder();
        processor.refillStatusesAndRowsNumbers(order, newOrder0);

        var newOrder1 = initNewOrder();
        var item = newOrder1.getItems().get(0);
        var oldQnt = item.getQnt();
        var newQnt = 5;
        item.setQnt(oldQnt + newQnt);
        processor.refillStatusesAndRowsNumbers(newOrder0, newOrder1);

        var newOrder2 = initNewOrder();
        newOrder2.getItems().get(0).setQnt(oldQnt);
        processor.refillStatusesAndRowsNumbers(newOrder1, newOrder2);

        var items = newOrder2.getItems();
        assertThat(items).hasSize(3);
        assertThat(items.get(0).getStatus()).isEqualTo(OrderItemStatus.IN_PROCESS);
        assertThat(items.get(0).getName()).isEqualTo(item.getName());
        assertThat(items.get(1).getStatus()).isEqualTo(OrderItemStatus.NEW);
    }

    @Test
    @DisplayName("должен уменьшить количество в строке со статусом Новый")
    void shouldDecreaseQntRowWithStatusNew() {
        startCookingFirstOrderItem(order);
        var newOrder0 = initNewOrder();
        processor.refillStatusesAndRowsNumbers(order, newOrder0);

        var newOrder1 = initNewOrder();
        var item = newOrder1.getItems().get(0);
        var oldQnt = item.getQnt();
        var newQnt = 5;
        item.setQnt(oldQnt + newQnt);
        processor.refillStatusesAndRowsNumbers(newOrder0, newOrder1);

        var newOrder2 = initNewOrder();
        newOrder2.getItems().get(0).setQnt(oldQnt + newQnt - 1);
        processor.refillStatusesAndRowsNumbers(newOrder1, newOrder2);

        var items = newOrder2.getItems();
        assertThat(items).hasSize(4);
        assertThat(items.get(0).getStatus()).isEqualTo(OrderItemStatus.IN_PROCESS);
        assertThat(items.get(0).getName()).isEqualTo(item.getName());
        assertThat(items.get(3).getStatus()).isEqualTo(OrderItemStatus.NEW);
        assertThat(items.get(3).getQnt()).isEqualTo(newQnt - 1);
    }

    @Test
    @DisplayName("должен добавить комментарий к строке заказа")
    void shouldAddNoteToOrderItem() {
        var note = "dummy";
        startCookingFirstOrderItem(order);
        var newOrder0 = initNewOrder();
        newOrder0.getItems().get(0).setNote(note);
        processor.refillStatusesAndRowsNumbers(order, newOrder0);

        var items = newOrder0.getItems();
        assertThat(items).hasSize(3);
        assertThat(items.get(0).getNote()).isEqualTo(note);
    }

    private void addNewOrderItemWithEmptyStatus(Order order) {
        var item = new OrderItem(0, workplace1, OrderItemStatus.EMPTY, "item2", 15);
        order.addItem(item);
    }

    private void startCookingFirstOrderItem(Order order) {
        order.getItems().get(0).setStatus(OrderItemStatus.IN_PROCESS);
    }

    private Order initNewOrder() {
        var order = new Order("dummyUuid", new Date().getTime(), "dummyTable");
        var item0 = new OrderItem(0, workplace1, OrderItemStatus.EMPTY, "item0", 5);
        var item1 = new OrderItem(0, workplace1, OrderItemStatus.EMPTY, "item1", 10);
        var item2 = new OrderItem(0, workplace2, OrderItemStatus.EMPTY, "item3", 15);
        order.addItem(item0);
        order.addItem(item1);
        order.addItem(item2);
        return order;
    }
}