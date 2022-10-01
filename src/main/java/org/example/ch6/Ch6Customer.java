package org.example.ch6;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.OrderColumn;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Data
@Entity
public class Ch6Customer {
    @Id
    @GeneratedValue
    private Long id;

    @OrderColumn
    @OrderBy("goal DESC")
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = ALL)
    private List<Ch6Payment> payments = new ArrayList<>();
}
