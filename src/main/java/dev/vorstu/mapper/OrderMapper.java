package dev.vorstu.mapper;


import dev.vorstu.entity.Orders;
import dev.vorstu.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Orders orderDTOToOrder(OrderDTO dto);

    @Mapping(source = "orderDate", target = "orderDate")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "customer.id", target = "customerId")
    OrderDTO orderToOrderDTO(Orders orders);
}