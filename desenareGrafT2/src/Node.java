import java.awt.*;
import java.util.Vector;

public class Node {
    private int coordX;
    private int coordY;
    private int number;
    private final Vector<Integer> listaAdiacenta;   //cate o linie a matricii

    public Node(int coordX, int coordY, int number) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
        listaAdiacenta = new Vector<Integer>();
    }

    public int getCoordX() {
        return this.coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return this.coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Vector<Integer> getListaAdiacenta() {
        return this.listaAdiacenta;
    }


    public void drawNode(Graphics g, int node_diam) {
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", 1, 8));
        g.fillOval(this.coordX, this.coordY, node_diam, node_diam);
        g.setColor(Color.black);
        g.drawOval(this.coordX, this.coordY, node_diam, node_diam);
        if (this.number < 10)
        {
            g.drawString(Integer.valueOf(this.number).toString(), this.coordX + 6, this.coordY + 10);
    } else {
        g.drawString(Integer.valueOf(this.number).toString(), this.coordX + 4, this.coordY + 10);
    }        }

    public void adaugaVecini(Integer n) {
        this.listaAdiacenta.add(n);
    }

}
