package dev.vorstu.service;

import dev.vorstu.mapper.CustomerMapper;
import dev.vorstu.repository.CustomerRepository;
import dev.vorstu.dto.CustomerDTO;
import dev.vorstu.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Создает нового клиента на основе переданных данных.
     *
     * @param dto данные клиента для создания
     * @return созданный объект Customer
     */

    public CustomerDTO create(CustomerDTO dto) {
        Customer customer = CustomerMapper.INSTANCE.customerDTOToCustomer(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.customerToCustomerDTO(savedCustomer);
    }

    public List<Customer> readAll() {
        return customerRepository.findAll();
    }

    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete customer with id: " + id, e);
        }
    }

    public Customer findById(Long id) {
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            return customer.orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to find customer with id: " + id, e);
        }
    }
}
