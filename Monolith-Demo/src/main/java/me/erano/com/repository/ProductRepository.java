package me.erano.com.repository;

import me.erano.com.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //for field use By as method signature. (we need something special about "active=true")
    List<Product> findByActiveTrue();

}
