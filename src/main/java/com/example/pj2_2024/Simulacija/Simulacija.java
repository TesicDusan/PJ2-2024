package com.example.pj2_2024.Simulacija;

import com.example.pj2_2024.HelloController;
import com.example.pj2_2024.Iznajmljivanje.Iznajmljivanje;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CyclicBarrier;

public class Simulacija {
    private static final int PAUZA = 5000;
    private final Map<Date, List<Iznajmljivanje>> grupisanaIznajmljivanja;
    private Label datum;

    public Simulacija(Map<Date, List<Iznajmljivanje>> grupisanaIznajmljivanja, HelloController controller) {
        this.grupisanaIznajmljivanja = new TreeMap<>(grupisanaIznajmljivanja);
        this.datum = controller.getDatum();
    }

    public void pokreni() {
        new Thread(() -> {
            for (Map.Entry<Date, List<Iznajmljivanje>> entry : grupisanaIznajmljivanja.entrySet()) {
                System.out.println(entry.getKey());
                Platform.runLater(() -> datum.setText(entry.getKey().toString()));
                List<Iznajmljivanje> lista = entry.getValue();

                CyclicBarrier barrier = new CyclicBarrier(lista.size(), () -> Platform.runLater(() -> updateGrid(lista)));

                for(Iznajmljivanje iznajmljivanje : lista) {
                    iznajmljivanje.setBarrier(barrier);
                    iznajmljivanje.start();
                }

                for (Iznajmljivanje iznajmljivanje : lista) {
                    try {
                        iznajmljivanje.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(PAUZA);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> HelloController.getPane().getChildren().removeIf(node -> node instanceof StackPane));
            }
        }).start();
    }

    private void updateGrid(List<Iznajmljivanje> iznajmljivanja) {
        for(Iznajmljivanje iznajmljivanje : iznajmljivanja) {
            Rectangle rectangle = new Rectangle(40, 20, iznajmljivanje.getVozilo().getColor());
            Text text = new Text(iznajmljivanje.getVozilo().getId() + "-" + iznajmljivanje.getVozilo().getNivoBaterije() + "%");
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rectangle, text);
            HelloController.getPane().add(stackPane, iznajmljivanje.getCurrentPos()[0], iznajmljivanje.getCurrentPos()[1]);
        }
    }
}
