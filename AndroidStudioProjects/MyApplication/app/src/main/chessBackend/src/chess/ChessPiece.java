package Backend.src.chess;

/**
 * Super class of chess pieces.
 * @author Matthew Yu, James Checca
 */
public abstract class ChessPiece
{
	String type, loc;
	Boolean isWhite;
	Boolean moved = false;

	public ChessPiece(String type, String loc, Boolean isWhite)
	{
		this.type = type;
		this.loc = loc;
		this.isWhite = isWhite;
	}

	/**
	 * Abstrat method that determine if this can move to destiniation.
	 * @param dst destnation index
	 * @param board board that is checking with
	 * @param game game that is checking with.
	 * @return
	 */
	protected abstract boolean canMove(String dst, ChessPiece[][] board, Chess game);
}
