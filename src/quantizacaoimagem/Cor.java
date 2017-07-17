/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantizacaoimagem;

/**
 *
 * @author elixandrebaldi
 */
public class Cor {
    private String red;
    private String green;
    private String blue;

    public Cor(String red, String green, String blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
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
}
