package com.billerp.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}
