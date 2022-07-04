package ucuenca.ejemplo.store.shoppingservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ucuenca.ejemplo.store.shoppingservice.model.Product;

@FeignClient(name = "product-service",path = "/products")
public interface ProductClient {

    @CircuitBreaker(name = "productCB",fallbackMethod = "getProductFallback")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id);

    @CircuitBreaker(name = "productUpdateCB",fallbackMethod = "getProductFallback")
    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable("id")Long id, @RequestParam(name="stock",required = true)Double stock);


    default ResponseEntity<Product> getProductFallback(RuntimeException e ){
        // Product product=Product.builder()
        //         .id(0L)
        //         .name("none")
        //         .description("none")
        //         .price(0.0)
        //         .stock(0.0)
        //         .build();
        return ResponseEntity.ok(null);
    }

}