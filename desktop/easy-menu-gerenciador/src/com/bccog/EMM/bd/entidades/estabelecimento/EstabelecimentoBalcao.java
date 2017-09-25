package com.bccog.EMM.bd.entidades.estabelecimento;

import com.bccog.EMM.bd.entidades.embutido.Delivery;
import com.bccog.EMM.bd.entidades.embutido.Endereco;
import com.bccog.EMM.bd.entidades.embutido.HorarioAtendimento;

/**
 * Subclasse de Estabelecimento que representa um estabelecimento com recebimento no balcão (senha)
 * @author Cristofer Oswald
 * @since 06/06/2017
 */
public class EstabelecimentoBalcao extends Estabelecimento {

    public EstabelecimentoBalcao(String nome, Endereco endereco, HorarioAtendimento horario_atendimento,
                                 float nota) {
        super(nome, endereco, horario_atendimento, nota);
    }

    public EstabelecimentoBalcao(String nome, Endereco endereco, HorarioAtendimento horario_atendimento,
                                 float nota, Delivery delivery, boolean so_delivery) {
        super(nome, endereco, horario_atendimento, nota, delivery, so_delivery);
    }
}
