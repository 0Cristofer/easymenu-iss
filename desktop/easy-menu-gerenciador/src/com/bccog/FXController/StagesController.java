package com.bccog.FXController;

import java.util.HashMap;

/**
 * Singleton que controla os stages de um programa. Deve ser utilizado em conjunto com ScreenController
 * @author Bruno Cesar
 * @author Cristofer Oswald
 * @since 23/05/17
 */
public class StagesController {
    private static StagesController instance_;

    private HashMap<String, ScreenController> screen_controllers_ = new HashMap<>();

    /**
     * @return A instância do controlador
     */
    public static synchronized StagesController getInstance(){
        if(instance_ == null){
            instance_ = new StagesController();
        }

        return instance_;
    }

    /**
     * Adiciona um screen controller ao Hash de controllers
     * @param name O nome (ou ID) do controlador
     * @param controller O controlador da tela
     */
    public void addScreenController(String name, ScreenController controller){
        screen_controllers_.put(name, controller);
    }

    /**
     * Obtém um controlador dado seu nome (ID)
     * @param name O ID do controlador
     * @return O controlador requisitado ou null se não foi achado
     */
    public ScreenController getScreenController(String name){
        return screen_controllers_.get(name);
    }

    private StagesController(){

    }
}
