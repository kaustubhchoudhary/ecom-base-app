// package ecom.base.app.user;

// import java.time.LocalDateTime;

// import ecom.base.app.role.Role;
// import jakarta.persistence.*;
// import lombok.*;

// @Entity
// @Table(name = "users")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class User {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;

// @Column(nullable = false)
// private String name;

// @Column(nullable = false, unique = true)
// private String email;

// @Column(nullable = false)
// private String password;

// @Column(nullable = false)
// private String phone;

// @ManyToOne(fetch = FetchType.EAGER)
// @JoinColumn(name = "role_id", nullable = false)
// private Role role;

// @Column(nullable = false)
// private Boolean active;

// @Column(nullable = false)
// private LocalDateTime createdAt;

// @Column(nullable = false)
// private LocalDateTime updatedAt;
// }