package models;

import models.datastructures.DataScore;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Model {
    private final String chooseCategory = "Kõik kategooriad";
    /**
     * See on vaikimisi andmebaasi fail kui käsurealt uut ei leotud. Andmebaasi tabelit nimed ja struktuurid peavad
     * samad olema, kuid andmed võivad erinevad olla.
     *  hangman_words_ee.db - Eestikeelsed sõnad, edetabel on tühi
     *  hangman_words_en.db - Inglisekeelsed sõnad, edetabel on tühi
     *  hangman_words_ee_test.db - Eestikeelsed sõnad, edetabel EI ole tühi
     */
    private String databaseFile = "hangman_words_ee_test.db";

    private String selectedCategory; // Vaikimisi valitud kategooria
    private String[] cbmCategories;

    /**
     * Kaust, kus on pildid
     * @
     */
    private String imagesFolder = "images";
    /**
     * Siia pannakse kõik võllapuu pildid õiges järjekorras
     */
    private List<String> imageFiles = new ArrayList<>();

    /**
     * Tabeli mugavaks kasutamiseks
     */
    private DefaultTableModel dtm;

    /**
     * Edetabeli anbmed listis
     */
    private List<DataScore> dataScores = new ArrayList<>();

    // Rippmenüü sisu
    // nii getter kui setter vaja
    public Model(String dbName) {
        if(dbName != null) {
            this.databaseFile = dbName;
        }
     //   System.out.println(this.databaseFile); Testimine käsurealt andmebaasi nime
        new Database(this); // Loome andmebaasi ühenduse
        readImagesFolder(); // Loeme võllapuu pildid mällu (Listi)
        selectedCategory = chooseCategory; // Vaikimisi "Kõik kategooriad"
    }

    private void readImagesFolder() {
        File folder = new File(imagesFolder); // Loo kausta objekt (Jah, File)
        File[] files = folder.listFiles(); // Loe kõik failid File objekti listi
        for (File file : Objects.requireNonNull(files)) {
            imageFiles.add(file.getAbsolutePath()); // Täispikk kausta tee

        }
        Collections.sort(imageFiles); // Sorteerida kasvavalt kausta sisu
        // System.out.println(imageFiles); // Test näita kausta sisu
    }

    /**
     * Rippmenüü esimene valik enne kategooriaid
     * @return teksti "Kõik kategooriad"
     */
    public String getChooseCategory() {
        return chooseCategory;
    }

    /**
     * Millise andmebaasiga on tegemist
     * @return andmebaasi failinimi
     */
    public String getDatabaseFile() {
        return databaseFile;
    }

    /**
     * Seadistab uue andmebaasi failinime, kui see saadi käsurealt
     * @param databaseFile uus andmebaasi failinimi
     */
    public void setDatabaseFile(String databaseFile) {
        this.databaseFile = databaseFile;
    }

    /**
     * Valitud kategoori
     * @return tagastab valitud kategooria
     */
    public String getSelectedCategory() {
        return selectedCategory;
    }

    /**
     * Seadistab valitud kategooria
     * @param selectedCategory uus valitud kategooria
     */
    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    /**
     * Kategooriate nimed
     * @return kategooriate nimed
     */

    public String[] getCbmCategories() {
        return cbmCategories;
    }

    /**
     * Seadistab uued kategooriate nimed
     * @param cbmCategories kategooriate massiiv
     */
    public void setCbmCategories(String[] cbmCategories) {
        this.cbmCategories = cbmCategories;
    }

    /**
     * Võllapuu pildid
     * @return Võllapuu pildid listina List<String>
     */
    public List<String> getImageFiles() {
        return imageFiles;
    }

    /**
     * @return DefaultTableModeli
     */
    public DefaultTableModel getDtm() {
        return dtm;
    }

    /**
     * Seadistan uue DefaultTabelModeli
     * @param dtm uus dtm
     */

    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
    }

    /**
     * Loetud edetabeli andmed andmebaasist
     * @return
     */
    public List<DataScore> getDataScores() {
        return dataScores;
    }

    /**
     * Muudab edetabeli andmed
     * @param dataScores uued andmed edetabeli jaoks
     */
    public void setDataScores(List<DataScore> dataScores) {
        this.dataScores = dataScores;
    }
}
