package code39.controller;

import java.io.File;
import java.io.IOException;

import code39.model.Code39;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class GuiViewController {
	@FXML private MenuItem menuItemClose;
	@FXML private MenuItem menuItemOpen;
	@FXML private ImageView imgBarCode;
	@FXML private ImageView imgBarCodeBin;
	@FXML private TextField readedCodeTextField;
	@FXML private TextField StartStopTextField;
	@FXML private TextField checkSumReaded;
	@FXML private TextField checkSumCalculated;
	@FXML private MenuItem oMnieMenu;
	@FXML private ImageView imgResult;
	
	//https://www.barcodesinc.com/generator/index.php
	private Image img;
	private Image imageOK;
	private Image imageNOK;
	
	{
		imageOK = new Image(getClass().getResourceAsStream("/resources/OK.png"));
		imageNOK = new Image(getClass().getResourceAsStream("/resources/NOK.png"));
	}
	@FXML
	private void initialize(){
		
		//Akcja przy nacisnieciu menu close - zamkniecie aplikacji
		menuItemClose.setOnAction(event->{
			Platform.exit();
		});
		
		//Akcja przy nacisnieci menu open - wybranie pliku z Code39
		menuItemOpen.setOnAction(event->{
			FileChooser fileChooser = new FileChooser(); //utworzenie obiektu fileChooser
			fileChooser.setTitle("Choose Code39 file"); //przypisanie tytu�u dla stworzonego obiektu
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Pictures","*png")); //okre�lenie rozszerzenia wczytywanych plik�w
			File file = fileChooser.showOpenDialog(null); //wybranie pliku
			FileTest(file);
		});
		
		oMnieMenu.setOnAction(event->{
			showDialogOMnie();
		});
	}
	
	
	//METODA SPRAWDZAJACA PRAWIDLOWOSC PLIKU
	private void FileTest(File file){
		if (file!=null){
			//Podpiecie obrazka do ImageView
			try {
				img = new Image(file.toURI().toURL().toString());
				if (img!=null){ //Sprawdzenie czy rozmiar obrazu si� zgadza
					imgBarCode.setImage(img); //podpiecie wczytanego pliku do ImageView
					Code39 code39 = new Code39(img); //Utworzenie instancji do klasy procesowej i przekazanie Image jako parametr
					Image imgBin = code39.Code39_Binarization();
					imgBarCodeBin.setImage(imgBin);
					//code39.Code39_CheckWidth(imgBin); //Sprawdzenie grubosci linii
					code39.Code39_Read(imgBin); //Odczytanie grubosci linii i odpowiednie przydzielanie do dekodowania
					
					
					String strReaded = code39.compareCharBuildString(); //Sprawdzanie znakow
					readedCodeTextField.setText(strReaded);
					String daneBack = strReaded;
					String dane = strReaded.substring(1, strReaded.length() - 2);
					StartStopTextField.setText(dane);
					
					String sumaKontrolnaOdczytana = strReaded.substring(daneBack.length() - 2, daneBack.length() - 1);
					checkSumReaded.setText(sumaKontrolnaOdczytana);
					
					String sumaKontrolnaObliczona = code39.calcCheckSum();
					checkSumCalculated.setText(sumaKontrolnaObliczona);
					
					if (sumaKontrolnaObliczona.equals(sumaKontrolnaOdczytana)){
						imgResult.setImage(imageOK);
					}
					else{
						imgResult.setImage(imageNOK);
					}
						
				}
				
				else{
					ImgAlertDialog(); //Dialog z bledem
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else{
			FileAlertDialog(); //Dialog z bledem
		}
	}
	
	//Metoda wyrzucajaca dialog z bledem dla File
	private void FileAlertDialog(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("BLAD");
		alert.setHeaderText("BLAD - proces otwarcia pliku ");
		alert.setContentText("Problem z otwarciem plikuThe problem with opening the file");
		alert.showAndWait();
	}
	
	//Metoda wyrzucajaca dialog z bledem dla Img
	private void ImgAlertDialog(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("BLAD");
		alert.setHeaderText("BLAD - sprawdzenie pliku graficznego ");
		alert.setContentText("Problem z plikiem graficznym - nieprawid�owy rozmiar obrazu");
		alert.showAndWait();
	}
	
	private void showDialogOMnie(){
		System.out.println("AAA");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("O mnie");
		alert.setTitle("O mnie");
		alert.setContentText("Autor: Wojciech Antczak/ PW JAVA EE");
		alert.showAndWait();
	}

}
