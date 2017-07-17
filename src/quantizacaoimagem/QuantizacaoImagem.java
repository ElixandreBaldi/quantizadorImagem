/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantizacaoimagem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static sun.text.normalizer.UTF16.append;

/**
 *
 * @author elixandrebaldi
 */
public class QuantizacaoImagem {
    public static final String CAMINHOIMAGEM = "/home/elixandrebaldi/Documentos/PID/quantizacaoImagem/exemplos/img1.bmp";
    public static final String CAMINHONOVAIMAGEM = "/home/elixandrebaldi/Documentos/PID/quantizacaoImagem/exemplos/img1_novo.bmp";
    public static final int INTERVALOS[] = new int[16];
    public static int iIntervalos = 0;
    /**
     * @param args the command line arguments
     */
    public static void setarIntervalos() {
        INTERVALOS[0] = 2;
        INTERVALOS[1] = 4;
        INTERVALOS[2] = 2;
        INTERVALOS[3] = 2;
        INTERVALOS[4] = 4;
        INTERVALOS[5] = 4;
        INTERVALOS[6] = 4;
        INTERVALOS[7] = 4;
        INTERVALOS[8] = 2;
        INTERVALOS[9] = 2;
        INTERVALOS[10] = 4;
        INTERVALOS[11] = 4;
        INTERVALOS[12] = 4;
        INTERVALOS[13] = 4;
        INTERVALOS[14] = 4;
        INTERVALOS[15] = 4;
    }
    
    public static String verificaLargura(String valor, int largura){
        while(valor.length() < largura) {
            valor = "0"+valor;
        }   
        return valor;
    }
    
    public static Imagem setarCabecalhos(Imagem img, InputStream entrada) throws IOException {
        while(iIntervalos < INTERVALOS.length){
            String variavelCorrente = "";
            for(int i = 0; i < INTERVALOS[iIntervalos]; i++) {            
                int umByte = entrada.read();        
                String byteCorrente = Integer.toBinaryString(umByte);
                byteCorrente = verificaLargura(byteCorrente, 8);
                variavelCorrente = byteCorrente+variavelCorrente;            
            }
            //System.out.println(iIntervalos+": "+variavelCorrente);
            switch (iIntervalos) {
                case 0:
                    img.setType(variavelCorrente);
                    break;
                case 1:
                    img.setSize(variavelCorrente);
                    break;
                case 2:
                    img.setReserved(variavelCorrente);
                    break;
                case 3:
                    img.setReserved2(variavelCorrente);
                    break;
                case 4:
                    img.setOffsetBits(variavelCorrente);
                    break;
                case 5:
                    img.setBiSize(variavelCorrente);
                    break;
                case 6:
                    img.setBiWidth(variavelCorrente);
                    break;
                case 7:
                    img.setBiHeight(variavelCorrente);
                    break;
                case 8:
                    img.setBiPlanes(variavelCorrente);
                    break;
                case 9:
                    img.setBiBitCount(variavelCorrente);
                    break;
                case 10:
                    img.setBiCompression(variavelCorrente);
                    break;
                case 11:
                    img.setBiSizeImage(variavelCorrente);
                    break;
                case 12:
                    img.setBiXPelsPerMeter(variavelCorrente);
                    break;
                case 13:
                    img.setBiYPelsPerMeter(variavelCorrente);
                    break;   
                case 14:
                    img.setBiClrUsed(variavelCorrente);
                    break;
                case 15:
                    img.setBiClrImportant(variavelCorrente);
                    break;                                                   
            }
            iIntervalos++;
        }        
        return img;
    }
    
    public static int binarioParaDecimal(String palavra) {
        int decimal = 0;
        int soma = 1;
        for(int i = palavra.length()-1; i >= 0; i--) {
            if(palavra.charAt(i) == '1')
                decimal += soma;
            soma *=2;
        }
        
        return decimal;
    }
    
    public static Imagem setarCores(Imagem img, InputStream entrada) throws IOException {        
        int contOffset = 54;
        int offsetBits = binarioParaDecimal(img.getOffsetBits());
        
        if(contOffset != offsetBits) {
            while(contOffset < offsetBits) {
                entrada.read();
                contOffset++;
            }
        }                
        int i = 0;
        do {            
            int intRed = entrada.read();
            int intGreen = entrada.read();
            int intBlue = entrada.read();
            
            if(intRed == 0 && intGreen == 0 && intBlue == -1)
                break;
            
            String red = Integer.toBinaryString(intRed);
            String green = Integer.toBinaryString(intGreen);
            String blue = Integer.toBinaryString(intBlue);
            
            img.setPixel(red, green, blue);
        }while(true);
        
        return img;
    }
    
    public static void salvarImagem(Imagem img) throws FileNotFoundException, IOException {
        FileOutputStream escreverSaida = new FileOutputStream(CAMINHONOVAIMAGEM);
        
        img.escreverNovaImagem(escreverSaida);        
        
        escreverSaida.close();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        setarIntervalos();
        InputStream entrada = new FileInputStream(CAMINHOIMAGEM);
        Imagem img = new Imagem();
        
        img = setarCabecalhos(img, entrada);
        
        img = setarCores(img, entrada);
        
        salvarImagem(img);
        
        entrada.close();
    }    
}
