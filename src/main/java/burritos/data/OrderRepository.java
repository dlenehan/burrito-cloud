package burritos.data;

import burritos.Order;

public interface OrderRepository {

Order save(Order order);
}
