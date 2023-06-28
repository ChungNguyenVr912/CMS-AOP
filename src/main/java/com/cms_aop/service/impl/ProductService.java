package com.cms_aop.service.impl;

import com.cms_aop.model.Product;
import com.cms_aop.repository.ProductRepository;
import com.cms_aop.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements GeneralService<Product> {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        if (pageNumber >= 1) pageable = pageable.withPage(pageNumber - 1);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Product address) {
        productRepository.save(address);
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }
}
