package ucuenca.ejemplo.store.serviceproduct.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ucuenca.ejemplo.store.serviceproduct.entity.Category;
import ucuenca.ejemplo.store.serviceproduct.entity.Product;
import ucuenca.ejemplo.store.serviceproduct.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl  implements ProductService {
    @Autowired
    private final ProductRepository productRepository;
    
    @Override
    public List<Product> listAllProduct() {
        // TODO Auto-generated method stub
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        // TODO Auto-generated method stub
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        // TODO Auto-generated method stub
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        // TODO Auto-generated method stub
        Product productoDB=getProduct(product.getId());
        if(productoDB==null){
            return null;
        }
        productoDB.setName(product.getName());
        productoDB.setDescription(product.getDescription());
        productoDB.setPrice(product.getPrice());
        productoDB.setCategory(product.getCategory());
        return productRepository.save(productoDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        // TODO Auto-generated method stub
        Product productoDB=getProduct(id);
        if(productoDB==null){
            return null;
        }
        productoDB.setStatus("DELETED");
        return productRepository.save(productoDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);
        if (null == productDB){
            return null;
        }
        Double stock =  productDB.getStock() + quantity;
        productDB.setStock(stock);
        return productRepository.save(productDB);
    }
    
}
