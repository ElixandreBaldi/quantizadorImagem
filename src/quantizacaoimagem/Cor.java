/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantizacaoimagem;

import java.awt.Color;

/**
 *
 * @author elixandrebaldi
 */
public class Cor {
    private String red;
    private String green;
    private String blue;

    public Cor(String red, String green, String blue,String msg) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        testValues(msg);
    }
    
    private void testValues(String msg){
        int Vred = binarioParaDecimal(blue);
        int Vblue = binarioParaDecimal(red);
        int Vgreen = binarioParaDecimal(green);
        StringBuilder errorMsg = new StringBuilder();
        if (Vred>255 || Vred<0){
            errorMsg.append("RED=").append(Integer.toString(Vred)).append(",Valor String=").append(blue).append("MSG="+msg);
        }
        if (Vblue>255 || Vblue<0){
            errorMsg.append("BLUE=").append(Integer.toString(Vblue));
        }
        if (Vgreen>255 || Vgreen<0){
            errorMsg.append("GREEN=").append(Integer.toString(Vgreen));
        }
        if (errorMsg.length()>0){
            System.err.println(errorMsg);
        }
    }
    
    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }
    
    public Color getValorCor(){
        int valorRed = binarioParaDecimal(blue);
        int valorGreen = binarioParaDecimal(green);
        int valorBlue = binarioParaDecimal(red);
        Color corRetorno = new Color(valorRed,valorGreen,valorBlue);
        return(corRetorno);
    }

    @Override
    public String toString() {
        return(getValorCor().toString());
    }
    
    
    
    private static int binarioParaDecimal(String palavra) {
        int decimal = 0;
        int soma = 1;
        if (palavra==null){
            System.out.println("NULL");
        }
        for(int i = palavra.length()-1; i >= 0; i--) {
            if(palavra.charAt(i) == '1')
                decimal += soma;
            soma *=2;
        }
        
        return decimal;
    }

    public static void main(String[] args) {
        Cor c = new Cor("0","0","0","Test Construtor Cor");
        System.out.println("RESUL=");
    }  
}
