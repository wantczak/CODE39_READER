package code39.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolArray {
	private Map<String,List<Integer>> charMap = new HashMap<>();
	private Map<Integer,String> charCodeMap = new HashMap<>();

	private static final String code39signs[] = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z", "-", ".", " ", "$",
			"/", "+", "%","*"};
			 
			
	private static final Integer code39bars[][] = {			
			{1,1,1,2,2,1,2,1,1},		
			{2,1,1,2,1,1,1,1,2},
			{1,1,2,2,1,1,1,1,2},
			{2,1,2,2,1,1,1,1,1},
			{1,1,1,2,2,1,1,1,2},
			{2,1,1,2,2,1,1,1,1},
			{1,1,2,2,2,1,1,1,1},
			{1,1,1,2,1,1,2,1,2},
			{2,1,1,2,1,1,2,1,1},
			{1,1,2,2,1,1,2,1,1},
			{2,1,1,1,1,2,1,1,2},
			{1,1,2,1,1,2,1,1,2},
			{2,1,2,1,1,2,1,1,1},
			{1,1,1,1,2,2,1,1,2},
			{2,1,1,1,2,2,1,1,1},
			{1,1,2,1,2,2,1,1,1},
			{1,1,1,1,1,2,2,1,2},
			{2,1,1,1,1,2,2,1,1},
			{1,1,2,1,1,2,2,1,1},
			{1,1,1,1,2,2,2,1,1},
			{2,1,1,1,1,1,1,2,2},
			{1,1,2,1,1,1,1,2,2},
			{2,1,2,1,1,1,1,2,1},
			{1,1,1,1,2,1,1,2,2},
			{2,1,1,1,2,1,1,2,1},
			{1,1,2,1,2,1,1,2,1},	
			{1,1,1,1,1,1,2,2,2},
			{2,1,1,1,1,1,2,2,1},
			{1,1,2,1,1,1,2,2,1},
			{1,1,1,1,2,1,2,2,1},
			{2,2,1,1,1,1,1,1,2},
			{1,2,2,1,1,1,1,1,2},
			{2,2,2,1,1,1,1,1,1},
			{1,2,1,1,2,1,1,1,2},
			{2,2,1,1,2,1,1,1,1},
			{1,2,2,1,2,1,1,1,1},
			{1,2,1,1,1,1,2,1,2},
			{2,2,1,1,1,1,2,1,1},
			{1,2,2,1,1,1,2,1,1},
			{1,2,1,2,1,2,1,1,1},
			{1,2,1,2,1,1,1,2,1},
			{1,2,1,1,1,2,1,2,1},
			{1,1,1,2,1,2,1,2,1},
			{1,2,1,1,2,1,2,1,1}
			};
	
	private static final Integer code39charCode[] = {0,1,2,3,4,5,6,7,8,9,10,
			11,12,13,14,15,16,17,18,19,20,
			21,22,23,24,25,26,27,28,29,30,
			31,32,33,34,35,36,37,38,39,40,
			41,42,null};
	
	//BLOK INSTANCYJNY
	{
		fillCharMap();
		fillCharCodeMap();
	}
	
	public Integer[][] getcode39Bar(){
		return code39bars;
	}
	
	public String[] getcode39Signs(){
		return code39signs;
	}
	
	public Map<String,List<Integer>> getCharMap(){
		return this.charMap;
	}
	
	public Map<Integer,String> getCharCodeMap(){
		return this.charCodeMap;
	}

	
	private void fillCharMap(){
		charMap.clear();
		for(int i = 0;i<code39bars.length;i++){
			charMap.put(code39signs[i], Arrays.asList(code39bars[i]));
		}
	}
	
	private void fillCharCodeMap(){
		charCodeMap.clear();
		for(int i = 0;i<code39charCode.length;i++){
			charCodeMap.put(code39charCode[i],code39signs[i]);
		}
	}


}
