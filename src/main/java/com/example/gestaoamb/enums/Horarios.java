package com.example.gestaoamb.enums;

public enum Horarios {
    horario1("07h00"),
    horario2("07h50"),
    horario3("08h40"),
    horario4("09h30"),
    horario5("10h20"),
    horario6("10h40"),
    horario7("11h30"),
    horario8("12h20"),
    horario9("12h40"),
    horario10("13h30"),
    horario11("14h20"),
    horario12("15h10"),
    horario13("15h30"),
    horario14("16h20"),
    horario15("17h10"),
    horario16("18h00");

    private String descricao;


    Horarios(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
