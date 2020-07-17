package board;

public class ChessBoard {
	private int spaces;
	private int rows;
	private int columns;
	
	
	
	public ChessBoard(int rows, int columns) {
		super();
		this.rows = rows;
		this.columns = columns;
		validateSpaces();
	}
	private void validateSpaces() {
		setSpaces(columns * rows);
	}
	public int getSpaces() {
		return spaces;
	}
	public void setSpaces(int spaces) {
		this.spaces = spaces;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	
}
