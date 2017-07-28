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
        //System.out.println("Entrada ="+red+","+green+","+blue);
        String zeros = "";
        int dif = 0;
        dif = 8-red.length();
        for (int i=0;i<dif;i++){
            zeros = zeros+"0";
        }
        this.red = zeros+red;
        zeros = "";
        dif = 0;
        dif = 8-green.length();
        for (int i=0;i<dif;i++){
            zeros = zeros+"0";
        }
        this.green = zeros+green;
        zeros = "";
        dif = 0;
        dif = 8-blue.length();
        for (int i=0;i<dif;i++){
            zeros = zeros+"0";
        }
        this.blue = zeros+blue;
        //System.out.println("Saida ="+this.red+","+this.green+","+this.blue);
        testValues(msg);
    }
    
    public Cor subtrair(Cor cor){
        Cor corRetorno;
        int newRed = Math.abs(binarioParaDecimal(red)-binarioParaDecimal(cor.red));
        String snewRed = Integer.toBinaryString(newRed);
        int newGreen = Math.abs(binarioParaDecimal(green)-binarioParaDecimal(cor.green));
        String snewGreen = Integer.toBinaryString(newGreen);
        int newBlue = Math.abs(binarioParaDecimal(blue)-binarioParaDecimal(cor.blue));
        String snewBlue = Integer.toBinaryString(newBlue);
        corRetorno = new Cor(snewRed,snewGreen,snewBlue,"sub");
        return(corRetorno);
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
        int valorRed = binarioParaDecimal(red);
        int valorGreen = binarioParaDecimal(green);
        int valorBlue = binarioParaDecimal(blue);
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
        Cor c = new Cor("101","111","010","Test Construtor Cor");
        Cor c2 = new Cor("1101","1011","1010","Test Construtor Cor");
        Cor c3 = c.subtrair(c2);
        System.out.println("c="+c+",c2="+c2+",c3="+c3);
    }  
}
