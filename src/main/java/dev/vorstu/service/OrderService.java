package dev.vorstu.service;

import dev.vorstu.repository.OrderRepository;
import dev.vorstu.entity.Orders;
import dev.vorstu.dto.OrderDTO;
import dev.vorstu.entity.Customer;
import dev.vorstu.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository ordersRepository;
     private final CustomerService customerService;

    public Orders create(OrderDTO dto) {
        Orders orders = OrderMapper.INSTANCE.orderDTOToOrder(dto);
        Customer customer = customerService.findById(dto.getCustomerId());
        orders.setCustomer(customer);
        return ordersRepository.save(orders);
    }

    public List<Orders> readAll() {
        return ordersRepository.findAll();
    }

    public Orders update(Orders orders) {
        return ordersRepository.save(orders);
    }

    public void delete(Long id) {
        try {
            ordersRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete order with id: " + id, e);
        }
    }

    public Orders findById(Long id) {
        try {
            Optional<Orders> order = ordersRepository.findById(id);
            return order.orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to find order with id: " + id, e);
        }
    }
}
