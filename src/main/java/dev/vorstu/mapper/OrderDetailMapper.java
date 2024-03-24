package dev.vorstu.mapper;

import dev.vorstu.dto.OrderDetailDTO;
import dev.vorstu.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    OrderDetail orderDetailDTOToOrderDetail(OrderDetailDTO dto);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    OrderDetailDTO orderDetailToOrderDetailDTO(OrderDetail orderDetail);
}
