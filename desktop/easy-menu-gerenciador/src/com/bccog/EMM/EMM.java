package com.bccog.EMM;

import com.bccog.EMM.bd.entidades.usuario.Usuario;
import com.bccog.EMM.gerenciadores.GerenciadorCardapios;
import com.bccog.EMM.gerenciadores.GerenciadorCategoria;
import com.bccog.EMM.gerenciadores.GerenciadorPedidos;
import com.bccog.EMM.gerenciadores.GerenciadorProdutos;
import com.bccog.EMM.gui.GUIController;
import javafx.stage.Stage;

/**
 * Classe principal da aplicação que controla e inicia os sistemas
 * @author Cristofer Oswald
 * @since 24/05/2017
 */
public class EMM {
    private static EMM instance_ = null;
    private Stage main_stage_;
    private Usuario usuario_atual_ = null;

    /**
     * @return A instância do singleton
     */
    public static EMM getInstance() {
        if(instance_ == null){
            instance_ = new EMM();
        }
        return instance_;
    }

    /**
     * Carrega os componentes principais do sistema e o inicia
     * @param main_stage Stage inicial necessário para a aplicação
     */
    void start(Stage main_stage) throws Exception {
        main_stage_ = main_stage;

        initSystems();

        GUIController.getInstance().showGUI();
    }

    /**
     * Inicializa os sistemas iniciais (GUI, BD, etc)
     * @throws Exception Em caso de erro em alguma das partes
     */
    private void initSystems() throws Exception{
        GUIController.getInstance().guiInit(main_stage_);
    }

    /**
     * @return O usuário da sessão atual
     */
    public Usuario getUsuarioAtual() {
        return usuario_atual_;
    }

    /**
     * Configura o usuário atual
     * @param usuario_atual O usuário
     */
    public void setUsuarioAtual(Usuario usuario_atual) {
        this.usuario_atual_ = usuario_atual;
    }

    /**
     * Função executada quando a aplicação é encerrada
     */
    void stop(){
    }

    /**
     * Construtor privado sem argumentos
     */
    private EMM() {
    }
}
