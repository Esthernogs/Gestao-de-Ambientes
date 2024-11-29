package com.example.gestaoamb.model;

import com.example.gestaoamb.repository.RoleRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@DiscriminatorValue(value = "A")
@Entity
public class Administrador extends Pessoa{
    public static final String ROLE_ADMINISTRADOR = "ADMINISTRADOR";

    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_ADMINISTRADOR);
        this.getUser().getRoles().add(role);
    }
}
