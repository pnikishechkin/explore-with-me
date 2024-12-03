package ru.practicum.ewm.main.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@Getter @Setter
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    @Length(min = 6, max = 254)
    private String email;

    @Column(name = "name", nullable = false)
    @Length(min = 2, max = 250)
    private String name;
}
