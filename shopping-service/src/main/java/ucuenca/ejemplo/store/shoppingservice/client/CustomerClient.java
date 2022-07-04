package ucuenca.ejemplo.store.shoppingservice.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ucuenca.ejemplo.store.shoppingservice.model.Customer;
@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping(value = "/customers/{id}")
    @CircuitBreaker(name = "customerCB",fallbackMethod = "getCustomerFallback")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);


    default ResponseEntity<Customer> getCustomerFallback(RuntimeException e){
        Customer customer = Customer.builder()
        .firstName("none")
        .lastName("none")
        .email("none")
        .photoUrl("none").build();
    return ResponseEntity.ok(customer);
    }
}