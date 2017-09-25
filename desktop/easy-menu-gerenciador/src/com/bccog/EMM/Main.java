package com.bccog.EMM;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe que inicia o programa
 * @author Cristofer Oswald
 * @since 23/05/2017
 */

public class Main extends Application {

    @Override
    public void start(Stage primary_stage) throws Exception{
        EMM.getInstance().start(primary_stage);
    }

    @Override
    public void stop(){
        EMM.getInstance().stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
