package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorEstabelecimentos;
import com.bccog.EMM.gerenciadores.GerenciadorUsuarios;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.*;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * Controlador da visão do cadastro do estabelecimento
 * @author Cristofer Oswald
 * @author Guilherme Quental
 * @since 04/06/2017
 */
public class CadastroEstabelecimentoController implements BaseController {
    public JFXTextField txt_nome_est;
    public JFXButton btn_voltar_;
    public JFXTextField txt_complemento_;
    public JFXTextField txt_cep_;
    public JFXTextField txt_referencia_;
    public JFXTextField txt_rua_;
    public JFXTextField txt_numero_;
    public JFXTextField txt_hora_fim_;
    public JFXTextField txt_hora_abertura_;
    public JFXCheckBox check_delivery_;
    public JFXTextField txt_taxa_gratis_;
    public JFXTextField txt_preco_min_;
    public JFXTextField txt_horario_entrega_;
    public JFXTextField txt_tempo_medio_;
    public JFXTextField txt_taxa_entrega_;
    public ToggleGroup tipo_estabelecimento;
    public JFXRadioButton radio_balcao_;
    public JFXRadioButton radio_mesa_;
    public JFXRadioButton radio_delivery_;
    private ScreenController controller_;

    public GridPane main_pane_;
    public Button btn_registrar_;
    public JFXTextField txt_login_;
    public JFXPasswordField txt_senha_;


    public void registrar(){
        try {
            if (GerenciadorUsuarios.criarUsuario(txt_login_.getText(), txt_senha_.getText())){
                if(check_delivery_.isSelected()){
                    if(radio_balcao_.isSelected()){
                        GerenciadorEstabelecimentos.criaEstabelecimento(txt_nome_est.getText(), txt_rua_.getText(),
                                txt_numero_.getText(), txt_cep_.getText(), txt_complemento_.getText(), txt_referencia_.getText(),
                                txt_hora_abertura_.getText(), txt_hora_fim_.getText(),
                                txt_preco_min_.getText(), txt_taxa_entrega_.getText(), txt_taxa_gratis_.getText(),
                                txt_horario_entrega_.getText(), txt_tempo_medio_.getText(), radio_delivery_.isSelected(),
                                "balcao");
                    }
                    else if(radio_mesa_.isSelected()){
                        GerenciadorEstabelecimentos.criaEstabelecimento(txt_nome_est.getText(), txt_rua_.getText(),
                                txt_numero_.getText(), txt_cep_.getText(), txt_complemento_.getText(), txt_referencia_.getText(),
                                txt_hora_abertura_.getText(), txt_hora_fim_.getText(),
                                txt_preco_min_.getText(), txt_taxa_entrega_.getText(), txt_taxa_gratis_.getText(),
                                txt_horario_entrega_.getText(), txt_tempo_medio_.getText(), radio_delivery_.isSelected(),
                                "mesa");
                    }
                    else if(radio_delivery_.isSelected()){
                        GerenciadorEstabelecimentos.criaEstabelecimento(txt_nome_est.getText(), txt_rua_.getText(),
                                txt_numero_.getText(), txt_cep_.getText(), txt_complemento_.getText(), txt_referencia_.getText(),
                                txt_hora_abertura_.getText(), txt_hora_fim_.getText(),
                                txt_preco_min_.getText(), txt_taxa_entrega_.getText(), txt_taxa_gratis_.getText(),
                                txt_horario_entrega_.getText(), txt_tempo_medio_.getText(), radio_delivery_.isSelected(),
                                "balcao");
                    }
                    else{
                        System.out.println("sem seleção");
                        throw new RuntimeException();
                    }
                }
                else{
                    if(radio_balcao_.isSelected()){
                        GerenciadorEstabelecimentos.criaEstabelecimento(txt_nome_est.getText(), txt_rua_.getText(),
                                txt_numero_.getText(), txt_cep_.getText(), txt_complemento_.getText(), txt_referencia_.getText(),
                                txt_hora_abertura_.getText(), txt_hora_fim_.getText(), "balcao");
                    }
                    else if(radio_mesa_.isSelected()){
                        GerenciadorEstabelecimentos.criaEstabelecimento(txt_nome_est.getText(), txt_rua_.getText(),
                                txt_numero_.getText(), txt_cep_.getText(), txt_complemento_.getText(), txt_referencia_.getText(),
                                txt_hora_abertura_.getText(), txt_hora_fim_.getText(), "mesa");
                    }
                }

                controller_.setVisibleScreen("main");
            }
            else{
                System.out.println("erro");
            }
        } catch (NoConnectionException e) {
            System.out.println("sem internet");
        } catch (NotImplementedErrorExcpetion e) {
            System.out.println("erro não implementado");
            System.out.println(e.getMessage());
        } catch (InvalidPasswordException e) {
            System.out.println("senha errada");
        } catch (InvalidEmailException e) {
            System.out.println("email inválido");
        } catch (UserDisabledException e) {
            System.out.println("usuário desabilitado");
        } catch (EmailNotFoundException e) {
            System.out.println("email não encontrado");
        } catch (MissingPasswordException e) {
            System.out.println("falta senha");
        } catch (OpNotAllowedException e) {
            System.out.println("operação não permitida");
        } catch (EmailExistsException e) {
            System.out.println("email já existe");
        } catch (TooManyAttempsException e) {
            System.out.println("muitas requisições, tente mais tarde");
        } catch (ForbiddenException | BadRequestException | InternalServerErrorException |
                NotFoundException | NotAuthorizedException e) {
            e.printStackTrace();
        }
    }

    public void voltar(){
        controller_.showScreen(ScreenController.PREVIOUS_SCREEN);
    }

    @Override
    public void atualizar() {

    }

    @Override
    public void init() {

    }

    @Override
    public void setMainController(ScreenController controller) {
        controller_ = controller;
    }

    @Override
    public double getWidth() {
        return main_pane_.getPrefWidth();
    }

    @Override
    public double getHeight() {
        return main_pane_.getPrefHeight();
    }
}
