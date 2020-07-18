package logic;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.transaction.xa.XAResource;

import org.junit.platform.commons.util.BlacklistedExceptions;

import piece.ChessPiece.Color;
import piece.ChessPiece.type;
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
	private String rInput, type;
	//If true, end entire game, as long as this is set to false, keep game going
	private boolean endGame;
	//Check color
	private String failString = "Not a valid piece, please try again";
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
					PLAYERCOLOR.toString().equals(item.getCOLOR().toString())) {
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
					logicPieceArr.add(gen.letters[gen.pieceArr.get(i).getX()] 
							+ gen.pieceArr.get(i).getY());
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
	private String getType() {
		return type;
	}
	private void setType(String type) {

		this.type = type;
	}
	private int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}
	
}