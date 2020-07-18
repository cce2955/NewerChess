package logic;

import java.util.ArrayList;

import board.ChessBoard;
import piece.ChessPiece;
import piece.ChessPiece.Color;
import piece.ChessPiece.type;

public class BoardGeneration {
	
	ChessBoard board = new ChessBoard(8, 8 );
	ChessPiece piece = new ChessPiece();
	ArrayList<ChessPiece> pieceArr = new ArrayList<>();
	public String[] letters = {"A","B","C","D","E","F","G","H","I"};
	
	//Right now this works via console, but I may redo this with JFrame later
	public void generatePieces() {
		for(int i = 0; i <= board.getColumns(); i++) {
			//I was going to make the grid dynamic, but I don't have the math
			//for this yet
			if(i > 0) {
				pieceArr.add(new ChessPiece(i, type.PAWN, Color.WHITE, i, 1));
				pieceArr.add(new ChessPiece(i, type.PAWN, Color.BLACK, i, 6));
				switch(i) {
				case 1:
					pieceArr.add(new ChessPiece(i, type.ROOK, Color.WHITE, i, 0));
					pieceArr.add(new ChessPiece(i, type.ROOK, Color.BLACK, i, 7));
					pieceArr.add(new ChessPiece(i, type.ROOK, Color.WHITE, i + 7, 0));
					pieceArr.add(new ChessPiece(i, type.ROOK, Color.BLACK, i + 7, 7));
					break;
				case 2:
					pieceArr.add(new ChessPiece(i, type.HORSE, Color.WHITE, i, 0));
					pieceArr.add(new ChessPiece(i, type.HORSE, Color.BLACK, i, 7));
					pieceArr.add(new ChessPiece(i, type.HORSE, Color.WHITE, i + 5, 0));
					pieceArr.add(new ChessPiece(i, type.HORSE, Color.BLACK, i + 5, 7));
					break;
				case 3:
					pieceArr.add(new ChessPiece(i, type.BISHOP, Color.WHITE, i, 0));
					pieceArr.add(new ChessPiece(i, type.BISHOP, Color.BLACK, i, 7));
					pieceArr.add(new ChessPiece(i, type.BISHOP, Color.WHITE, i + 3, 0));
					pieceArr.add(new ChessPiece(i, type.BISHOP, Color.BLACK, i + 3, 7));
					break;
				case 4:
					pieceArr.add(new ChessPiece(i, type.QUEEN, Color.WHITE, i, 0));
					pieceArr.add(new ChessPiece(i, type.QUEEN, Color.BLACK, i, 7));
					break;
				case 5:
					pieceArr.add(new ChessPiece(i, type.KING, Color.WHITE, i, 0));
					pieceArr.add(new ChessPiece(i, type.KING, Color.BLACK, i, 7));
				
				}
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
		int x = 1;
		boolean pass = true;
		for (int i = 1; i <= board.getSpaces(); i++) {
			for (int lower = 0; lower < pieceArr.size(); lower++) {
				if(pieceArr.get(lower).getX() == x
						&& pieceArr.get(lower).getY() == y) {
					switch(pieceArr.get(lower).getCOLOR()) {
					case BLACK:
						System.out.print(" [B" + 
								pieceArr.get(lower).getTYPE().toString().charAt(0) +
								"] ");
						pass = false;
						break;
					case WHITE:
						System.out.print(" [W" + 
								pieceArr.get(lower).getTYPE().toString().charAt(0) +
								"] ");
						pass = false;
						break;
					default:
						break;
					}	
				}	
			}
			if(pass) {
				System.out.print(" [" + letters[y] + (x) + "] ");
			}
			
			if (i % 8 == 0) {
				x = 0;
				y++;
				System.out.println();
			}
			pass = true;
			x++;
			
		}
		System.out.println();
	}

	public ArrayList<ChessPiece> getPieceArr() {
		return pieceArr;
	}
	
}


