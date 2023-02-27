package org.adaschool.api.controller.product;

import org.adaschool.api.exception.ProductNotFoundException;
import org.adaschool.api.repository.product.Product;
import org.adaschool.api.service.product.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products/")
public class ProductsController {

    @Autowired
    private final ProductsService productsService;

    public ProductsController(@Autowired ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product p) {
        Product product = productsService.save(p);
        URI createdProductUri = URI.create("");
        return ResponseEntity.created(createdProductUri).body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(productsService.all());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") String id) {
        Optional<Product> optionalProduct = productsService.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return ResponseEntity.ok(product);
        }
        else throw new ProductNotFoundException(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product p) {
        Optional<Product> optionalProduct = productsService.findById(id);
        if (optionalProduct.isPresent()) {
            productsService.save(optionalProduct.get());
            Product updatedProduct = productsService.update(p, id);
            return ResponseEntity.ok(updatedProduct);
        }
        else throw new ProductNotFoundException(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        Optional<Product> optionalProduct = productsService.findById(id);
        if (optionalProduct.isPresent()) {
            productsService.save(optionalProduct.get());
            productsService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        else throw new ProductNotFoundException(id);
    }
}
