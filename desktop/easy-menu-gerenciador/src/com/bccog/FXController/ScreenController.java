package com.bccog.FXController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

/**
 * Um ScreenControler deve ser utiizado para gerenciar todas as telas de uma janela, cada janela
 * deve ter um ScreenController. Extende StackPane, que é um pane básico, já que este controlador deve ser
 * visto como uma Scene pelo Stage e esta Scene irá controlar as outras
 * @author Bruno Cesar
 * @author Cristofer Oswald
 * @since 23/05/17
 */
public class ScreenController{
    public static final byte INITIAL_SCREEN = 0;
    public static final byte LAST_SCREEN = 1;
    public static final byte PREVIOUS_SCREEN = 2;

    private Stage stage_;
    private double height_ = 0;
    private double width_ = 0;

    private HashMap<String, Parent> screens_ = new HashMap<>();
    private HashMap<String, BaseController> controllers_ = new HashMap<>();
    private Stack<String> previous_screen_ = new Stack<>();

    private String initial_screen_;
    private String last_screen_;
    private byte mode_;

    /**
     * Instancia um stage e configura o controlador
     */
    public ScreenController(){
        super();
        stage_ = new Stage();
    }

    /**
     * Instancia um stage e configura o controlador
     * @param resize Se o stage deve ser resizeble
     */
    public ScreenController(boolean resize, String icon){
        super();
        stage_ = new Stage();
        stage_.setResizable(resize);
        stage_.getIcons().add(new Image(icon));
    }

    /**
     * Recebe um stage a qual este controlador será ligado e o configura
     * @param stage O stage da tela
     */
    public ScreenController(Stage stage){
        super();
        stage_ = stage;
    }

    /**
     * Recebe um stage a qual este controlador será ligado e o configura
     * @param stage O stage da tela
     * @param resize Se o stage deve ser resizeble
     */
    public ScreenController(Stage stage, boolean resize){
        super();
        stage_ = stage;
        stage_.setResizable(resize);
    }

    /**
     * Recebe um stage a qual este controlador será ligado e o configura
     * @param stage O stage da tela
     * @param resize Se o stage deve ser resizeble
     * @param icon Caminho para o ícone que será mostrado no dock do OS
     */
    public ScreenController(Stage stage, boolean resize, String icon){
        super();
        stage_ = stage;
        stage_.setResizable(resize);
        stage_.getIcons().add(new Image(icon));
    }

    /**
     * Mostra a tela controlada
     * @param mode O modo que deve ser mostrada. Deve ser ScreenController.INITIAL_SCREEN
     * se a tela a ser mostrada deve ser a inicial, ScreenController.LAST_SCREEN se
     * deve ser a última tela visível ou ScreenController.PREVIOUS_SCREEN se deve ser
     * a tela anterior.
     * @return True se o modo for válido, se não, false
     */
    public boolean showScreen(byte mode){
        String screen = initial_screen_;
        String previous = previous_screen_.peek();

        mode_ = mode;

        switch (mode){
            case INITIAL_SCREEN:
                break;
            case LAST_SCREEN:
                if(last_screen_ != null){
                    screen = last_screen_;
                }
                break;
            case PREVIOUS_SCREEN:
                if(previous != null){
                    screen = previous_screen_.pop();
                }
                break;
            default:
                System.out.println("Modo incorreto");
                return false;
        }

        setVisibleScreen(screen);
        stage_.show();

        return true;
    }

    /**
     * Mostra a tela inicial
     */
    public void showScreen(){
        setVisibleScreen(initial_screen_);
        stage_.show();
    }

    /**
     * Configura qual tela será visivel dado o seu nome.
     * @param name O nome da tela a ser mostrada
     * @return True se o nome da tela é válido, se não, false
     */
    public boolean setVisibleScreen(String name){
        Parent screen = getScreen(name);
        BaseController controller = getController(name);

        if(screen != null){
            if(mode_ != PREVIOUS_SCREEN){
                previous_screen_.push(last_screen_);
            }
            else{
                mode_ = 0;
            }

            if(stage_.getScene() == null){
                stage_.setScene(new Scene(screen));
            }
            else{
                stage_.getScene().setRoot(screen);
            }

            if((height_ == 0) || (width_ == 0)){
                stage_.setHeight(controller.getHeight());
                stage_.setWidth(controller.getWidth());
            }
            else {
                stage_.setHeight(height_);
                stage_.setWidth(width_);
            }

            controller.atualizar();

            last_screen_ = name;

            return true;
        }
        else{
            System.out.println("Tela " + name + " não carregada");
            return false;
        }

    }

    /**
     * Configura a tela inicial. A tela inicial é a tela que é mostrada
     * quando o controlador é requisitado em modo inicial
     * @param initial_screen O ID da tela inicial
     * @return True se a tela existir, se não, false
     */
    public boolean setInitialScreen(String initial_screen){
        if(screens_.get(initial_screen) != null){
            initial_screen_ = initial_screen;
            return true;
        }
        else{
            System.out.println("Tela " + initial_screen + " inexistente");
            return false;
        }
    }

    /**
     * Carrega a tela e a coloca no HashMap
     * @param name Nome da tela que será utilizado como ID no Hash
     * @param path Caminho para o FXML da tela
     * @return True se foi possível carregar a tela, se não, false
     */
    public boolean loadScreen(String name, URL path){
        try {
            FXMLLoader loader = new FXMLLoader(path);
            Parent screen = loader.load();
            BaseController screen_controller = loader.getController();

            screen_controller.setMainController(this);

            addScreen(name, screen, screen_controller);

            screen_controller.init();
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao carregar FXML");
            e.printStackTrace();

            return false;
        }
    }

    /**
     * Descarrega uma tela da lista de telas
     * @param name O nome (ID) a ser removido
     * @return True se a tela foi removida, se não, false
     */
    public boolean unloadScreen(String name){
        if(removeScreen(name)){
            return true;
        }
        else{
            System.out.println("Tela " + name + " inexistente");
            return false;
        }
    }

    /**
     * Adiciona uma tela dado seu controlador e nome (ID)
     * @param name Nome da tela que será utlizado como ID
     * @param screen Controlador da tela
     */
    private void addScreen(String name, Parent screen, BaseController controller){
        screens_.put(name, screen);
        controllers_.put(name, controller);
    }

    /**
     * Retorna uma tela dado sua ID (nome)
     * @param name O nome da tela
     * @return A tela
     */
    private Parent getScreen(String name){
        return screens_.get(name);
    }

    /**
     * Retorna o controlador de uma tela dado sua ID (nome)
     * @param name O nome da tela
     * @return O controlador
     */
    public BaseController getController(String name){
        return controllers_.get(name);
    }

    /**
     * Remove uma tela da tabela Hash
     * @param name Nome da tela (ID)
     * @return True se a operação foi sucedida, se não, false
     */
    private boolean removeScreen(String name){
        return screens_.remove(name) != null;
    }

    /**
     * @return O stage ao qual este controlador está ligado
     */
    public Stage getStage(){
        return stage_;
    }

}
