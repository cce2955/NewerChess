package piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import piece.ChessPiece.Color;
import piece.ChessPiece.type;

class PieceTest {
	
	@Test
	void test() {
		ChessPiece piece = new ChessPiece(0, type.PAWN, Color.BLACK, 1, 2);
		assertEquals(1, piece.getX());
		assertEquals(2, piece.getY());
	}
	
	@Test
	void movement(){
		ChessPiece piece = new ChessPiece(0, type.PAWN, Color.BLACK, 1, 2);
		Movement movementInterface = (x)-> x+1;
		System.out.println(piece.getX());
		piece.setX(movementInterface.movePiece(piece.getX()));
		System.out.println(piece.getX());
		assertEquals(2, piece.getX());
	}

	

}
