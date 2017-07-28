/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import quantizacaoimagem.Cor;
import quantizacaoimagem.Imagem;
import quantizacaoimagem.QuantizacaoImagem;

/**
 *
 * @author FREE
 */
public class imagemPanel extends javax.swing.JPanel {
    private BufferedImage buffer = null;
    private Imagem bufferedImagem= null;
    /**
     * Creates new form imagemPanel
     */
    public imagemPanel(File file) {
        initComponents();
        changeImage(file);
    }
    
    public void quantizar(){
        if (!(bufferedImagem==null)){
            System.out.println("quantizando chamado");
            bufferedImagem.quantizar();
            updateBufferImage(bufferedImagem);
            System.out.println("fim quantizando");
        }
    }
    
    public void subtrair(Imagem img){
        if (!(bufferedImagem==null)){
            System.out.println("quantizando chamado");
            bufferedImagem.subtrair(img);
            updateBufferImage(bufferedImagem);
            System.out.println("fim subtraindo");
        }
    }
    
    private void updateBufferImage(Imagem img){
        List< Cor > pixels = img.getPixel();
        for (int i=0;i<pixels.size();i++){
            //System.out.println("Cor em " + i + "=" + pixels.get(i).toString());
        }
        int imagemWidth = QuantizacaoImagem.binarioParaDecimal(img.getBiWidth());
        int imagemHeight = QuantizacaoImagem.binarioParaDecimal(img.getBiHeight());
        int quantiaPixels = imagemWidth*imagemHeight;
        
        BufferedImage quadro = new BufferedImage(imagemWidth,imagemHeight,BufferedImage.TYPE_INT_ARGB);
        buffer = quadro;

        for (int i=0;i<imagemHeight;i++)
        {
            int i2 = imagemHeight-i-1;
            for (int j=0;j<imagemWidth;j++){
                int corPreencher = Color.OPAQUE;
                if (!(pixels==null)){
                    if (i<imagemHeight && j<imagemWidth){
                        int pos = i2*imagemWidth + j;
                        //System.out.println("i="+i+",j="+j+",pos = " + pos);
                        Cor corRelativa = pixels.get(pos);
                        Color corNoPixel = corRelativa.getValorCor();
                        //System.out.println("Valor em " + pos + "="+corNoPixel.toString());
                        corPreencher = corNoPixel.getRGB();
                        //System.out.println("Escrevendo em " + j + ","+i+"=" + (new Color(corPreencher)).toString());
                    }
                }
                quadro.setRGB(j, i, corPreencher);
            }
        }
        this.repaint();
        System.out.println("fim setter image");
    }
    
    public void changeImage(File file){
       try{
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        QuantizacaoImagem qI = new QuantizacaoImagem(file);
        Imagem img = qI.getImagem(); 
        bufferedImagem = img;
        //salvarImagem(img);
        List< Cor > pixels = img.getPixel();
        for (int i=0;i<pixels.size();i++){
            //System.out.println("Cor em " + i + "=" + pixels.get(i).toString());
        }
        int imagemWidth = QuantizacaoImagem.binarioParaDecimal(img.getBiWidth());
        int imagemHeight = QuantizacaoImagem.binarioParaDecimal(img.getBiHeight());
        int quantiaPixels = imagemWidth*imagemHeight;
        
        BufferedImage quadro = new BufferedImage(imagemWidth,imagemHeight,BufferedImage.TYPE_INT_ARGB);
        buffer = quadro;

        for (int i=0;i<imagemHeight;i++)
        {
            int i2 = imagemHeight-i-1;
            for (int j=0;j<imagemWidth;j++){
                int corPreencher = Color.OPAQUE;
                if (!(pixels==null)){
                    if (i<imagemHeight && j<imagemWidth){
                        int pos = i2*imagemWidth + j;
                        //System.out.println("i="+i+",j="+j+",pos = " + pos);
                        Cor corRelativa = pixels.get(pos);
                        Color corNoPixel = corRelativa.getValorCor();
                        //System.out.println("Valor em " + pos + "="+corNoPixel.toString());
                        corPreencher = corNoPixel.getRGB();
                        //System.out.println("Escrevendo em " + j + ","+i+"=" + (new Color(corPreencher)).toString());
                    }
                }
                quadro.setRGB(j, i, corPreencher);
            }
        }
        this.repaint();
        System.out.println("fim setter image");
        //this.setPreferredSize(new Dimension(buffer.getWidth(),buffer.getHeight()));
        }
        catch(Exception e){
            System.out.println("Erro imagemPanel");
            e.printStackTrace();
        } 
    }
    
    @Override
    public void paint(Graphics g)
    {
        if (!(buffer==null)){
            //System.out.println("DRAWING");
            g.drawImage(buffer, 20, 50, this);
        }
    }
    
    public Imagem getImagem(){
        return(bufferedImagem);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
