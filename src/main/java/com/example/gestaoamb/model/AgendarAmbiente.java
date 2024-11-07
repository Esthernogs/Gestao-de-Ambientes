package com.example.gestaoamb.model;

import com.example.gestaoamb.enums.Horarios;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AgendarAmbiente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Ambiente ambiente;
    @Temporal(TemporalType.DATE)
    @Basic
    private Date dateAgendamento;
    private Horarios horarios;

}
