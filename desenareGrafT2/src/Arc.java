import java.awt.Color;
import java.awt.Graphics;

public class Arc {
    private Node start;
    private Node end;

    public Arc(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public void drawArc(Graphics g) {
        if (this.start != null) {
            g.setColor(Color.BLACK);
            g.drawLine(this.start.getCoordX() + 15, this.start.getCoordY() + 15, this.end.getCoordX() + 15, this.end.getCoordY() + 15);
            //ca sa se duca pana in mijloc;
        }
    }

    public void drawArrow(Graphics g) {
        if (this.start != null) {
            int heightArrow = 10;
            int widthArrow = 3;
            int xDifference = this.end.getCoordX() - this.start.getCoordX();
            int yDifference = this.end.getCoordY() - this.start.getCoordY();

            double D = Math.sqrt((double)xDifference * xDifference + yDifference * yDifference);
            double xm = D - (double) heightArrow;
            double xn = xm;
            double ym = (double)widthArrow;
            double yn = (double)(-widthArrow);

            double sin = (double)yDifference / D;
            double cos = (double)xDifference / D;

            double x = xm * cos - ym * sin + (double)this.start.getCoordX() + (double)(MyPanel.nodeDiam / 2);
            ym = xm * sin + ym * cos + (double)this.start.getCoordY() + (double)(MyPanel.nodeDiam / 2);
            xm = x;

            //+MyPanel.nodeDiam/2 ma ajuta sa ii duc varful sagetii pana in centru, nu pana in stanga sus

            x = xn * cos - yn * sin + (double)this.start.getCoordX() + (double)(MyPanel.nodeDiam / 2);
            yn = xn * sin + yn * cos + (double)this.start.getCoordY() + (double)(MyPanel.nodeDiam / 2);
            int[] xpoints = new int[]{this.end.getCoordX() + MyPanel.nodeDiam / 2, (int)xm, (int)x};
            int[] ypoints = new int[]{this.end.getCoordY() + MyPanel.nodeDiam / 2, (int)ym, (int)yn};
            g.setColor(Color.BLACK);
            g.drawLine(this.start.getCoordX() + MyPanel.nodeDiam / 2, this.start.getCoordY() + MyPanel.nodeDiam / 2, this.end.getCoordX() + MyPanel.nodeDiam / 2, this.end.getCoordY() + MyPanel.nodeDiam / 2);
            g.fillPolygon(xpoints, ypoints, 3);

        }
    }
}
