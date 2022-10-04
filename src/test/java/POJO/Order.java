package POJO;

import java.util.List;

public class Order {

    private List<OrderDetails> Orders;

    public List<OrderDetails> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        Orders = orders;
    }
}
