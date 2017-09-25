package com.bccog.easymenu.entidades.embutido;

/**
 * Representa um endereço no mundo real
 * @author Cristofer Oswald
 * @since 06/06/2017
 */
public class Endereco {
    private String cep_;
    private String rua_;
    private String numero_;
    private String complemento_;
    private String referencia_;

    public Endereco(){
    }

    public Endereco(String cep, String rua, String numero, String complemento, String referencia){
        setCep(cep);
        setRua(rua);
        setNumero(numero);
        setComplemento(complemento);
        setReferencia(referencia);
    }

    public String getCep() {
        return cep_;
    }

    public void setCep(String cep) {
        this.cep_ = cep;
    }

    public String getRua() {
        return rua_;
    }

    public void setRua(String rua) {
        this.rua_ = rua;
    }

    public String getNumero() {
        return numero_;
    }

    public void setNumero(String numero) {
        this.numero_ = numero;
    }

    public String getComplemento() {
        return complemento_;
    }

    public void setComplemento(String complemento) {
        this.complemento_ = complemento;
    }

    public String getReferencia() {
        return referencia_;
    }

    public void setReferencia(String referencia) {
        this.referencia_ = referencia;
    }

    @Override
    public String toString() {
        return "Endereço:\n" + "CEP: " + cep_ + "\nRua: " + rua_ + "\nnumero:" + numero_ + "\ncomplemento: " + complemento_
                + "\nreferencia: " + referencia_;
    }
}
