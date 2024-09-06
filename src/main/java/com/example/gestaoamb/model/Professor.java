package com.example.gestaoamb.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@DiscriminatorValue(value = "P")
@Entity
public class Professor extends Pessoa{

}
