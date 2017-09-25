package com.bccog.easymenu.entidades.produto;

/**
 * Filho de produto que representa um produto com diferentes tamanhos
 * @author Cristofer Oswald
 * @author Bruno Cesar
 * @since 01/06/2017
 */
public class ProdutoComTamanho extends Produto {
    private float preco_p_;
    private float preco_m_;
    private float preco_g_;

    public ProdutoComTamanho(){
        super();
    }

    /**
     * Construtor Padrão com 3 tamanhos diferentes P, M e G
     * @param nome Nome do produto
     * @param descricao Descrição breve
     * @param tags Lista com as tags do produto
     * @param foto O caminho para a foto
     * @param preco_p Preço do tamanho pequeno
     * @param preco_m Preço do tamanho médio
     * @param preco_g Preço do tamanho grande
     */
    public ProdutoComTamanho(String nome, String descricao, Tag[] tags, String foto,
                             float preco_p, float preco_m, float preco_g){
        super(nome, descricao, tags, foto);
        setPrecoP(preco_p);
        setPrecoM(preco_m);
        setPrecoG(preco_g);
    }

    public float getPrecoP() {
        return preco_p_;
    }

    public float getPrecoM() {
        return preco_m_;
    }

    public float getPrecoG() {
        return preco_g_;
    }

    public void setPrecoP(float preco_p) {
        this.preco_p_ = preco_p;
    }

    public void setPrecoM(float preco_m) {
        this.preco_m_ = preco_m;
    }

    public void setPrecoG(float preco_g) {
        this.preco_g_ = preco_g;
    }

    public String toString(){
        String textTags = "";

        if (getTags() != null) {
            for (Tag t : getTags()) {
                textTags = textTags.concat(t.getNome() + ", ");
            }
        }

        return getNome() + " : " + textTags + "\nP=" + Float.toString(getPrecoP()) +
                " M=" + Float.toString(getPrecoM()) + " G=" + Float.toString(getPrecoG()) +
                "\nimage_url: " + getFotos() + "\nDescricao: " + getDescricao();
    }
}
