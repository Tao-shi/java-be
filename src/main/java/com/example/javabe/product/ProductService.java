package com.example.javabe.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(Product product) {
        product.setId(null); // ensure a new row is inserted
        return repository.save(product);
    }

    public Product update(Long id, Product incoming) {
        Product existing = findById(id);
        existing.setName(incoming.getName());
        existing.setCategory(incoming.getCategory());
        existing.setPrice(incoming.getPrice());
        existing.setQuantity(incoming.getQuantity());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // Flexible query: any combination of name, category, and price range.
    public List<Product> search(String name, String category, Double minPrice, Double maxPrice) {
        double min = minPrice != null ? minPrice : 0.0;
        double max = maxPrice != null ? maxPrice : Double.MAX_VALUE;

        return repository.findAll().stream()
                .filter(p -> name == null || p.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> category == null || p.getCategory().equalsIgnoreCase(category))
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .toList();
    }
}
