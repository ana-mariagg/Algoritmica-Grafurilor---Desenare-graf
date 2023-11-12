import javax.swing.*; // pentru fereastra

public class Graf
{
    public Graf() {
    }
    private static void initUI() {  //initUI= initializare user interface
        JFrame f = new JFrame("Algoritmica Grafurilor");
        //sa se inchida aplicatia atunci cand inchid fereastra
        f.setDefaultCloseOperation(3);
        //imi creez obj MyPanel
        f.add(new MyPanel());
        //setez dimensiunea ferestrei
        f.setSize(1920, 1080);
        //fac fereastra vizibila
        f.setVisible(true);
    }

    public static void main(String[] args)
    {
        //pornesc firul de executie grafic
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Graf.initUI();
            }
        });

    }
}
