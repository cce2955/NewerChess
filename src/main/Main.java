package main;

import logic.BoardGeneration;
import logic.GameLogic;

public class Main {
	
	
	public static void main(String[] args) {
		GameLogic logic = new GameLogic();
		logic.start();
		
		while(!logic.isEndGame()) {
			logic.update();
		}
	}
}
