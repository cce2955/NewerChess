package board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChessBoardTest {

	@Test
	void checkSpaces() {
		ChessBoard board = new ChessBoard(8, 8);
		assertEquals(64, board.getSpaces());
		
	}
	
	@Test
	void checkSpaces2() {
		ChessBoard board = new ChessBoard(10, 10);
		assertEquals(100, board.getSpaces());
		
	}
	
	
}
