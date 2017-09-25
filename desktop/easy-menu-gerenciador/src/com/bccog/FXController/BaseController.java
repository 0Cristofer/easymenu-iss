package com.bccog.FXController;

/**
 * Interface básica para qualquer controlador de cena gerenciado por um controlador geral
 * @author Bruno Cesar
 * @author Cristofer Oswald
 * @since 23/05/17
 */
public interface BaseController {

    /**
     * É chamada toda vez que a tela for mostrada e pode ser utilizada para efetuar atualizações na tela
     */
    void atualizar();

    /**
     * É chamada somente na inicialização do sistema
     */
    void init();

    /**
     * Deve ser implementada tendo em vista a configuração do objeto controlador dessa tela
     * @param controller A instância do controlador para essa tela
     */
    void setMainController(ScreenController controller);

    /**
     * Deve retornar a largura da tela, calculada pelo seu maior pane
     * @return A largura
     */
    double getWidth();

    /**
     * Deve retornar a altura da tela, calculada pelo seu maior pane
     * @return A altura
     */
    double getHeight();
}
