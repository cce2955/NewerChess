package logic;

import java.util.ArrayList;
import java.util.Scanner;

import javax.transaction.xa.XAResource;

import org.junit.platform.commons.util.BlacklistedExceptions;

import piece.ChessPiece.Color;
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
	//Check color
	
	//Check ID of currently selected piece
	private int ID, X, Y, destinationX, destinationY;
	private String failString = "Not a valid piece, please try again";
	//When user is looking for a piece, store all active pieces in here
	//ex. If user wants a bishop and all that's available is a bishop at
	//[A3] and [F6], the program will locate those
	private ArrayList<String> choiceArr = new ArrayList<>();
	private ArrayList<Integer> arrX = new ArrayList<>();
	private ArrayList<Integer> arrY = new ArrayList<>();

	//Interface to calculate movement
	Movement xAdd = (int x) -> x + 1;
	Movement yAdd = (int y) -> y + 1;
	Movement xSub = (int x) -> x - 1;
	Movement ySub= (int y) -> y - 1;
	
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
		//Okay I was doing too much when I made this
		//Refactor incoming
		//Flow:
		//	Ask what piece they want (Pawn, bishop, rook, etc.)
		//	Generate possible spaces they can access from this piece
		//	Ask which space they wish to move to
		//	Move piece to space
		//	Loop
		//	Done, no fuss, get it done
		
		switch(STATUS) {
			case ASKPIECE:
				gen.updateBoard();
				askForPiece();
				break;
			case FINDPIECE:
				findAvailablePieces();
				System.out.println();
				System.out.println("Which space would you like to move to?");
				setrInput(input.nextLine());
				break;
			case MOVEPIECE:
				movePieces(getrInput());
				break;
			case AVAILABLESPACE:
				showAvailableSpaces();
				setrInput(input.nextLine());
				break;
			case ASKSPACE:
				moveToSpace(getrInput());
				break;
			default:
				askForPiece();
			}
		
	}
	private void moveToSpace(String input) {
		//After getting input, move to space. There are like 4 checks
		//before this so it's kind of redundant to check again unless I want
		//to be extra sure
		for (int i = 0; i < gen.pieceArr.size(); i++) {
			try {
				if(String.valueOf(input.charAt(0)).equals(
						gen.letters[gen.pieceArr.get(i).getX()])) {
					gen.pieceArr.get(i).setX(i);
					gen.pieceArr.get(i).setY(Integer.valueOf(input.charAt(1)));
				}
			}catch (NumberFormatException num) {
				System.out.println("No idea how you managed to throw an error "
						+ "this far, congrats");
			}catch (IndexOutOfBoundsException num) {
				
			}
			
		}
		System.out.println();
		STATUS = Status.ASKPIECE;
	}
	private boolean collision(int ID) {
		
		for(int i = 0; i < gen.pieceArr.size(); i++) {
			if (gen.pieceArr.get(ID).getX() == gen.pieceArr.get(i).getX()
					&& gen.pieceArr.get(ID).getY() 
					== gen.pieceArr.get(i).getY()
					&& ID != i) {
				//If two pieces occupy the same space, there's a collision
				return true;
			}
		}
		return false;
		
	}
	
	private boolean attackCollision(int ID) {
		for(int i = 0; i < gen.pieceArr.size(); i++) {
			switch(gen.pieceArr.get(ID).TYPE) {
			case PAWN:
				break;
			case BISHOP:
				break;
			case HORSE:
				break;
			case KING:
				break;
			case QUEEN:
				break;
			case ROOK:
				break;
			case DUMMY:
			default:
				break;
			}
		}
		
		return false;
	}
	private void showAvailableSpaces() {
		int spaceAvailable = 0;
		switch(gen.pieceArr.get(getID()).getTYPE()){
			case PAWN:
				//Assign X and Y
				X = getX();
				Y = getY();
				//If there's no collision, move the pawn one space ahead
				if(!collision(getID())) {
					if(gen.pieceArr.get(getID()).getCOLOR() == Color.BLACK) {
						X = xSub.movePiece(X);
						
						arrX.add(X);
						arrY.add(Y);
						
						spaceAvailable++;
					}else {
						
						X = xAdd.movePiece(X);
						
						arrX.add(X);
						arrY.add(Y);
			
						spaceAvailable++;
					}
				}
				
				if(gen.pieceArr.get(getID()).isFirst() && !collision(getID())){
					//If this is the pawn's first move, show a second space
					if(gen.pieceArr.get(getID()).getCOLOR() == Color.BLACK) {
						X = xSub.movePiece(X);
						arrX.add(X);
						arrY.add(Y);
						spaceAvailable++;
					}else {
						X = xAdd.movePiece(X);
						arrX.add(X);
						arrY.add(Y);
						spaceAvailable++;
					}
					//Set this pawn's second move to false so you cannot
					//do this again
					gen.pieceArr.get(getID()).setFirst(false);
				}
				
				break;
			case BISHOP:
				break;
			case HORSE:
				break;
			case KING:
				break;
			case QUEEN:
				break;
			case ROOK:
				break;
			case DUMMY:
			default:
				System.out.println("Nope");
				break;
		
		}
		if(collision(getID())) {
			System.out.println("Cannot move to that space");
			STATUS = Status.ASKPIECE;
		}else {
			
			System.out.println();
			System.out.println("For the " + gen.pieceArr.get(getID()).getTYPE() +
					" at " + getrInput().toUpperCase() + " you have "
					+ spaceAvailable + " spaces available: ");
			
			for(int i = 0; i < gen.letters.length; i++) {
				try {
					System.out.print(" [" + gen.letters[arrX.get(i)] + 
							arrY.get(i) + "] ");
				}catch (NullPointerException e) {
					
				}catch (IndexOutOfBoundsException num) {
					
				}
				
				
			}
			System.out.println("Which space would you like to move to? ");
			STATUS = Status.ASKSPACE;
			
		}
	}
	
	private void movePieces(String input) {
		//Get space, convert it to an XY coordinate
		boolean fail = false;
		//Throw user out for a bad input
		
		
		try {
			setY(Integer.valueOf(String.valueOf(input.charAt(1))));
		}catch(NullPointerException e) {
			System.out.println(failString);
			STATUS = Status.ASKPIECE;
			fail = true;
		}catch(NumberFormatException num) {
			System.out.println(failString);
			STATUS = Status.ASKPIECE;
			fail = true;
		}
		try {
			//If the first letter matches the letter array
			//append the index value
			for(int i = 0; i < gen.letters.length; i++) {
				if(String.valueOf(
						input.charAt(0)).equals(gen.letters[i].toLowerCase())
						&& !fail){		
					setX(i);
					fail = false;
				}
			}	
		}catch (NullPointerException e) {
			System.out.println(failString);
			STATUS = Status.ASKPIECE;
			fail = true;
		}
		
		if(!fail) {
			for(int i = 0; i < gen.pieceArr.size(); i++) {
				System.out.println((getX()+1) + " " + (getY()));
				System.out.println((gen.pieceArr.get(i).getX()+1) +
						" " + (gen.pieceArr.get(i).getY()) + " " 
						+ gen.pieceArr.get(i).getTYPE());
				if(gen.pieceArr.get(i).getX()  == getX() 
						&& gen.pieceArr.get(i).getY() == getY()) {
					//Set the ID for easy access later
					System.out.println("ID Accessed");
					setID(gen.pieceArr.get(i).getId());
				}
						
			}
			STATUS = Status.AVAILABLESPACE;
		}else {
			setID(gen.pieceArr.size());
			STATUS = Status.ASKPIECE;
		}
		
		
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
	
	private void findAvailablePieces() {
		choiceArr.clear();
		 
		for(int i = 0; i < gen.pieceArr.size(); i++) {
			
			if(getrInput().equalsIgnoreCase(String.valueOf(
					gen.pieceArr.get(i).getTYPE().toString().charAt(0)))){
				if(PLAYERCOLOR == PlayerColor.BLACK 
						&& gen.pieceArr.get(i).getCOLOR() == Color.BLACK
						&& gen.pieceArr.get(i).isActive()) {
					System.out.print("[" + gen.letters
							[gen.pieceArr.get(i).getY()]
							+ (gen.pieceArr.get(i).getX() + 1) + "]" );
					choiceArr.add(gen.letters[gen.pieceArr.get(i).getY()] 
							+ (gen.pieceArr.get(i).getX() + 1));
					setID(gen.pieceArr.get(i).getId());
					//Print out all black pieces that are currently active
					//Add them to array to test against user input
					//SetID finds the ID which gives us all info, the only 
					//relevant info I need from this ID is the piece type
				}
				if(PLAYERCOLOR == PlayerColor.WHITE 
						&& gen.pieceArr.get(i).getCOLOR() == Color.WHITE
						&& gen.pieceArr.get(i).isActive()) {
					System.out.print("[" + gen.letters
							[gen.pieceArr.get(i).getY()]
									+ (gen.pieceArr.get(i).getX() +1) + "]" );
					choiceArr.add(gen.letters[gen.pieceArr.get(i).getY()]
							+ (gen.pieceArr.get(i).getX() + 1));
					setID(gen.pieceArr.get(i).getId());
					//Print out all white pieces that are currently active
				}
			}
		}
		STATUS = Status.MOVEPIECE;
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
	private int getID() {
		return ID;
	}
	private void setID(int iD) {
		ID = iD;
	}
	private int getX() {
		return X;
	}
	private void setX(int x) {
		X = x;
	}
	private int getY() {
		return Y;
	}
	private void setY(int y) {
		Y = y;
	}
	
	
}
