package logic;

import java.util.ArrayList;
import java.util.Scanner;

import piece.ChessPiece;
import piece.ChessPiece.type;
import piece.Movement;

public class GameLogic {
	private enum Status{
		ASKPIECE,FINDPIECE,MOVEPIECE,
		FINDSPACE, ASKSPACE
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
	//Enums
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
		if(getrInput().equalsIgnoreCase("check")) {
			//little output debugger
			System.out.println("ID: " + gen.pieceArr.get(getId()).getId());
			System.out.println("X: " + gen.pieceArr.get(getId()).getX());
			System.out.println("Y: " + gen.pieceArr.get(getId()).getX());
			setDummy();
			
		}
		
		switch(STATUS) {
			case ASKPIECE:
				gen.updateBoard();
				askForPiece();
				break;
			case FINDPIECE:
				findAvailablePieces(getrInput());				
				break;
			case ASKSPACE:
				findAvailableSpace(getrInput());
				break;
			case FINDSPACE:
				moveToSpace(getrInput());
				break;
			default:
				askForPiece();
			}
		
	}
	private void setDummy() {
		//Sets the ID of the piece to dummy when applicable
		//There is a slight bug where the setID will overwrite the current
		//ID in some scenarios, so this will overwrite the ID with an 
		//unreachable piece
		for(int i = 0; i < gen.pieceArr.size(); i++) {
			if(gen.pieceArr.get(i).getTYPE().equals(type.DUMMY)) {
				setId(gen.getPieceArr().get(i).getId());
			}
		}
	}
	private boolean findCollision(String input) {
		//The input is a potential choice the user can choose from
		//If the potential choice happens to exist as a piece, 
		//we have a collision
		for(int i = 0; i < gen.pieceArr.size(); i++) {
			if(gen.pieceArr.get(i).getTYPE() != type.DUMMY
					&& input.equals(gen.letters[gen.pieceArr.get(i).getX()]
					+ gen.pieceArr.get(i).getY())) {
				return false;
			}		
		}
		//Apparently we're returning true if there is no collision
		//I'm done trying to wrestle with semantics
		return true;
	}
	
	private void moveToSpace(String input) {
		int x = 0;
		int y = Character.getNumericValue(input.charAt(1));
		for(int i = 0; i < gen.letters.length; i++) {
			if(input.charAt(0) == gen.letters[i].toLowerCase().charAt(0)) {
				//Actually find X
				x = i;
			}
		}//Get the XY value as usual
		if(gen.getPieceArr().get(getId()).getTYPE() != type.DUMMY
				//This will check if the second character is correct
				//I fully expect this to break in the future but for now
				//I'm letting this band aid ride until it falls off
				&& input.charAt(1) == Integer.toString(y).charAt(0)) {
			gen.pieceArr.get(getId()).setX(y);
			gen.pieceArr.get(getId()).setY(x);
			
		}
		
		STATUS = Status.ASKPIECE;	
	}
	
	private void findAvailableSpace(String input) {
		//local init
		int x = 0;
		//Assign Y from the input
		int y = 0;
		
		try { //This is the ugliest try catch I've done, I should
			//Just toss this in a method but I am too burnt out to care at the
			//Moment
			y = Character.getNumericValue(input.charAt(1));

			int potentialX = 0;
			int potentialY = 0;
			boolean pass = false;
			ArrayList<String> spaceArr = new ArrayList<>();
			spaceArr.clear();
			for(int i = 0; i < gen.letters.length; i++) {
				if(input.charAt(0) == gen.letters[i].toLowerCase().charAt(0)) {
					//Actually find X
					x = i;
				}
			}
			
			for (int i = 0; i < gen.pieceArr.size(); i++) {
				//X and Y are switched because I wasn't paying enough attention
				//This will be a problem later but that's for later me to complain
				//about, get your ctrl+ f ready
				if(gen.pieceArr.get(i).getX() == y
						&& gen.pieceArr.get(i).getY() == x) {
					setId(gen.pieceArr.get(i).getId());
				}
			}
			
			switch(gen.pieceArr.get(getId()).getTYPE()) {
				case PAWN:	
					//HERE'S THE PROBLEM!!!
					potentialX = Add.movePiece(gen.pieceArr.get(getId()).getY());
					if(!findCollision(gen.letters[potentialX] + y)) {
						spaceArr.add(gen.letters[potentialX] + y);
					}
					if(gen.pieceArr.get(getId()).isFirst() &&
							!findCollision(gen.letters[potentialX] 
									+ y)) {
						potentialX = Add.movePiece(potentialX);
						spaceArr.add(gen.letters[potentialX] + y);
						gen.pieceArr.get(getId()).setFirst(false);
					}
					
					pass = true;
				default:
					break;
			}
			System.out.println("Your available spaces are: ");
			for (int i = 0; i < spaceArr.size(); i++) {
				System.out.print(" [" + spaceArr.get(i) + "] ");
			}
			try {
				//A quick print to show the user what to enter
				System.out.println("Please input a number like " + spaceArr.get(0));
			} catch (IndexOutOfBoundsException e) {
				System.out.println("There are no spaces available");
				pass = false;
			}
			
			setrInput(this.input.nextLine());
			if (pass) {
				STATUS = Status.FINDSPACE;
			}else{
				STATUS = Status.ASKPIECE;
			}
			
		}catch (NumberFormatException e) {
			STATUS = Status.ASKPIECE;
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
		//Array that holds all potential spaces
		//If you choose a bishop and there are two bishops
		//available, it will show you the space of both bishops
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
		System.out.println("Which piece would you like to move to? type in the "
				+ "number, for example \"" + logicPieceArr.get(0) + "\" for "
				+ " for [" + logicPieceArr.get(0) +"] ");
		setrInput(this.input.nextLine());
		for(int i = 0; i < gen.pieceArr.size();i++) {
			if(gen.pieceArr.get(i).getTYPE() != type.DUMMY
					&& getrInput().toLowerCase().equals(
					gen.letters[gen.pieceArr.get(i).getY()].toLowerCase() 
					+ gen.pieceArr.get(i).getX()) 
					) {
				//If the user types in something valid, then do stuff
				STATUS = Status.ASKSPACE;
				break;
				
			}else {
				STATUS = Status.ASKPIECE;
			}
		}
		
	}
	
	private void askForPiece() {

		System.out.println("Type which piece you wish to use. \n (Ex. P for pawn, B for bishop, etc.)");
		for(int i = 0; i < gen.pieceArr.size(); i++) {
			if(gen.pieceArr.get(i).getTYPE().equals(type.DUMMY)) {
				setId(gen.getPieceArr().get(i).getId());
			}
		}
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