package listeners;

import models.Model;
import views.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonNew implements ActionListener {
    private Model model;
    private View view;
    public ButtonNew(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      //  System.out.println("Klikk");
        view.hideButtons();
        if(!view.getGameTimer().isRunning()) {// Mängu aeg ei jookse
            view.getGameTimer().setSeconds(0); // Sekundid nullida
            view.getGameTimer().setMinutes(0); // Minutid nullida
            view.getGameTimer().setRunning(true); //Aeg jooksma
            view.getGameTimer().startTime(); // Käivita aeg
        } else {
            view.getGameTimer().stopTime();
            view.getGameTimer().setRunning(false);
        }
    // TODO siit jätkub õpilaste arendus

    }
}
