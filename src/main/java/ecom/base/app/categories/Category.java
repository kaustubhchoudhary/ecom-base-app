package ecom.base.app.categories;

import java.util.ArrayList;
import java.util.List;

import ecom.base.app.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    @Column(unique = false, nullable = true, length = 100)
    private String description;

    @Column(name = "isActive", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "category"/* , fetch = FetchType.EAGER */)
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return "\n Category [id=" + id + ", name=" + name + ", description=" + description + ", active=" + active + "]";
    }

}
