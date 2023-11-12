import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException; //daca nu poate sa scrie, arunca pe ecran o exceptie "Eroare la....."
import java.io.PrintWriter;
import java.util.Vector;


public class MyPanel extends JPanel {
    private int nodeNr = 0; //nr noduri
    private int nodeDiam = 30; //diametrul cercului
    private Vector<Node> listaNoduri;
    private Vector<Arc> listaArce;
    Point pointStart = null;
    Point pointEnd = null;
    boolean isDragging = false;
    boolean grafOrientat = false;
    private int[][] matriceAdiacenta = new int[200][200];
    boolean modMutareNoduri = false;
    int indexNodMutat;


    //initializare matrice cu valori de 0
    void initMatrice(int[][] matriceAdiacenta) {
        for (int index = 0; index < listaNoduri.size(); index++) {
            for (int index2 = 0; index2 < listaNoduri.size(); index2++) {
                matriceAdiacenta[index][index2] = 0;
            }
        }
    }

    //se sterge graful neorientat cand apas pe graf orientat
    void deleteGraf(Vector<Node> listaNoduri, Vector<Arc> listaArce) {
        listaArce.clear();
        listaNoduri.clear();
        initMatrice(matriceAdiacenta);
        nodeNr = 0;
        repaint();
    }

    // functie folosita pt verificarea distantei (la desenare)
    // daca distanta dintre nodul desenat si nodul care urmeaza a fi desenat este prea mica,
    // atunci nodul nu se adauga
    boolean canAddNode(int x, int y) {
        for (int i = 0; i < listaNoduri.size(); i++)
            if (Math.sqrt((Math.pow((listaNoduri.elementAt(i).getCoordX() - x), 2)) +
                    (Math.pow((listaNoduri.elementAt(i).getCoordY() - y), 2))) < 40)
                return false;
        return true;
    }

    // functie folosita pt a verifica daca coordonatele mouse-ului se afla pe surpafata unui nod
    boolean nodeSurface(int x, int y, Point p) {    //point p e mouse ul care e un pct cu coord x si y
        return (((x < p.getX()) && (p.getX() < x + nodeDiam)) && ((y < p.getY()) && (p.getY() < y + nodeDiam)));
    }
    //x->nodul dat si p.getX() e pt nodul care se verifica pt a fi adaugat, la fel si la y

    void afisareListaAdiacenta(Vector<Node> listaNoduri) {
        try {
            //clasa printwriter e luata din pachet
            PrintWriter out = new PrintWriter(new FileWriter("ListaAdiacenta.txt"));
            out.println("Lista de adiacenta: ");
            for (int index = 0; index < listaNoduri.size(); index++)
                out.println((index) + " " + listaNoduri.elementAt(index).getListaAdiacenta());
            out.println("\n");

            out.close();
        } catch (IOException e1) {
            //este un obiect de tip IOException
            System.out.println("Eroare la scrierea in fisierul ListaAdiacenta.txt");
        }
    }

    void afisareMatriceAdiacenta(int[][] matriceAdiacenta) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("MatriceAdiacenta.txt"));
            out.println("In graf sunt " + nodeNr + " noduri");
            out.print("\n");
            out.println("Matricea de adiacenta: ");
            for (int index = 0; index < listaNoduri.size(); index++) {
                for (int index2 = 0; index2 < listaNoduri.size(); index2++) {
                    out.print(matriceAdiacenta[index][index2] + " ");
                }
                out.print("\n");
            }
            out.close();
        } catch (IOException e1) {
            System.out.println("Eroare la scrierea in fisierul MatriceAdiacenta.txt");
        }
    }

    public MyPanel() {
        listaNoduri = new Vector<Node>();
        listaArce = new Vector<Arc>();
        initMatrice(matriceAdiacenta);

        // borderul panel-ului
        setBorder(BorderFactory.createLineBorder(Color.black));

        //creez buton pentru modul de desenare graf orientat
        MyPanel f = this;
        JToggleButton b = new JToggleButton("Graf orientat");
        b.setBounds(50, 100, 95, 30);
        f.add(b);
        b.setVisible(true);

        //creez buton pentru modul mutare noduri
        JToggleButton b1 = new JToggleButton("Repozitionare graf ");
        b.setBounds(20, 100, 95, 30);
        f.add(b1);
        b1.setVisible(true);

        //daca butonul b e apasat, se intra in modul de graf orientat
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                if (selected) {
                    grafOrientat = true;
                    deleteGraf(listaNoduri, listaArce);
                } else {
                    grafOrientat = false;
                    deleteGraf(listaNoduri, listaArce);
                }
            }
        };
        b.addActionListener(actionListener);


        //daca butonul b1 e apasat, se intra in modul de repozitionare graf
        ActionListener actionListener1 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton1 = (AbstractButton) actionEvent.getSource();
                boolean selected1 = abstractButton1.getModel().isSelected();
                if (selected1) {
                    modMutareNoduri = true;
                } else {
                    modMutareNoduri = false;
                }
            }
        };
        b1.addActionListener(actionListener1);


        addMouseListener(new MouseAdapter() {
            //evenimentul care se produce la apasarea mousse-ului
            public void mousePressed(MouseEvent e) {    // mouse event = clasa pentru click, dublu ckick, mouse up, mouse down)
                pointStart = e.getPoint();
                if (modMutareNoduri) {
                    for (int i = 0; i < listaNoduri.size(); i++) {
                        if (nodeSurface(listaNoduri.elementAt(i).getCoordX(), listaNoduri.elementAt(i).getCoordY(), pointStart)) {
                            indexNodMutat = i;
                        }
                    }
                }
            }

            //evenimentul care se produce la eliberarea mousse-ului
            public void mouseReleased(MouseEvent e) {
                if (!modMutareNoduri) {
                    if (!isDragging) {
                        //adaugam primul nod in lista
                        if (listaNoduri.size() == 0)
                            addNode(e.getX(), e.getY());
                        else
                            //verificam distanta intre punctele curente si fiecare nod din lista
                            if ((canAddNode(e.getX(), e.getY())))
                                addNode(e.getX(), e.getY());

                    } else {

                        //verificam daca pointStart este pe suprafata unui nod
                        for (int i = 0; i < listaNoduri.size(); i++) {
                            if (nodeSurface(listaNoduri.elementAt(i).getCoordX(), listaNoduri.elementAt(i).getCoordY(), pointStart)) {

                                //verificam daca pointEnd este pe suprafata unui nod
                                for (int j = 0; j < listaNoduri.size(); j++) {
                                    if (nodeSurface(listaNoduri.elementAt(j).getCoordX(), listaNoduri.elementAt(j).getCoordY(), pointEnd)) {

                                        //daca ambele conditii sunt indeplinite si nodurile sunt diferite, se adauga arcul in listaArce
                                        if (i != j) {
                                            Arc arc = new Arc(listaNoduri.elementAt(i),listaNoduri.elementAt(j));
                                            listaArce.add(arc);

                                            if (grafOrientat) {
                                                //se adauga nodul in lista de adiacenta
                                                listaNoduri.elementAt(i).adaugaVecini(j);

                                                //se adauga arcul in matricea de adiacenta
                                                matriceAdiacenta[i][j] = 1;

                                            } else {
                                                //se adauga nodurile in lista de adiacenta
                                                listaNoduri.elementAt(i).adaugaVecini(j);
                                                listaNoduri.elementAt(j).adaugaVecini(i);

                                                //se adauga arcele in matricea de adiacenta
                                                matriceAdiacenta[i][j] = 1;
                                                matriceAdiacenta[j][i] = 1;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    pointStart = null;
                    isDragging = false;

                    //se redeseneaza graful
                    repaint();
                    afisareListaAdiacenta(listaNoduri);
                    afisareMatriceAdiacenta(matriceAdiacenta);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            //evenimentul care se produce la drag&drop pe mousse
            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                isDragging = true;
                if (!modMutareNoduri) {
                    for (int i = 0; i < listaNoduri.size(); i++) {
                        if (nodeSurface(listaNoduri.elementAt(i).getCoordX(), listaNoduri.elementAt(i).getCoordY(), pointStart)) {
                            repaint();
                        }
                    }
                } else {
                    if (listaNoduri.elementAt(indexNodMutat) != null) {

                        //retinem coord vechi ale nodului
                        int xVechi = listaNoduri.elementAt(indexNodMutat).getCoordX();
                        int yVechi = listaNoduri.elementAt(indexNodMutat).getCoordY();

                        if(canAddNode(e.getX(),e.getY())) {
                            //actualizam coordonatele nodului cu coord pointEnd
                            listaNoduri.elementAt(indexNodMutat).setCoordX(e.getX());
                            listaNoduri.elementAt(indexNodMutat).setCoordY(e.getY());
                        }
                        repaint();
                    }
                }
            }
        });

    }

    //metoda care se apeleaza la eliberarea mouse-ului
    private void addNode(int x, int y) {
        Node node = new Node(x, y, nodeNr);
        listaNoduri.add(node);
        nodeNr++;
        repaint();
    }

    //se executa atunci cand apelam repaint()
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//apelez metoda paintComponent din clasa de baza

        //daca este graf orientat, se trag arce cu sageata in capete
        if (grafOrientat) {
            for (Arc a : listaArce) {
                a.drawArrow(g);
            }
            //deseneaza arcul curent; cel care e in curs de desenare
            if (pointStart != null && !modMutareNoduri) {
                g.setColor(Color.black);
                g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
            }
        }

        //daca nu este graf orientat, se trag arce fara sageata
        else {
            for (Arc a : listaArce) {
                a.drawArc(g);
            }
            //deseneaza arcul curent; cel care e in curs de desenare
            if (pointStart != null && !modMutareNoduri) {
                g.setColor(Color.black);
                g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
            }
        }

        //deseneaza lista de noduri pentru graf orientat/neorientat
        for (int i = 0; i < listaNoduri.size(); i++) {
            listaNoduri.elementAt(i).drawNode(g, nodeDiam);
        }
    }
}
