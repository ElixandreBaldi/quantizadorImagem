/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantizacaoimagem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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

    public void escreverNovaImagem(FileOutputStream escrever) throws IOException {
        this.escrever = escrever;
        
        escreverCabecalhos();
        escreverCores();
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
    
    public void escreverCabecalhos() throws IOException {   
        String cabecalhoBytes[] = new String[16];
        
        cabecalhoBytes[0] = type;
        cabecalhoBytes[1] = size;
        cabecalhoBytes[2] = reserved;
        cabecalhoBytes[3] = reserved2;
        cabecalhoBytes[4] = offsetBits;

        cabecalhoBytes[5] = biSize;
        cabecalhoBytes[6] = biWidth;
        cabecalhoBytes[7] = biHeight;
        cabecalhoBytes[8] = biPlanes;
        cabecalhoBytes[9] = biBitCount;
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
    
    public void escreverCores() throws IOException {
        for(int i = 0; i < pixel.size(); i++) {
            escrever.write(byteStringToByte(pixel.get(i).getRed()));
            escrever.flush();
            escrever.write(byteStringToByte(pixel.get(i).getGreen()));
            escrever.flush();
            escrever.write(byteStringToByte(pixel.get(i).getBlue()));
            escrever.flush();
        }
        escrever.write(byteStringToByte("00000000"));
        escrever.flush();
        escrever.write(byteStringToByte("00000000"));
        escrever.flush();       
    }
    
    public ArrayList<Cor> getPixel() {
        return pixel;
    }

    public void setPixel(String red, String green, String blue) {
        pixel.add(new Cor(red, green, blue));
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
    
    
}
