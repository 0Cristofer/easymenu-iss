package com.bccog.EMM.gui;

import com.bccog.FXController.ScreenController;
import com.bccog.FXController.StagesController;
import javafx.stage.Stage;

import java.net.URISyntaxException;

/**
 * Singleton responsável por executar serviços relacionados a GUI
 * @author Cristofer Oswald
 * @author Bruno Cesar
 * @since 21/03/17
 */
public class GUIController {
    private static GUIController instance_ = new GUIController();
    private ScreenController main_controller;

    public ScreenController getMain_controller() {
        return main_controller;
    }

    /**
     * @return A instância do singleton
     */
    public static GUIController getInstance() {
        return instance_;
    }

    /**
     * Inicializa o controlador geral da GUI e carrega as telas
     * @param main_stage O Stage principal dado pelo Application
     */
    public void guiInit(Stage main_stage) throws URISyntaxException {
        //Cria um controlador para cada janela
        main_controller = new ScreenController(main_stage, true);
        //Adiciona os controladores ao controlador de janelas
        StagesController.getInstance().addScreenController("main", main_controller);

        main_controller.loadScreen("main",
                GUIController.class.getResource("/main.fxml"));
        main_controller.loadScreen("cadastro_estabelecimento",
                GUIController.class.getResource("/cadastro_estabelecimento.fxml"));
        main_controller.loadScreen("cadastro_produto",
                GUIController.class.getResource("/cadastro_produto.fxml"));
        main_controller.loadScreen("login",
                GUIController.class.getResource("/login.fxml"));
        main_controller.loadScreen("produtos",
                GUIController.class.getResource("/produtos.fxml"));
        main_controller.loadScreen("info_estabelecimento",
                GUIController.class.getResource("/info_estabelecimento.fxml"));
        main_controller.loadScreen("carregando",
                GUIController.class.getResource("/splashscreen.fxml"));

        main_controller.setInitialScreen("login");
    }

    /**
     * Mostra a tela principal da GUI
     */
    public void showGUI(){
        StagesController.getInstance().getScreenController("main").showScreen();
    }


    /**
     * Contrutor privado sem argumentos
     */
    private GUIController() {
    }
}
