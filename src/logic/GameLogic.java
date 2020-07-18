package logic;

import java.util.ArrayList;
import java.util.Scanner;

import piece.Movement;

public class GameLogic {
	private enum Status{
		ASKPIECE,FINDPIECE,MOVEPIECE,
		AVAILABLESPACE, ASKSPACE
	}
	private enum PlayerColor{
		WHITE, BLACK
	}
	//Access the chesspiece Array
	BoardGeneration gen = new BoardGeneration();
	//Input for user
	private Scanner input = new Scanner(System.in);
	//store user input
	private String rInput;
	//If true, end entire game, as long as this is set to false, keep game going
	private boolean endGame;
	private int id;
	//Interface to calculate movement
	Movement Add = (int x) -> x + 1;
	Movement Sub = (int x) -> x - 1;
	Status STATUS;
	PlayerColor PLAYERCOLOR;
	public void start() {
		gen.generatePieces();
		getColor();
		STATUS = Status.ASKPIECE;
		//gen.updateBoard();
		setEndGame(false);//Force game in endless loop
	}
	public void update() {
		switch(STATUS) {
			case ASKPIECE:
				gen.updateBoard();
				askForPiece();
				break;
			case FINDPIECE:
				findAvailablePieces(getrInput());				
				break;
			default:
				askForPiece();
			}
		
	}
	private void findAvailablePieces(String input) {
		
		gen.pieceArr.forEach(item->{
			if (item.getTYPE().toString().toLowerCase().charAt(0) == 
					getrInput().charAt(0) &&
					PLAYERCOLOR.toString().equals(item.getCOLOR().toString())){
				setId(item.getId());
		//Grabbing the ID so we can just grab the type to print out
		//the spaces
			}
		});
		
		ArrayList<String> logicPieceArr = new ArrayList<>();
		logicPieceArr.clear();
		int spaceCounter = 0;
		for (int i = 0; i < gen.pieceArr.size(); i++) {
			if (gen.pieceArr.get(i).getTYPE() == 
					gen.pieceArr.get(getId()).getTYPE()) {
				if(PLAYERCOLOR.toString().equals(
						gen.pieceArr.get(i).getCOLOR().toString())) {
					logicPieceArr.add(gen.letters[gen.pieceArr.get(i).getY()] 
							+ gen.pieceArr.get(i).getX());
					spaceCounter++;
				}
				
			}
		}
		System.out.println("Your " + gen.pieceArr.get(getId()).getCOLOR() + " " 
				+ gen.pieceArr.get(getId()).getTYPE() + 
				" has " + spaceCounter +" spaces: ");
		for(int i = 0; i < logicPieceArr.size(); i++) {
			System.out.print(" [" + logicPieceArr.get(i) + "] ");
		}
		System.out.println();
		System.out.println("Which piece would you like to move? type in the "
				+ "number, for example \"" + logicPieceArr.get(0) + "\" for "
				+ " for [" + logicPieceArr.get(0) +"] ");
	
		STATUS = Status.ASKPIECE;
	}
	private void askForPiece() {

		System.out.println("Type which piece you wish to use. \n (Ex. P for pawn, B for bishop, etc.)");
		setrInput(input.nextLine());
		while(!getrInput().equalsIgnoreCase("p") && 
				!getrInput().equalsIgnoreCase("h") &&
				!getrInput().equalsIgnoreCase("b") &&
				!getrInput().equalsIgnoreCase("r") &&
				!getrInput().equalsIgnoreCase("q") &&
				!getrInput().equalsIgnoreCase("k")) {
			System.out.println("Not a valid input, please type in a piece");
			setrInput(input.nextLine());
		}
		
		STATUS = Status.FINDPIECE;
	}
	private void getColor() {
		System.out.println("Which color do you wish to use? (Type White "
				+ "or Black)");
		setrInput(input.nextLine());
		
		while(!getrInput().equalsIgnoreCase("white") &&
				!getrInput().equalsIgnoreCase("w") &&
				!getrInput().equalsIgnoreCase("b") &&
				!getrInput().equalsIgnoreCase("black")) {
			System.out.println("Not a valid input, please input black or "
					+ "white");
			setrInput(input.nextLine());
		}
		
		if(getrInput().equalsIgnoreCase("white") ||
				getrInput().equalsIgnoreCase("w")){
			PLAYERCOLOR = PlayerColor.WHITE;
		} else {
			PLAYERCOLOR = PlayerColor.BLACK;
		}
	}
	
	public boolean isEndGame() {
		return endGame;
	}

	private void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}
	private String getrInput() {
		return rInput;
	}
	private void setrInput(String rInput) {

		this.rInput = rInput;
	}
	private int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}
	
}