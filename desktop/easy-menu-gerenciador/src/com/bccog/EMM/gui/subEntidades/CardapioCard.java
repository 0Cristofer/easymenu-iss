package com.bccog.EMM.gui.subEntidades;

import com.bccog.EMM.bd.entidades.cardapio.Cardapio;
import com.bccog.EMM.gui.GUIController;
import com.bccog.EMM.gui.controladores.CardapioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.joda.time.DateTime;

/**
 * Classe view para o card de cardápio
 * @author Diogo Almeida
 * @since 12/10/2017
 */

public class CardapioCard extends GridPane {
    private Label nome_ = new Label();
    private ImageView status_ = new ImageView();
    private Label last_edit_ = new Label();
    private Label categorias_ = new Label();
    private ColumnConstraints col1 = new ColumnConstraints();
    private ColumnConstraints col2 = new ColumnConstraints();
    private ColumnConstraints col3 = new ColumnConstraints();
    private RowConstraints row1 = new RowConstraints();
    private RowConstraints row2 = new RowConstraints();
    private RowConstraints row3 = new RowConstraints();

    public CardapioCard(Cardapio cardapio) {
        //Dados
        nome_.setText(cardapio.getNome());
        DateTime time = new DateTime(cardapio.getTime());
        last_edit_.setText("Ultima edição em " + time.toString() + ".");
        categorias_.setText(cardapio.categoriasToString());
        switch (cardapio.getStatus()){
            case ATIVO:
                //Todo Ativo
                break;
            case ANALISE:
                //Todo em analise
                break;
            case DORMINDO:
                //Todo parado
                break;
            case RECUSADO:
                status_.setImage(new Image(getClass().getResource("/icons/status_rejeitado.png").toString()));
                break;
        }

        //Configs
        setMargin(this, new Insets(10,10,10,10));
        col1.setHgrow(Priority.SOMETIMES);
        col1.setPercentWidth(10.0);
        col2.setHgrow(Priority.SOMETIMES);
        col2.setPercentWidth(80.0);
        col3.setHgrow(Priority.SOMETIMES);
        col3.setPercentWidth(10.0);
        getColumnConstraints().addAll(col1,col2,col3);

        row1.setVgrow(Priority.NEVER);
        row1.setPercentHeight(15.0);
        row2.setVgrow(Priority.NEVER);
        row2.setPercentHeight(75.0);
        row3.setVgrow(Priority.NEVER);
        row3.setPercentHeight(10.0);
        getRowConstraints().addAll(row1,row2,row3);

        nome_.setPadding(new Insets(0,10,0,0));
        last_edit_.setPadding(new Insets(0,0,5,5));
        categorias_.setPadding(new Insets(5,5,5,5));

        add(nome_, 1, 0);
        add(status_,2,0);
        add(categorias_,1,1);
        add(last_edit_, 1, 2 );

        String css = GUIController.class.getResource("/stylesheet.css").toExternalForm();
        getStylesheets().add(css);
        getStyleClass().add("card");

        this.setAlignment(Pos.CENTER);
        this.setMaxHeight(USE_COMPUTED_SIZE);
        this.setMaxWidth(USE_COMPUTED_SIZE);

        this.setOnMouseClicked(event -> {
            ((CardapioController) GUIController.getInstance().
                    getMain_controller().getController("cardapio")).setCardapio(cardapio);

            GUIController.getInstance().getMain_controller().setVisibleScreen("cardapio");
        });
    }

    public Label getNome() {
        return nome_;
    }


    public ImageView getStatus() {
        return status_;
    }

    public Label getLast_edit() {
        return last_edit_;
    }

    public Label getCategorias() {
        return categorias_;
    }
}
