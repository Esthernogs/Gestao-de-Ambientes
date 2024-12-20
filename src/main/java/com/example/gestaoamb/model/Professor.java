package com.example.gestaoamb.model;

import com.example.gestaoamb.repository.RoleRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@DiscriminatorValue(value = "P")
@Entity
public class Professor extends Pessoa{
    public static final String ROLE_PROFESSOR = "PROFESSOR";

    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_PROFESSOR);
        this.getUser().getRoles().add(role);
    }

}
