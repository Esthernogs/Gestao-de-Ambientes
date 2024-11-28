package com.example.gestaoamb.model;

import com.example.gestaoamb.enums.Horarios;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendarAmbiente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ambiente ambiente;

    @Temporal(TemporalType.DATE)
    @Basic
    private Date dateAgendamento;
    private Horarios horarios;
}
