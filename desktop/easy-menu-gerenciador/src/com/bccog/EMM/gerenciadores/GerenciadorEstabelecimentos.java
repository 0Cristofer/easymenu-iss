package com.bccog.EMM.gerenciadores;

import com.bccog.EMM.EMM;
import com.bccog.EMM.bd.BancoDeDados;
import com.bccog.EMM.bd.entidades.embutido.Delivery;
import com.bccog.EMM.bd.entidades.embutido.Endereco;
import com.bccog.EMM.bd.entidades.embutido.HorarioAtendimento;
import com.bccog.EMM.bd.entidades.estabelecimento.Estabelecimento;
import com.bccog.EMM.bd.entidades.estabelecimento.EstabelecimentoBalcao;
import com.bccog.EMM.bd.entidades.estabelecimento.EstabelecimentoMesa;
import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.bd.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Gerenciador de estabelecimentos
 * @author Cristofer Oswald
 * @since 30/05/2017
 */
public class GerenciadorEstabelecimentos {

    /**
     * Cria um estabelecimento sem delivery, salva-o e liga-o ao usuário criado
     * @param nome Nome do estabelecimento
     * @param rua Rua do estabelecimento
     * @param numero Numero do estabelecimento
     * @param cep CEP do estabelecimento
     * @param complemento Complemento do estabelecimento
     * @param referencia Referencia do estabelecimento
     * @param horario_inicio Horario de inicio do trabalho
     * @param horario_fim Horario de fim de trabalho
     * @param tipo Tipo do estabelecimento
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     */
    public static void criaEstabelecimento(String nome, String rua, String numero, String cep,
                                           String complemento, String referencia, String horario_inicio,
                                           String horario_fim, String tipo) throws NoConnectionException,
            ForbiddenException, BadRequestException, NotImplementedErrorExcpetion,
            InternalServerErrorException, NotFoundException, NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        Estabelecimento est;
        JsonParser parser = new JsonParser();
        JsonElement element;

        Endereco endereco = new Endereco(cep, rua, numero, complemento, referencia);
        HorarioAtendimento horaroio = new HorarioAtendimento(horario_inicio, horario_fim);
        if(tipo.equals("balcao")){
            est = new EstabelecimentoBalcao(nome, endereco, horaroio, 4);
            element = parser.parse(new Gson().toJson(est, EstabelecimentoBalcao.class));
        }
        else{
            est = new EstabelecimentoMesa(nome, endereco, horaroio, 4);
            element = parser.parse(new Gson().toJson(est, EstabelecimentoMesa.class));
        }

        BancoDeDados.getInstance().putData(usuario.getToken(), "estabelecimento/" + usuario.getId(), element);

        EMM.getInstance().getUsuarioAtual().setEstabelecimento(est);
    }

    /**
     * Cria um estabelecimento com delivery, salva-o e liga-o ao usuário criado
     * @param nome Nome do estabelecimento
     * @param rua Rua do estabelecimento
     * @param numero Numero do estabelecimento
     * @param cep CEP do estabelecimento
     * @param complemento Complemento do estabelecimento
     * @param referencia Referencia do estabelecimento
     * @param horario_inicio Horario de inicio do trabalho
     * @param horario_fim Horario de fim de trabalho
     * @param preco_min Preço mínimo para entrega
     * @param taxa_entrega Taxa de entrega
     * @param frete_gratis Preço mínimo para frete gratis
     * @param horario_entrega Horário de início de entrega
     * @param media_demora Média de tempo de demora
     * @param so_delivery Se é apenas delivery
     * @param tipo O tipo do estabelecimento
     * @throws NoConnectionException Sem internet
     * @throws NotImplementedErrorExcpetion Erro não implementado
     * @throws NotAuthorizedException Não auorizado
     * @throws InternalServerErrorException Erro interno do servidor
     * @throws ForbiddenException Não encontrado
     * @throws BadRequestException Request errado
     * @throws NotFoundException Não encontrado
     * @throws NumberFormatException Erro ao ler preços
     */
    public static void criaEstabelecimento(String nome, String rua, String numero, String cep,
                                           String complemento, String referencia, String horario_inicio,
                                           String horario_fim, String preco_min, String taxa_entrega, String frete_gratis,
                                           String horario_entrega, String media_demora, boolean so_delivery,
                                           String tipo) throws NoConnectionException,
            ForbiddenException, BadRequestException, NotImplementedErrorExcpetion,
            InternalServerErrorException, NotFoundException, NotAuthorizedException, NumberFormatException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        Estabelecimento est;
        JsonParser parser = new JsonParser();
        JsonElement element;

        Endereco endereco = new Endereco(cep, rua, numero, complemento, referencia);
        Delivery delivery = new Delivery(Float.valueOf(preco_min), Float.valueOf(taxa_entrega), Float.valueOf(frete_gratis),
                horario_entrega, media_demora);
        HorarioAtendimento horaroio = new HorarioAtendimento(horario_inicio, horario_fim);

        if(tipo.equals("balcao")){
            est = new EstabelecimentoBalcao(nome, endereco, horaroio, 4, delivery, so_delivery);
            element = parser.parse(new Gson().toJson(est, EstabelecimentoBalcao.class));
        }
        else{
            est = new EstabelecimentoMesa(nome, endereco, horaroio, 4, delivery, so_delivery);
            element = parser.parse(new Gson().toJson(est, EstabelecimentoMesa.class));
        }

        BancoDeDados.getInstance().putData(usuario.getToken(), "estabelecimento/" + usuario.getId(), element);

        EMM.getInstance().getUsuarioAtual().setEstabelecimento(est);
    }

    public static Estabelecimento getEstabelecimento() throws NoConnectionException, ForbiddenException,
            BadRequestException, NotImplementedErrorExcpetion, InternalServerErrorException, NotFoundException,
            NotAuthorizedException {
        Usuario usuario = EMM.getInstance().getUsuarioAtual();

        JsonElement element;
        element = BancoDeDados.getInstance().getData(usuario.getToken(),
                "estabelecimento/"+ usuario.getId());

        Estabelecimento estabelecimento = new Gson().fromJson(element.toString(), EstabelecimentoMesa.class);
        estabelecimento.setId(usuario.getId());
        return estabelecimento;
    }
}
