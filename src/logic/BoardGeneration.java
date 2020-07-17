package logic;

import java.util.ArrayList;

import board.ChessBoard;
import piece.ChessPiece;
import piece.ChessPiece.Color;
import piece.ChessPiece.type;

public class BoardGeneration {
	
	ChessBoard board = new ChessBoard(8, 8);
	ChessPiece piece = new ChessPiece();
	private int numPieces = getNumPieces();
	ArrayList<ChessPiece> pieceArr = new ArrayList<>();
	public String[] letters = {"A","B","C","D","E","F","G","H","I"};
	private int getNumPieces(){
		return (board.getColumns() * board.getRows() / 2);
	}
	//Right now this works via console, but I may redo this with JFrame later
	public void generatePieces() {
		for(int i = 0; i < board.getColumns(); i++) {
			//I was going to make the grid dynamic, but I don't have the math
			//for this yet
			pieceArr.add(new ChessPiece(i, type.PAWN, Color.WHITE, i, 1));
			pieceArr.add(new ChessPiece(i, type.PAWN, Color.BLACK, i, 6));
			switch(i) {
			case 0:
				pieceArr.add(new ChessPiece(i, type.ROOK, Color.WHITE, i, 0));
				pieceArr.add(new ChessPiece(i, type.ROOK, Color.BLACK, i, 7));
				pieceArr.add(new ChessPiece(i, type.ROOK, Color.WHITE, i + 7, 0));
				pieceArr.add(new ChessPiece(i, type.ROOK, Color.BLACK, i + 7, 7));
				break;
			case 1:
				pieceArr.add(new ChessPiece(i, type.HORSE, Color.WHITE, i, 0));
				pieceArr.add(new ChessPiece(i, type.HORSE, Color.BLACK, i, 7));
				pieceArr.add(new ChessPiece(i, type.HORSE, Color.WHITE, i + 5, 0));
				pieceArr.add(new ChessPiece(i, type.HORSE, Color.BLACK, i + 5, 7));
				break;
			case 2:
				pieceArr.add(new ChessPiece(i, type.BISHOP, Color.WHITE, i, 0));
				pieceArr.add(new ChessPiece(i, type.BISHOP, Color.BLACK, i, 7));
				pieceArr.add(new ChessPiece(i, type.BISHOP, Color.WHITE, i + 3, 0));
				pieceArr.add(new ChessPiece(i, type.BISHOP, Color.BLACK, i + 3, 7));
				break;
			case 3:
				pieceArr.add(new ChessPiece(i, type.QUEEN, Color.WHITE, i, 0));
				pieceArr.add(new ChessPiece(i, type.QUEEN, Color.BLACK, i, 7));
				break;
			case 4:
				pieceArr.add(new ChessPiece(i, type.KING, Color.WHITE, i, 0));
				pieceArr.add(new ChessPiece(i, type.KING, Color.BLACK, i, 7));
			
			}
		}
		pieceArr.add(new ChessPiece(pieceArr.size(), type.DUMMY, Color.WHITE, 100, 100));
		//Set a unique ID for each piece, moreso for debugging than anything else
		//Might be used to fetch during user input
		for(int i = 0; i < pieceArr.size(); i++) {
			try{
				pieceArr.get(i).setId(i);
			}catch(IndexOutOfBoundsException e) {
				
			}
		}
	}
	
	public void updateBoard() {
		
		int y = 0;
		int xCorrect = 0;
		boolean pass = false;
		int piece = 0;
		for(int x = 0; x < board.getSpaces(); x++) {
			pass = false;
			//Reset the X after 8 passes so you can maintain the XY value
			//ex (0,1)(0,2)...(0,7)(1,0)(1,1)
			if (xCorrect % 8 == 0) {
				xCorrect = 0;
			}
			if(x > 1 && x % 8 == 0) {
				System.out.println();
				y++;
			}
			for(int i = 0; i < pieceArr.size(); i++) {
				if(pieceArr.get(i).getX() == xCorrect && pieceArr.get(i).getY() == y) {
					pass = true;
					piece = i;
				}
			}
			xCorrect++;
			if (pass) {
				//If the piece passed all the checks, print out the piece it is
				//If the player is black, attach a B in front of the piece
				if(pieceArr.get(piece).getCOLOR() == Color.BLACK) {
					System.out.print(" [B" + pieceArr.get(piece).getTYPE().toString().charAt(0) +  "] ");
				} else {
					System.out.print(" [W" + pieceArr.get(piece).getTYPE().toString().charAt(0) +  "] ");
				}
				
			} else {
				//Otherwise just print out the gridspace
				System.out.print(" [" + letters[y] + xCorrect +  "] ");
			}
		}
		System.out.println();
		System.out.println();
	}

	public ArrayList<ChessPiece> getPieceArr() {
		return pieceArr;
	}
	
}


