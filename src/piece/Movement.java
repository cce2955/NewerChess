package piece;

//Quick little functional interface, I'll make a interface for each piece and then
//pass them through the interface for simple movement
@FunctionalInterface
public interface Movement {
	public int movePiece(int num);
}
