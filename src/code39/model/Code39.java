package code39.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Code39 {
	final static int width = 680; //stala szerokosc
	final static int height = 50; //stala wysokosc
	private List<Integer> charArray = new ArrayList<Integer>();
	private List<List<Integer>> arrayToCompare = new ArrayList<>();
	private Image img;
	private String daneWynikowe = null;
	
	public Code39(Image img){
		this.setImg(img);
	}
	
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	//METODA DO BINARYZACJI KODU W WERSJI SKALARNEJ
	public Image Code39_Binarization(){
		WritableImage dstImage = new WritableImage((int)img.getWidth(),(int)img.getHeight());
		PixelWriter writer = dstImage.getPixelWriter();

		try{
			PixelReader reader = img.getPixelReader();
			
			double red = 0;
			double green = 0;
			double blue = 0;
			
			for (int x = 0; x<(int)img.getWidth();x++){
				for (int y = 0; y<(int)img.getHeight();y++){

				Color color = reader.getColor(x, (int)img.getHeight()/2);
				red = color.getRed();
				green = color.getGreen();
				blue = color.getBlue();
				
				if (red < 0.3 ){ //Prog dolny
					red =0;
					green =0;
					blue =0;	
				}			
				else if (red>0.7) { //Prog gorny
					red = 1;
					green = 1;
					blue = 1;	
				}
				
				Color binColor = Color.color(red, green, blue);
				writer.setColor(x, y, binColor);
				}		
			}
		}
		catch (Exception e){
			
		}	
		return dstImage;
	}

	
	//METODA WYLICZAJACA SZEROKOSC LINII
	public void Code39_CheckWidth(Image img){
		try{
			PixelReader reader = img.getPixelReader();
			double red = 0;
			double green = 0;
			double blue = 0;
			int minWidth=0;
			int LineWidth=0;
			int maxLineWidth =0;
			int blankWidth=0;
			int maxBlankWidth =0;

			for (int x = 0; x<(int)img.getWidth();x++){
				Color color = reader.getColor(x, 0);
				red = color.getRed();
				green = color.getGreen();
				blue = color.getBlue();
				
				if (red < 0.2 ){
					LineWidth++;
					if (blankWidth>maxBlankWidth)maxBlankWidth = blankWidth;
					//System.out.println("Line width: "+LineWidth +" X: "+x+" Y: 0");
					blankWidth = 0;
					
				}
				else  {
					if (LineWidth>maxLineWidth) maxLineWidth = LineWidth;
					else minWidth = LineWidth;		 
					blankWidth++;
					//System.out.println("Blank width: "+blankWidth +" X: "+x+" Y: 0");
					LineWidth = 0;
				}
			}		
			//System.out.println("Min width: "+minWidth);
			//System.out.println("Max width: "+maxLineWidth);
		}
		
		catch(Exception e){
			
		}
	}
	
	//METODA WYLICZAJACA SZEROKOSC LINII
	public void Code39_Read(Image img){
		try{
			PixelReader reader = img.getPixelReader();
			double red = 0;
			double green = 0;
			double blue = 0;
			int minWidth=0;
			int LineWidth=0;
			int maxLineWidth =0;
			int blankWidth=0;
			int maxBlankWidth =0;
			boolean startNextSign = false;
			boolean lineSign = false;
			boolean blankSign = false;
			charArray.clear();
			int zmiennaArray = 0;
			
			for (int x = 0; x<(int)img.getWidth();x++){
				Color color = reader.getColor(x, 0);
				red = color.getRed();
				if (zmiennaArray==9)blankSign=false;

				if (red < 0.2 ){
					startNextSign = true;
					lineSign = true;
					if (blankSign){
						if (blankWidth>=4){charArray.add(2);zmiennaArray++;}
						else{ charArray.add(1);zmiennaArray++;}
					}

					blankSign = false;

					LineWidth++;
					if (blankWidth>maxBlankWidth)maxBlankWidth = blankWidth;
					blankWidth = 0;
				}
				else {
					if (startNextSign && zmiennaArray<9 ){

						blankSign = true;
						if (lineSign){
							if (LineWidth>=4){charArray.add(2);zmiennaArray++;}
							else{ charArray.add(1);zmiennaArray++;}	


						}
						lineSign = false;
						if (LineWidth>maxLineWidth) maxLineWidth = LineWidth;
						else minWidth = LineWidth;
						blankWidth++;
						LineWidth = 0;
					}
					else{
						zmiennaArray=0;
					}
				}
			}		
			
			splitBigArray(charArray);
		}
		
		catch (Exception e){
			
		}
	}
	
//================================DZIELENIE TABLICY ODCZYTANYCH ZNAKOW NA POSZCZEGOLNE LISTY 9 ELEMENTOWE
	public void splitBigArray(List<Integer> array){
		List<List<Integer>> listOfCharLists = new ArrayList<>(); 
		
		int end=9;
		boolean nextArray = true;
		int numberOfArrays = array.size()/9;
		List<Integer> list = new ArrayList<Integer>();
		int numer =0;
		int wskaznik=0;
		
		for (int i:array){
			if (end == 0){
				//System.out.println("Char number ["+numer+"]"+": "+list);
				list = new ArrayList<Integer>();
				nextArray = true;
				end = 9;
				numer ++;
			}
			
			if (nextArray){
				listOfCharLists.add(list);
				nextArray = false;
			}
			
			list.add(i);
			
			wskaznik++;
			end--;
		}
				
		arrayToCompare = listOfCharLists;
	}
	
	
//========================POROWNANIE ZNAKOW ODCZYTANYCH Z TABLICA I ZLOZENIE SLOWA====================================	
	public String compareCharBuildString(){
		Map<String,List<Integer>> charMap;
		SymbolArray symbol = new SymbolArray();
		charMap = symbol.getCharMap();
		StringBuilder strBuild = new StringBuilder();
		
		
		for (List<Integer> table:arrayToCompare){			
			for (Entry<String,List<Integer>> set:charMap.entrySet()){
				if (table.equals(set.getValue())){
					strBuild.append(set.getKey());
				}
			}
		}
		String dane = strBuild.toString();
		
		daneWynikowe = dane.substring(1, dane.length()-2);
		return strBuild.toString();
	}
	
//=================================OBLICZANIE SUMY KONTROLNEJ=============================================
	public String calcCheckSum(){
		String dane = daneWynikowe;
		Map<Integer,String> charCodeMap;
		SymbolArray symbol = new SymbolArray();
		charCodeMap = symbol.getCharCodeMap();
		char[] charArray = dane.toCharArray();
		
		int codeSum = 0;
		for (char a:charArray){			
			for (Entry<Integer,String> set:charCodeMap.entrySet()){
				char c = set.getValue().charAt(0);
				if (a == c){					
					codeSum+=set.getKey();
				}
			}
		}
		
		int kodZnaku = codeSum%43;
		String znakKontrolny = charCodeMap.get(kodZnaku);		
		return znakKontrolny;	
	}
}
