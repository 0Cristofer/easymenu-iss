package com.bccog.EMM.gui.controladores;

import com.bccog.EMM.bd.exceptions.*;
import com.bccog.EMM.gerenciadores.GerenciadorUsuarios;
import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * Controlador da tela de login
 * @author Cristofer Oswald
 * @author Guilher Quental
 * @since 29/05/2017
 */
public class LoginController implements BaseController {
    private ScreenController controller_;

    public GridPane main_pane_;
    public Button btn_entrar_;
    public Button btn_registrar_;
    public JFXTextField txtf_login_;
    public JFXPasswordField txtf_senha_;


    @FXML
    public void entrar(){
        Thread login = new Thread(() -> {
            try {
                if(GerenciadorUsuarios.logarUsuario(txtf_login_.getText(), txtf_senha_.getText())){
                    controller_.setVisibleScreen("main");
                }
                else{
                    System.out.println("erro ao logar");
                }
            } catch (NoConnectionException e) {
                controller_.setVisibleScreen("login");
                System.out.println("sem internet");
            } catch (NotImplementedErrorExcpetion e) {
                controller_.setVisibleScreen("login");
                System.out.println("erro não implementado");
                System.out.println(e.getMessage());
            } catch (InvalidPasswordException e) {
                controller_.setVisibleScreen("login");
                System.out.println("senha errada");
            } catch (InvalidEmailException e) {
                controller_.setVisibleScreen("login");
                System.out.println("email inválido");
            } catch (UserDisabledException e) {
                controller_.setVisibleScreen("login");
                System.out.println("usuário desabilitado");
            } catch (EmailNotFoundException e) {
                controller_.setVisibleScreen("login");
                System.out.println("email não encontrado");
            } catch (MissingPasswordException e) {
                controller_.setVisibleScreen("login");
                System.out.println("falta senha");
            } catch (OpNotAllowedException e) {
                controller_.setVisibleScreen("login");
                System.out.println("operação não permitida");
            } catch (EmailExistsException e) {
                controller_.setVisibleScreen("login");
                System.out.println("email já cdastrado");
            } catch (TooManyAttempsException e) {
                controller_.setVisibleScreen("login");
                System.out.println("muitas tentativas, tente mais tarde");
            }
        }
        );
        controller_.setVisibleScreen("carregando");
        login.setDaemon(true);
        login.start();
    }

    @FXML
    public void registrar(){
        controller_.setVisibleScreen("cadastro_estabelecimento");
    }

    @FXML
    public void keyboardInput(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            entrar();
        }
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
