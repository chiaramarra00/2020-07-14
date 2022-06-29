/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Statistiche;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	txtResult.clear();
    	Team squadra = cmbSquadra.getValue();
    	if (squadra == null) {
    		txtResult.setText("Selezionare una squadra.\n");
    		return;
    	}
    	List<Adiacenza> squadreMigliori = model.doSquadreMigliori(squadra);
    	txtResult.setText("SQUADRE MIGLIORI: \n");
    	for (Adiacenza a : squadreMigliori) {
    		txtResult.appendText(a.getT2()+"("+a.getPeso()+")\n");
    	}
    	List<Adiacenza> squadrePeggiori = model.doSquadrePeggiori(squadra);
    	txtResult.appendText("\nSQUADRE PEGGIORI: \n");
    	for (Adiacenza a : squadrePeggiori) {
    		txtResult.appendText(a.getT2()+"("+a.getPeso()+")\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	model.creaGrafo();
    	cmbSquadra.getItems().setAll(model.getIdMap().values());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	if (!model.grafoCreato()) {
    		txtResult.setText("Creare un grafo.\n");
    		return;
    	}
    	int N;
    	try {
    		N = Integer.parseInt(txtN.getText());
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    		return;
    	}
    	int X;
    	try {
    		X = Integer.parseInt(txtX.getText());
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    		return;
    	}
    	Statistiche stat = model.simula(N,X);
    	txtResult.setText("Numero di reporter che hanno assistito, in media, ad ogni partita: "+stat.getMediaReporter()+"\n");
    	txtResult.appendText("Numero di partite per cui il numero totale di reporter era critico: "+stat.getNumPartiteCritiche());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
