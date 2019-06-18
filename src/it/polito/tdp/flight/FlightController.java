package it.polito.tdp.flight;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		if(txtDistanzaInput.getText()==null ) {
			txtResult.setText("Inserire un numero nella casella!");
			return;
		}
		else {
		double k;
		try {
    		k = Double.parseDouble(txtDistanzaInput.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero");
    		return;
    	}
		model.creaGrafo(Double.parseDouble(txtDistanzaInput.getText()));
		if(model.testRaggiungibili()==true) {
			txtResult.setText("Ogni aereoporto può raggiungere gli altri\n");
			
		}
		else
			txtResult.setText("Non tutti gli aereoporti sono collegati\n");
		
	}txtResult.appendText("Aereoporto raggiungibile da Fiumicino più lontano: "+model.piùLontanoDaFiumicino());
		}

	@FXML
	void doSimula(ActionEvent event) {
		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
