package com.example.pj2_2024.Simulacija;

import com.example.pj2_2024.HelloController;
import com.example.pj2_2024.Iznajmljivanje.Iznajmljivanje;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Simulacija {
    private static final int PAUZA = 5000;
    private final Map<Date, List<Iznajmljivanje>> grupisanaIznajmljivanja;
    private final Label datum;

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

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
                    synchronized (Iznajmljivanje.getLock()) {
                        updateGrid(lista);
                        Iznajmljivanje.getLock().notify();
                    }
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

                for(Iznajmljivanje iznajmljivanje : lista) {
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

                timeline.stop();
                Platform.runLater(() -> HelloController.getPane().getChildren().removeIf(node -> node instanceof StackPane));
            }
        }).start();
    }

    private void updateGrid(List<Iznajmljivanje> iznajmljivanja) {
        Platform.runLater(() -> HelloController.getPane().getChildren().removeIf(node -> node instanceof StackPane));
        for(Iznajmljivanje iznajmljivanje : iznajmljivanja) {
            Rectangle rectangle = new Rectangle(40, 20, iznajmljivanje.getVozilo().getColor());
            Text text = new Text(iznajmljivanje.getVozilo().getId() + "-" + iznajmljivanje.getVozilo().getNivoBaterije() + "%");
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rectangle, text);
            Platform.runLater(() -> HelloController.getPane().add(stackPane, iznajmljivanje.getCurrentPos()[0], iznajmljivanje.getCurrentPos()[1]));
        }
    }
}
