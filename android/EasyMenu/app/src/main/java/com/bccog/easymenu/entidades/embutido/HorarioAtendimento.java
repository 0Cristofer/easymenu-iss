package com.bccog.easymenu.entidades.embutido;

/**
 * Utilizada para configurar o horario de atendimento de estabelecimentos
 * @author Cristofer Oswald
 * @since 06/06/2017
 */
public class HorarioAtendimento {
    private String hora_abertura_;
    private String hora_fim_;

    public HorarioAtendimento(){

    }

    public HorarioAtendimento(String hora_abertura, String hora_fim){
        setHoraAbertura(hora_abertura);
        setHoraFim(hora_fim);
    }

    public String getHoraAbertura() {
        return hora_abertura_;
    }

    public void setHoraAbertura(String hora_abertura) {
        this.hora_abertura_ = hora_abertura;
    }

    public String getHoraFim() {
        return hora_fim_;
    }

    public void setHoraFim(String hora_fim) {
        this.hora_fim_ = hora_fim;
    }

    @Override
    public String toString() {
        return "Horario atendimento:\n" + "hora abertura: " + hora_abertura_ + "\nhora fim: " + hora_fim_;
    }
}

