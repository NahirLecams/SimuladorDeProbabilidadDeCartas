package com.example.simuladorprobabilidadescartas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.util.Random;

public class SimuladorController {

    // Componentes inyectados desde Scene Builder (FXML)
    @FXML private TextArea txtAreaResultados;
    @FXML private ComboBox<String> cmbDevolverCarta;
    @FXML private Button btnPrimeraExtraccion;
    @FXML private Button btnSegundaExtraccion;

    // Estado del mazo y generador aleatorio
    private int cartasRojas = 10;
    private int cartasNegras = 10;
    private int totalCartas = 20;
    private Random generadorAleatorio = new Random();

    // Variables de control de la partida
    private boolean esRojaPrimera;

    @FXML
    public void initialize() {
        // Configuramos las opciones del ComboBox al iniciar la ventana
        cmbDevolverCarta.getItems().addAll("Sí", "No");
        cmbDevolverCarta.setValue("No"); // Valor por defecto

        // Bloqueamos la segunda extracción hasta que se haga la primera
        btnSegundaExtraccion.setDisable(true);
    }

    @FXML
    private void handlePrimeraExtraccion() {
        int numeroAleatorioPrimeraExtraccion = generadorAleatorio.nextInt(totalCartas) + 1;

        if (numeroAleatorioPrimeraExtraccion <= cartasRojas) {
            txtAreaResultados.appendText("Primera extracción: Carta ROJA (N° " + numeroAleatorioPrimeraExtraccion + ")\n");
            esRojaPrimera = true;
        } else {
            txtAreaResultados.appendText("Primera extracción: Carta NEGRA (N° " + numeroAleatorioPrimeraExtraccion + ")\n");
            esRojaPrimera = false;
        }

        // Desactivamos este botón y habilitamos el siguiente paso
        btnPrimeraExtraccion.setDisable(true);
        btnSegundaExtraccion.setDisable(false);
    }

    @FXML
    private void handleSegundaExtraccion() {
        String respuestaUsuario = cmbDevolverCarta.getValue();

        // Aplicamos la lógica de devolución según el ComboBox
        if (respuestaUsuario != null && respuestaUsuario.equalsIgnoreCase("No")) {
            if (esRojaPrimera) {
                cartasRojas--;
            } else {
                cartasNegras--;
            }
            totalCartas--;
            txtAreaResultados.appendText("-> Decisión: No devolver carta. Mazo actualizado (" + totalCartas + " cartas).\n");
        } else {
            txtAreaResultados.appendText("-> Decisión: Devolver carta al mazo.\n");
        }

        // Segunda extracción
        int numeroAleatorioSegunda = generadorAleatorio.nextInt(totalCartas) + 1;
        boolean esRojaSegunda;

        if (numeroAleatorioSegunda <= cartasRojas) {
            txtAreaResultados.appendText("Segunda extracción: Carta ROJA (N° " + numeroAleatorioSegunda + ")\n");
            esRojaSegunda = true;
        } else {
            txtAreaResultados.appendText("Segunda extracción: Carta NEGRA (N° " + numeroAleatorioSegunda + ")\n");
            esRojaSegunda = false;
        }

        // Evaluación final de probabilidades
        txtAreaResultados.appendText("\n--- RESULTADO FINAL ---\n");
        if (esRojaPrimera && esRojaSegunda) {
            txtAreaResultados.appendText("Combinación: ROJA y ROJA.\n");
        } else if (esRojaPrimera && !esRojaSegunda) {
            txtAreaResultados.appendText("Combinación: ROJA y NEGRA.\n");
        } else if (!esRojaPrimera && esRojaSegunda) {
            txtAreaResultados.appendText("Combinación: NEGRA y ROJA.\n");
        } else {
            txtAreaResultados.appendText("Combinación: NEGRA y NEGRA.\n");
        }
        txtAreaResultados.appendText("------------------------\n\n");

        // Desactivamos el botón final para terminar la ronda
        btnSegundaExtraccion.setDisable(true);
    }
}