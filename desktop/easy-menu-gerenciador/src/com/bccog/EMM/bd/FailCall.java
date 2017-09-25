package com.bccog.EMM.bd;

import com.bccog.EMM.EMM;
import com.bccog.EMM.gerenciadores.Gerenciador;
import org.jdeferred.FailCallback;
import org.joda.time.DateTime;
import org.restonfire.exceptions.FirebaseRuntimeException;

import java.util.concurrent.TimeUnit;

/**
 * Implementação da função de erro no stream
 * @author Cristofer Oswald
 * @since 23/07/17
 */
public class FailCall implements FailCallback<FirebaseRuntimeException> {
    private long stop_time_;
    private static int retries_ = 0;

    private BancoDeDados.Stream stream_;
    private Gerenciador gerenciador_;

    public FailCall(Gerenciador gerenciador,long time){
        this.gerenciador_ = gerenciador;
        stop_time_ = DateTime.now().getMillis() + time;
    }

    /**
     * Reinicia o stream. Tenta até 10 vezes
     * @param o O erro
     */
    @Override
    public void onFail(FirebaseRuntimeException o) {
        long now = DateTime.now().getMillis();

        if((now < stop_time_) && (retries_ < 10)){
            System.out.println("stream stopped, restarting");

            retries_ = retries_ + 1;
            stream_.stopStream();

            Thread start = new Thread(() ->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BancoDeDados.getInstance().startStream(EMM.getInstance().getUsuarioAtual().getToken(),
                        stream_.getPath(), stream_.getProgress(), this, (int)(stop_time_ - now - 10));
            });
            start.setDaemon(true);
            start.start();

        }
        else{
            System.out.println("failed to restart, stopping");
            gerenciador_.stopStream();
        }

    }

    public void setStream(BancoDeDados.Stream stream){
        this.stream_ = stream;
        gerenciador_.setStream(stream);

    }
    public BancoDeDados.Stream getStream(){
        return stream_;
    }
}
