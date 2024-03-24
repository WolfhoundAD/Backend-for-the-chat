package dev.vorstu.controller.v1;

import dev.vorstu.dto.ProductDTO;
import dev.vorstu.entity.Product;
import dev.vorstu.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> readAll() {
        return ResponseEntity.ok(productService.readAll());
    }

    @PutMapping("/")
    public ResponseEntity<Product> update(@RequestBody Product product) {
        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inStock")
    public ResponseEntity<List<Product>> readInStockProducts() {
        List<Product> inStockProducts = productService.readInStockProducts();
        return ResponseEntity.ok(inStockProducts);
    }
}