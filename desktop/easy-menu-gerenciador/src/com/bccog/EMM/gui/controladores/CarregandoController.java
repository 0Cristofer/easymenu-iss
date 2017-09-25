package com.bccog.EMM.gui.controladores;

import com.bccog.FXController.BaseController;
import com.bccog.FXController.ScreenController;
import javafx.scene.layout.GridPane;

/**
 * Controlador da tela de carregamento
 * @author Cristofer Oswald
 * @author Guilherme Quental
 * @since 08/06/17
 */
public class CarregandoController implements BaseController {
    public GridPane main_pane_;
    private ScreenController controller_;

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
