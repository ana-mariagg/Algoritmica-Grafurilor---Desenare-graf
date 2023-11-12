import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException; //daca nu poate sa scrie, arunca pe ecran o exceptie "Eroare la....."
import java.io.PrintWriter;
import java.util.Vector;
import java.util.Random;

//https://www.youtube.com/watch?v=wJY43Ml6vcQ&ab_channel=MadonaS.Wambua

public class MyPanel extends JPanel {
    private int nodeNr = 0; //nr noduri
    private int writtenNumber = 0;
    static public int nodeDiam = 15; //diametrul cercului
    public static int probability = 90;

    //le-am alocat spatiu pentru listaNoduri si lista Arce
    private Vector<Node> listaNoduri = new Vector();
    private Vector<Arc> listaArce = new Vector();
    //Point pointStart = null;
    //Point pointEnd = null;
    // boolean isDragging = false;
    boolean grafOrientat = false;
    // private int[][] matriceAdiacenta = new int[200][200];
    //boolean modMutareNoduri = false;
    //int indexNodMutat;


    //initializare matrice cu valori de 0
  /*  void initMatrice(int[][] matriceAdiacenta) {
        for (int index = 0; index < listaNoduri.size(); index++) {
            for (int index2 = 0; index2 < listaNoduri.size(); index2++) {
                matriceAdiacenta[index][index2] = 0;
            }
        }
    }
   */

    //se sterge graful neorientat cand apas pe graf orientat si invers
    void deleteGraf(Vector<Node> listaNoduri, Vector<Arc> listaArce) {
        listaArce.clear();
        listaNoduri.clear();
        this.writtenNumber = 0;
        repaint();
    }

    // functie folosita pt verificarea distantei (la desenare)
    // daca distanta dintre nodul desenat si nodul care urmeaza a fi desenat este prea mica,
    // atunci nodul nu se adauga
    boolean canAddNode(int x, int y) {
        for (int i = 0; i < this.listaNoduri.size(); ++i) {
            if (Math.sqrt(Math.pow((double) (((Node) this.listaNoduri.elementAt(i)).getCoordX() - x), 2.0) + Math.pow((double) (((Node) this.listaNoduri.elementAt(i)).getCoordY() - y), 2.0)) < 20.0) {
                return false;
            }
        }

        return true;
    }

    //functie pentru adaugare de nod
    private void addNode(int x, int y) {
        Node node = new Node(x, y, this.writtenNumber);
        this.listaNoduri.add(node);
        ++this.writtenNumber;
        this.repaint();
    }
        // functie folosita pt a verifica daca coordonatele mouse-ului se afla pe surpafata unui nod
    /*boolean nodeSurface(int x, int y, Point p) {    //point p e mouse ul care e un pct cu coord x si y
        return (((x < p.getX()) && (p.getX() < x + nodeDiam)) && ((y < p.getY()) && (p.getY() < y + nodeDiam)));
    }
    //x->nodul dat si p.getX() e pt nodul care se verifica pt a fi adaugat, la fel si la y
    /*

     */
   /* void afisareListaAdiacenta(Vector<Node> listaNoduri) {
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
*/
    /*
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
     */

    public MyPanel() {

            // borderul panel-ului
            this.setBorder(BorderFactory.createLineBorder(Color.black));

            //creez buton pentru modul de desenare graf orientat
            MyPanel f = this;
            final JToggleButton b = new JToggleButton("Graf orientat");
            b.setBounds(15, 100, 95, 30);
            this.add(b);
            b.setVisible(true);

            //creez buton pentru numarul de noduri
            final JTextField b2 = new JTextField(10);
            b.setBounds(20, 100, 95, 30);
            this.add(b2);
            this.setVisible(true);

            //creez buton pentru generare graf
            JToggleButton b1 = new JToggleButton("Generare graf");
            b.setBounds(15, 100, 95, 30);
            this.add(b1);
            b1.setVisible(true);

            //daca butonul b e apasat, se intra in modul de graf orientat
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                    boolean selected = abstractButton.getModel().isSelected();
                    if (selected) {
                        MyPanel.this.grafOrientat = true;
                        b.setText("Graf neorientat");
                        MyPanel.this.deleteGraf(MyPanel.this.listaNoduri, MyPanel.this.listaArce);
                    } else {
                        MyPanel.this.grafOrientat = false;
                        b.setText("Graf orientat");
                        MyPanel.this.deleteGraf(MyPanel.this.listaNoduri, MyPanel.this.listaArce);
                    }
                }
            };
            //am legat butonul creat de butonul abstract
            b.addActionListener(actionListener);


            //daca butonul b1 e apasat, se intra in modul de repozitionare graf
            ActionListener actionListener1 = new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    AbstractButton abstractButton1 = (AbstractButton) actionEvent.getSource();
                    boolean selected1 = abstractButton1.getModel().isSelected();
                    if (selected1) {
                        MyPanel.this.nodeNr = Integer.parseInt(b2.getText());
                        if (MyPanel.this.nodeNr < 100) {
                            MyPanel.probability = 85;
                        } else if (MyPanel.this.nodeNr > 100 && MyPanel.this.nodeNr < 150) {
                            MyPanel.probability = 92;
                        } else {
                            MyPanel.probability = 98;
                        }

                        int upperboundx = 1550;
                        int upperboundy = 800;

                        int i;
                        int random_prob;
                        for (i = 0; i < MyPanel.this.nodeNr; ++i) {
                            Random randx = new Random();
                            int random_nrx = randx.nextInt(upperboundx);
                            random_prob = randx.nextInt(upperboundy);
                            if (MyPanel.this.canAddNode(random_nrx, random_prob)) {
                                MyPanel.this.addNode(random_nrx, random_prob);
                            }
                        }

                        for (i = 0; i < MyPanel.this.listaNoduri.size(); ++i) {
                            for (int j = 0; j < MyPanel.this.listaNoduri.size(); ++j) {
                                if (i != j) {
                                    Random rand = new Random();
                                    random_prob = rand.nextInt(100);
                                    if (random_prob > MyPanel.probability) {
                                        Arc arc = new Arc((Node) MyPanel.this.listaNoduri.elementAt(i), (Node) MyPanel.this.listaNoduri.elementAt(j));
                                        MyPanel.this.listaArce.add(arc);
                                    }
                                }
                            }
                        }

                        MyPanel.this.repaint();
                    } else {
                        b2.setText("");
                        MyPanel.this.deleteGraf(MyPanel.this.listaNoduri, MyPanel.this.listaArce);
                    }

                }
            };
            //am legat butonul creat de butonul abstract
            b1.addActionListener(actionListener1);
        }
            /*addMouseListener(new MouseAdapter() {
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
                   // afisareListaAdiacenta(listaNoduri);
                    afisareMatriceAdiacenta(matriceAdiacenta);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            //evenimentul care se produce la drag&drop pe mousse, doar pentru miscare mouse
            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                isDragging = true;
                if (!modMutareNoduri) { //daca nu suntem in modul de mutare noduri, atunci deseneaza linia pe care vr sa o trag
                    for (int i = 0; i < listaNoduri.size(); i++) {
                        if (nodeSurface(listaNoduri.elementAt(i).getCoordX(), listaNoduri.elementAt(i).getCoordY(), pointStart)) {
                            repaint();
                        }
                    }
                } else {    //daca suntem in modul de mutare nod, atunci muta nodul
                    if (listaNoduri.elementAt(indexNodMutat) != null) {

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

    //metoda care se apeleaza la eliberarea mouse-ului si am creat un nou nod ca sa il adauge in vectorul de noduri
    private void addNode(int x, int y) {
        Node node = new Node(x, y, nodeNr);
        listaNoduri.add(node);
        nodeNr++;
        repaint();
    }
*/

            //se executa atunci cand apelam repaint()
        protected void paintComponent (Graphics g){
                super.paintComponent(g);//apelez metoda paintComponent din clasa de baza

                int i;
                if (this.grafOrientat) {
                    for (i = 0; i < this.listaArce.size(); ++i) {
                        ((Arc) this.listaArce.elementAt(i)).drawArrow(g);
                    }
                } else {
                    for (i = 0; i < this.listaArce.size(); ++i) {
                        ((Arc) this.listaArce.elementAt(i)).drawArc(g);
                    }
                }

                for (i = 0; i < this.listaNoduri.size(); ++i) {
                    ((Node) this.listaNoduri.elementAt(i)).drawNode(g, nodeDiam);
                }
            }
        }