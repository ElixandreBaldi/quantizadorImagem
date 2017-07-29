/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantizacaoimagem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import static quantizacaoimagem.QuantizacaoImagem.binarioParaDecimal;

/**
 *
 * @author elixandrebaldi
 */
public class Imagem {
    private String type;
    private String size;
    private String reserved;
    private String reserved2;
    private String offsetBits;
    
    private String biSize;
    private String biWidth;
    private String biHeight;
    private String biPlanes;
    private String biBitCount;
    private String biCompression;
    private String biSizeImage;
    private String biXPelsPerMeter;
    private String biYPelsPerMeter;
    private String biClrUsed;
    private String biClrImportant;    
    
    private ArrayList<Cor> pixel;

    private FileOutputStream escrever;
    
    public Imagem() {
        pixel = new ArrayList();
    }    
    
    public ArrayList<Cor> getPixel() {
        return pixel;
    }
    
    public void subtrair(Imagem outraimagem){
        ArrayList<Cor> newCorArray = new ArrayList<>();
        for (int i=0;i<pixel.size();i++){
            Cor cor = pixel.get(i).subtrair( outraimagem.getPixel().get(i) );
            newCorArray.add(cor);
        }
        this.pixel = newCorArray;
    }

    public void setPixel(String red, String green, String blue,String Msg) {
        pixel.add(new Cor(red, green, blue,Msg));
    }
    
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String Size) {
        this.size = Size;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getOffsetBits() {
        return offsetBits;
    }

    public void setOffsetBits(String offsetBits) {
        this.offsetBits = offsetBits;
    }

    public String getBiSize() {
        return biSize;
    }

    public void setBiSize(String biSize) {
        this.biSize = biSize;
    }

    public String getBiWidth() {
        return biWidth;
    }

    public void setBiWidth(String biWeidth) {
        this.biWidth = biWeidth;
    }

    public String getBiHeight() {
        return biHeight;
    }

    public void setBiHeight(String biHeight) {
        this.biHeight = biHeight;
    }

    public String getBiPlanes() {
        return biPlanes;
    }

    public void setBiPlanes(String biPlanes) {
        this.biPlanes = biPlanes;
    }

    public String getBiBitCount() {
        return biBitCount;
    }

    public void setBiBitCount(String biBitCount) {
        this.biBitCount = biBitCount;
    }

    public String getBiCompression() {
        return biCompression;
    }

    public void setBiCompression(String biCompression) {
        this.biCompression = biCompression;
    }

    public String getBiSizeImage() {
        return biSizeImage;
    }

    public void setBiSizeImage(String biSizeImage) {
        this.biSizeImage = biSizeImage;
    }

    public String getBiXPelsPerMeter() {
        return biXPelsPerMeter;
    }

    public void setBiXPelsPerMeter(String biXPelsPerMeter) {
        this.biXPelsPerMeter = biXPelsPerMeter;
    }

    public String getBiYPelsPerMeter() {
        return biYPelsPerMeter;
    }

    public void setBiYPelsPerMeter(String biYPelsPerMeter) {
        this.biYPelsPerMeter = biYPelsPerMeter;
    }

    public String getBiClrUsed() {
        return biClrUsed;
    }

    public void setBiClrUsed(String biClrUsed) {
        this.biClrUsed = biClrUsed;
    }

    public String getBiClrImportant() {
        return biClrImportant;
    }

    public void setBiClrImportant(String biClrImportant) {
        this.biClrImportant = biClrImportant;
    }
    
    public void escreverNovaImagem16Bits(FileOutputStream escrever) throws IOException {
        escreverCabecalhos(escrever,"0000000000010000");
        escreverCores16bits(escrever);
    }
    
    public void escreverNovaImagem24Bits(FileOutputStream escrever)throws IOException {
        escreverCabecalhos(escrever,"0000000000011000");
        escreverCores24bits(escrever);
    }
    
    private byte byteStringToByte(String bits) {
        return (byte) Integer.parseInt(bits, 2);
    }
    
    public String quebrarString (String original, int parte) {
        String retorno = "";
        int j = 0;
        if(original.length() == 16 && (parte == 3 || parte == 4))
            return "";
        
        if(parte == 4)
            j = 0;        
        else if(parte == 3)
            j = 8;                    
        else if(parte == 2) { 
            if(original.length() == 16)
                j = 0;
            else
                j = 16;
        }
        else if(parte == 1) {
            if(original.length() == 16)
                j = 8;
            else
                j = 24;
        }
        
        for(int i = 0; i < 8; i++) {
            retorno += original.charAt(j);
            j++;
        }
        return retorno;        
    }
    
    public void escreverCabecalhos(FileOutputStream escrever, String qtdBits) throws IOException {   
        String cabecalhoBytes[] = new String[16];
        String novoSize = "";
        if(qtdBits.equals("0000000000011000"))
            novoSize = size;
        else if(qtdBits.equals("0000000000010000")){
            int novoSize2 = (binarioParaDecimal(size) - ((binarioParaDecimal(biWidth)*binarioParaDecimal(biHeight))*8));
            novoSize = Integer.toBinaryString(novoSize2);
            while(novoSize.length() < 32)
                    novoSize = "0"+novoSize;
        } else
            System.out.println("ERRO");
        
        cabecalhoBytes[0] = type;
        cabecalhoBytes[1] = novoSize;
        cabecalhoBytes[2] = reserved;
        cabecalhoBytes[3] = reserved2;
        cabecalhoBytes[4] = offsetBits;

        cabecalhoBytes[5] = biSize;
        cabecalhoBytes[6] = biWidth;
        cabecalhoBytes[7] = biHeight;
        cabecalhoBytes[8] = biPlanes;
        cabecalhoBytes[9] = qtdBits;
        cabecalhoBytes[10] = biCompression;        
        cabecalhoBytes[11] = biSizeImage;
        cabecalhoBytes[12] = biXPelsPerMeter;
        cabecalhoBytes[13] = biYPelsPerMeter;
        cabecalhoBytes[14] = biClrUsed;
        cabecalhoBytes[15] = biClrImportant; 
        
        
        for(int i = 0; i < cabecalhoBytes.length; i++) {
            String str1 = quebrarString(cabecalhoBytes[i], 1);
            String str2 = quebrarString(cabecalhoBytes[i], 2);
            String str3 = quebrarString(cabecalhoBytes[i], 3);
            String str4 = quebrarString(cabecalhoBytes[i], 4);
            
            escrever.write(byteStringToByte(str1));
            escrever.flush();
            escrever.write(byteStringToByte(str2));
            escrever.flush();
            if(!str3.equals("")) {
                escrever.write(byteStringToByte(str3));
                escrever.flush();
                escrever.write(byteStringToByte(str4));
                escrever.flush();
            }            
        }
    }        
    
    public static String juntarCores(String pri, String seg, int escolha) {
        String strRetorno = "";
        if(escolha == 1) {
            for(int i = 0; i < 5; i++)
                strRetorno += pri.charAt(i);
            for(int i = 0; i < 3; i++)
                strRetorno += seg.charAt(i);
        } else if(escolha == 2) {
            for(int i = 3; i < 6; i++)
                strRetorno += pri.charAt(i);
            for(int i = 0; i < 5; i++)
                strRetorno += seg.charAt(i);
        }
        return strRetorno;
    }        
    
    public void escreverCores16bits(FileOutputStream escrever) throws IOException {
        int imagemWidth = binarioParaDecimal(biWidth);
        int imagemHeight = binarioParaDecimal(biHeight);
        int pixelsVazios = imagemWidth%4;
        System.out.println("pixelsVazios : "+pixelsVazios);
        int contadorDeLinha = 0;
        int contadorLinhasLidas = 0;
        
        String emptyString = "00000000";
        
        int quantiaPixels = imagemWidth*imagemHeight;
        System.out.println(pixel.size()+"="+quantiaPixels);
        
        for(int i = 0; i < pixel.size(); i++) {
            String blue = pixel.get(i).getBlue();
            String green = pixel.get(i).getGreen();
            String red = pixel.get(i).getRed();
            
           // System.out.println("red: " +red+ ", green:"+green+", blue:"+blue);
            String b1 = juntarCores(red, green, 1);
            String b2 = juntarCores(green, blue, 2);
            //System.out.println("red + green= "+b1+",green + blue= "+b2);
                        
            escrever.write(byteStringToByte(b2));            
            escrever.write(byteStringToByte(b1));           
           
            //System.out.println("writing linha");
             
            if (contadorDeLinha>=imagemWidth-1){
                //System.out.println("write espaço vazio");
                for (int j=0;j<pixelsVazios;j++){
                    escrever.write(byteStringToByte(emptyString));
                }
                contadorLinhasLidas++;
                contadorDeLinha = -1;
            }
            escrever.flush();
            contadorDeLinha = contadorDeLinha +1;
         } 
    }
    
    public void escreverCores24bits(FileOutputStream escrever) throws IOException {
        int imagemWidth = binarioParaDecimal(biWidth);
        int imagemHeight = binarioParaDecimal(biHeight);
        int pixelsVazios = imagemWidth%4;
        System.out.println("pixelsVazios : "+pixelsVazios);
        int contadorDeLinha = 0;
        int contadorLinhasLidas = 0;
        
        String emptyString = "00000000";
        
        int quantiaPixels = imagemWidth*imagemHeight;
        System.out.println(pixel.size()+"="+quantiaPixels);
        
        for(int i = 0; i < pixel.size(); i++) {
            String blue = pixel.get(i).getBlue();
            String green = pixel.get(i).getGreen();
            String red = pixel.get(i).getRed();

            escrever.write(byteStringToByte(blue));           
            escrever.write(byteStringToByte(green));
            escrever.write(byteStringToByte(red));
           
            //System.out.println("writing linha");
             
            if (contadorDeLinha>=imagemWidth-1){
                //System.out.println("write espaço vazio");
                for (int j=0;j<pixelsVazios;j++){
                    escrever.write(byteStringToByte(emptyString));
                }
                contadorLinhasLidas++;
                contadorDeLinha = -1;
            }
            escrever.flush();
            contadorDeLinha = contadorDeLinha +1;
         } 
    }
        
    public static String truncarString(String palavra, int limite) {
        //System.out.println("palavara="+palavra);
        String palavraTruncada = "";
        for(int i = 0; i < limite; i++) {
            if (i<palavra.length())
            palavraTruncada += palavra.charAt(i);
            else
            palavraTruncada += "0";
        }
        
        while(palavraTruncada.length() < 8) {
            palavraTruncada += "0";
        }
        
        return palavraTruncada;
    }
    
    public void quantizar() {
        int j = 1;
        for(int i = 0; i < pixel.size(); i++) {
            String r = pixel.get(i).getRed();
            String g = pixel.get(i).getGreen();
            String b = pixel.get(i).getBlue();
            
            pixel.set(i, new Cor(truncarString(r, 5), truncarString(g, 6), truncarString(b, 5),"Quantizacao"));
        }
    }
    
    public static void main(String args[]){
        String red = "12345678";
        String green = "98765432";
        String blue = "00000000";
        System.out.println("red: " +red+ ", green:"+green+",, blue:"+blue);
        String b1 = juntarCores(red, green, 1);
        String b2 = juntarCores(green,blue, 2);
        System.out.println("blue + green="+b1+",green + red="+b2);
    }
}
