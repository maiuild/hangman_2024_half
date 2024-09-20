package views.panels;

import models.Database;
import models.Model;
import views.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * See on edetabeli klass. See näitab andmebaasist loetud edetabelit. Seda ei saa mängimise ajal
 * vaadata.
 */
public class LeaderBoard extends JPanel {
    /**
     * Klassisisene mudel, mille väärtus saadakse View konstruktorist ja loodud MainApp-is
     */
    private final Model model;
    /**
     * Klassisisene vaade, mille väärtus saadakse otse View-st
     */
    private final View view;

    /**
     * Tabeli päis mida näeb Edetabeli vahelehel
     */
    private String[] heading = new String[]{"Kuupäev", "Nimi", "Sõna", " Tähed", "Mänguaeg"};

    /**
     * Loome tabeli teostuse päisega, kuid andmeid pole
     */
    private DefaultTableModel dtm = new DefaultTableModel(heading, 0);

    /**
     * uue tabeli loomine
     */
    private JTable table = new JTable(dtm);

    /**
     * Leaderboard konstruktor
     * @param model loodud mudel MainAppis
     * @param view loodud view MainAppis
     */
    public LeaderBoard(Model model, View view) {
        this.model = model;
        this.view = view;

        setLayout(new BorderLayout());
        setBackground(new Color(250, 150, 215)); // Leaderboard paneeli taustavärv
        setBorder(new EmptyBorder(5, 5, 5, 5));

        model.setDtm(dtm);
        createLeaderboard(); // Loob edetabeli tabeli paneelile
    }

    private void createLeaderboard() {
        // Kerimisriba tabeli paremal servas, siis kui vaja
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        // Tabeli esimene veerg 100px
        table.getColumnModel().getColumn(0).setPreferredWidth(120);

        // Tabeli sisu pole muudetav 1990817 stackoverflow
        table.setDefaultEditor(Object.class, null);

        // Lahtri keskele koondamine
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);


        // Kijuta tabelist sisu mudelisse
       new Database(model).selectScores();
       if(!model.getDataScores().isEmpty()) {
        view.updateScoreTable();
       } else {
           JOptionPane.showMessageDialog(view, "Esmalt tuleb mängida");
       }
    }

}
