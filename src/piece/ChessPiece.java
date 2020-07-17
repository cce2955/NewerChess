package piece;



public class ChessPiece{
	private int id;
	//private String piece;
	private boolean color, first;
	private int x, y;
	private boolean active;
	public enum type{
		PAWN, HORSE, ROOK, BISHOP, KING, QUEEN, DUMMY;
	}
	public enum Color {
		WHITE, BLACK
	}

	public type TYPE;
	public Color COLOR;
	public ChessPiece(int id, type TYPE, Color COLOR, int x, int y) {
		super();
		this.id = id;
		this.TYPE = TYPE;
		this.COLOR = COLOR;
		this.x = x;
		this.y = y;
		this.active = true;
		this.first = true;
	}
	
	public ChessPiece() {
		super();
	}

	
	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isColor() {
		return color;
	}
	public void setColor(boolean color) {
		this.color = color;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public type getTYPE() {
		return TYPE;
	}

	public void setTYPE(type tYPE) {
		TYPE = tYPE;
	}

	public Color getCOLOR() {
		return COLOR;
	}

	public void setCOLOR(Color cOLOR) {
		COLOR = cOLOR;
	}
	
	

}
