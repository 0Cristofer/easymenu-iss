package com.bccog.easymenu.entidades.estabelecimento;

import com.bccog.easymenu.entidades.embutido.Delivery;
import com.bccog.easymenu.entidades.embutido.Endereco;
import com.bccog.easymenu.entidades.embutido.HorarioAtendimento;

/**
 * Subclasse de estabelecimento que representa um estabelecimento em que os produtos são levados na mesa
 * @author Cristofer Oswald
 * @since 06/06/2017
 */
public class EstabelecimentoMesa extends Estabelecimento {

    public EstabelecimentoMesa(){
        super();
    }

    public EstabelecimentoMesa(String nome, Endereco endereco, HorarioAtendimento horario_atendimento,
                               float nota) {
        super(nome, endereco, horario_atendimento, nota);
    }

    public EstabelecimentoMesa(String nome, Endereco endereco, HorarioAtendimento horario_atendimento,
                               float nota, Delivery delivery, boolean so_delivery) {
        super(nome, endereco, horario_atendimento, nota, delivery, so_delivery);
    }
}
