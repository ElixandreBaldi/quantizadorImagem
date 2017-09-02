/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantizacaoimagem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author elixandrebaldi
 */
public class QuantizacaoImagem {
    public static final String CAMINHOIMAGEM = "/home/elixandrebaldi/Documentos/PID/quantizacaoImagem/exemplos/img1.bmp";
    public static final String CAMINHONOVAIMAGEM = "/home/elixandrebaldi/Documentos/PID/quantizadorImagem/Images/img1_novo.bmp";
    public final int INTERVALOS[] = new int[16];
    public int iIntervalos = 0;
    private Imagem imgBuffer = null;
    /**
     * @param args the command line arguments
     */
    
    public QuantizacaoImagem(){
        
    }
    
    public QuantizacaoImagem(File file) throws IOException{
        startImagem(file);
    }
    
    public void setarIntervalos() {
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
    
    public String verificaLargura(String valor, int largura){
        while(valor.length() < largura) {
            valor = "0"+valor;
        }   
        return valor;
    }
    
    public Imagem setarCabecalhos(Imagem img, InputStream entrada) throws IOException {
        System.out.println("SETANDO INTERVALOS");
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
        System.out.println("FIM SETANDO INTERVALOS");
        return img;
    }
    
    public static int binarioParaDecimal(String palavra) {
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
    
    public static Imagem setarCores(Imagem img, InputStream entrada) throws IOException {  
        System.out.println("INICIO SETAR CORES");
        int imagemWidth = binarioParaDecimal(img.getBiWidth());
        int imagemHeight = binarioParaDecimal(img.getBiHeight());
        int numeroDePixels = imagemWidth*imagemHeight;
        System.out.println("Imagem W : "+imagemWidth+",Imagem H : "+imagemHeight);
        int contOffset = 54;
        int offsetBits = binarioParaDecimal(img.getOffsetBits());
        BufferedInputStream bInput = new BufferedInputStream(entrada);
        int pixelsVazios = imagemWidth%4;
        
        System.out.println("Espacos vazios : " + pixelsVazios);
        
        System.out.println("Bibitcountsize=" + img.getBiBitCount()+"="+binarioParaDecimal(img.getBiBitCount()));
        
        if(contOffset != offsetBits) {
            while(contOffset < offsetBits) {
                entrada.read();
                contOffset++;
            }
        }
        int contadorDeLinha = 0;
        int contadorLinhasLidas = 0;
        int debugLinhas = 2;
        
        int bitsPorCanal = binarioParaDecimal(img.getBiBitCount());
        if (bitsPorCanal==24){
            System.out.println("24 bits");
            // /*
            //ler 1 linha por vez = 
            int numeroLido = 0;
            for (int j=0;j<imagemHeight;j++){
                byte[] linhaLida = new byte[imagemWidth*3+pixelsVazios];
                bInput.read(linhaLida);
                for (int i=0;i<imagemWidth;i++){
                    numeroLido=numeroLido+1;
                    byte intBlue = linhaLida[i*3];
                    byte intGreen = linhaLida[i*3+1];
                    byte intRed = linhaLida[i*3+2];
                    String red = Integer.toBinaryString( Byte.toUnsignedInt(intRed) );
                    String green = Integer.toBinaryString( Byte.toUnsignedInt(intGreen) );
                    String blue = Integer.toBinaryString( Byte.toUnsignedInt(intBlue) );
                    img.setPixel(red, green, blue,("ID="+Integer.toString(j)));
                    if (j==1 && i<2){
                        System.out.println("intred="+intRed+"red="+red);
                        System.out.println("intgreen="+intGreen+"green="+green);
                        System.out.println("intblue="+intBlue+"blue="+blue);
                    }
                }
            }
            System.out.println("size depois de ler imagem : " + img.getPixel().size());
            // */
            /*
            for (int j=0;j<imagemHeight;j++){
                for (int i=0;i<imagemWidth;i++){
                    int valorLido = j*imagemHeight+i;
                    int intBlue = entrada.read();
                    int intGreen = entrada.read();
                    int intRed = entrada.read();
                    String red = Integer.toBinaryString(intRed);
                    String green = Integer.toBinaryString(intGreen);
                    String blue = Integer.toBinaryString(intBlue);
                    //System.out.println("ID="+j+",Coluna="+contadorLinhasLidas+",valorRed que eh blue="+blue);
                    if (contadorDeLinha>=imagemWidth-1){
                        for (int i2=0;i2<pixelsVazios;i2++){
                            entrada.read();
                        }
                        contadorLinhasLidas++;
                        contadorDeLinha = -1;
                    }
                    if (j==1 && i<2){
                        System.out.println("intred="+intRed+"red="+red);
                        System.out.println("intgreen="+intGreen+"green="+green);
                        System.out.println("intblue="+intBlue+"blue="+blue);
                    }
                    img.setPixel(red, green, blue,("ID="+Integer.toString(j)));
                    contadorDeLinha = contadorDeLinha +1;
                }
            }
            // */
        }else if(bitsPorCanal==16){
            System.out.println("16 bits");
            for (int j=0;j<numeroDePixels;j++){
                int intBlueGreen = entrada.read();
                int intGreenRed = entrada.read();
                String BlueGreen = Integer.toBinaryString(intBlueGreen);
                String GreenRed = Integer.toBinaryString(intGreenRed);
                String blue = "";
                String green = "";
                String red = "";
                int desloc = 8-BlueGreen.length();
                for (int i=0;i<8;i++)
                {   
                    char charLocal;
                    if (i<desloc){
                        charLocal = '0';
                    }else{
                        charLocal = BlueGreen.charAt(i-desloc);
                    }
                    if (i<5){
                        blue = blue + charLocal;
                    }else{
                        green = green + charLocal;
                    }
                }
                desloc = 8-GreenRed.length();
                for (int i=0;i<8;i++){
                    char charLocal;
                    if (i<desloc){
                        charLocal = '0';
                    }else{
                        charLocal = GreenRed.charAt(i-desloc);
                    }
                    if (i<3){
                        green = green + charLocal;
                    }else{
                        red = red + charLocal;
                    }
                }
                blue = blue + "000";
                green = green + "00";
                red = red + "000";
                //System.out.println("blue = " + blue);
                //System.out.println("green = " + green);
                //System.out.println("red = " + red);
                //System.out.println("ID="+j+",Coluna="+contadorLinhasLidas+",valorRed que eh blue="+blue);
                if (contadorDeLinha>=imagemWidth-1){
                    for (int i=0;i<pixelsVazios;i++){
                        entrada.read();
                    }
                    contadorLinhasLidas++;
                    contadorDeLinha = -1;
                }
                if (j<debugLinhas){
                    System.out.println("bluegreen = " + BlueGreen);
                    System.out.println("greenred = " + GreenRed);
                    System.out.println("red="+red);
                    System.out.println("green="+green);
                    System.out.println("blue="+blue);
                }
                img.setPixel(red, green, blue,("ID="+Integer.toString(j)));
                contadorDeLinha = contadorDeLinha +1;
            }
        }
        System.out.println("Linhas lidas = " + contadorLinhasLidas);
        System.out.println("FIM SETAR CORES");
        return img;
    }
    
    public void salvarImagem(Imagem img,String sufixo) throws FileNotFoundException, IOException {
        FileOutputStream escreverSaida = new FileOutputStream(CAMINHONOVAIMAGEM+sufixo);
        
        //img.escreverNovaImagem24Bits(escreverSaida);        
        img.escreverNovaImagem16Bits(escreverSaida);        
        
        escreverSaida.close();
    }
    
    public void startImagem(File file) throws FileNotFoundException, IOException{
        System.out.println("INICIO START IMAGEM");
        setarIntervalos();
        InputStream entrada = new FileInputStream(file);
        Imagem img = new Imagem();
        //Imagem imgOriginal = new Imagem();
        
        img = setarCabecalhos(img, entrada);
        
        img = setarCores(img, entrada);
        //imgOriginal = setarCabecalhos(imgOriginal, entrada);
        
        //imgOriginal = setarCores(imgOriginal, entrada);
        
        //img.quantizar();
        
        //salvarImagem(img,"modificada");
        //salvarImagem(imgOriginal,"original");
        
        entrada.close();
        imgBuffer = img;
        System.out.println("FIM START IMAGEM");
    }
    
    public Imagem getImagem(){
        return(imgBuffer);
    }
        
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        /*
        QuantizacaoImagem quantiza = new QuantizacaoImagem();
        quantiza.setarIntervalos();
        InputStream entrada = new FileInputStream(CAMINHOIMAGEM);
        Imagem img = new Imagem();
        //Imagem imgOriginal = new Imagem();
        
        img = quantiza.setarCabecalhos(img, entrada);
        
        img = quantiza.setarCores(img, entrada);
 
        entrada = new FileInputStream(CAMINHOIMAGEM);
        //imgOriginal = setarCabecalhos(imgOriginal, entrada);
        
        //imgOriginal = setarCores(imgOriginal, entrada);
        
        img.quantizar();
        
        quantiza.salvarImagem(img,"modificada");
        //salvarImagem(imgOriginal,"original");
        
        entrada.close();
        */
        int v = binarioParaDecimal("0");
        System.out.println("RESUL="+v);
    }    
}
