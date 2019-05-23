/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cronometro.contador;

/**
 *
 * @author João Hudson
 */
public class Cronometro {
    
    private int minutos;
    private int segundos;
    private int miliSegundos;
    private boolean contando;
    private boolean iniciou;
    private boolean fim;
    private long tempoInicial;
    
    public Cronometro()
    {
        minutos = 0;
        segundos = 0;
        miliSegundos = 0;
        contando = false;
        iniciou = false;
        fim = false;
        
    }
    
    public boolean iniciou()
    {
        return iniciou;
    }
    
    public boolean parado()
    {
        return !contando;
    }
    
    public String obterDisplay()
    {
        int minutos = this.minutos;
        int segundos = this.segundos;
        int miliSegundos = this.miliSegundos;
        
        String displayMinutos = (minutos < 10) ? ("0" + minutos) : ("" + minutos);
        String displaySegundos = (segundos < 10) ? ("0" + segundos) : ("" + segundos);
        String displayMiliSegundos = (miliSegundos < 100) ? ("0" + miliSegundos) : ("" + miliSegundos);
        displayMiliSegundos = (miliSegundos < 10) ? ("0" + displayMiliSegundos) :
                                ("" + displayMiliSegundos);
        
        return displayMinutos + ":" + displaySegundos + ":" + displayMiliSegundos;
    }
    
    public void parar()
    {
        contando = false;
    }
    
    public void play()
    {
        if(contando || fim)
        {
            return;
        }
        
        iniciar();
        contando = true;
        
        new Thread(){
            public void run()
            {
                contar();
            }
        }.start();
    }
    
    private void contar()
    {
        while(contando)
        {
            long tempo = System.currentTimeMillis() - tempoInicial;
            
            minutos      = (int)((tempo/60_000) % 60);
            segundos     = (int)((tempo/1000) % 60);
            miliSegundos = (int)(tempo % 1000);
            
            if(tempo >= 3600_000)
            {
                contando = false;
                fim = true;
            }
        }
    }
    
    private void iniciar()
    {
        if(!iniciou)
        {
            iniciou = true;
            tempoInicial = System.currentTimeMillis();
        }
    }
}
