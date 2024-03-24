package dev.vorstu.controller.v1;

import dev.vorstu.dto.CustomerDTO;
import dev.vorstu.entity.Customer;
import dev.vorstu.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO dto) {
        return ResponseEntity.ok(customerService.create(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> readAll() {
        return ResponseEntity.ok(customerService.readAll());
    }

    @PutMapping("/")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.update(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
