package Backend.src.chess;
/**
 * Abstract subclass, inherits piece type,location, and color.
 * Have an individual logic of available moves for King.
 * @author Matthew Yu, James Checca
 */
public class King extends ChessPiece
{
	public King(String type, String loc, Boolean isWhite)
	{
		super(type, loc, isWhite);
	}

	/**
	 * King can't be castle if moved is true
	 */
	public boolean moved = false;

	/**
	 *
	 * @param dst checking this.piece can move to dst location
	 * @param board board that is checking with
	 * @param game game that is checking with
	 * @return true if this can ove to dst location, false otherwise.
	 */
	public boolean canMove(String dst, ChessPiece[][] board, Chess game)
	{
		int[] src = game.fileRankToIndex(this.loc);
		int[] dest = game.fileRankToIndex(dst);
		if (src[0] == -1 || src[1] == -1 || dest[0] == -1 || dest[1] == -1)
		{
			return false;
		}
		if (src == dest)
		{
			return false;
		}
		int rowDiff = Math.abs(src[0] - dest[0]);
		int colDiff = Math.abs(src[1] - dest[1]);

		if (rowDiff > 1 || colDiff > 1)
		{
			return false;
		}

		if (board[dest[0]][dest[1]] == null)
		{
			moved = true;
			return true;
		}
		else if ((board[dest[0]][dest[1]].isWhite && !this.isWhite) || (!board[dest[0]][dest[1]].isWhite && this.isWhite))
		{
			moved = true;
			return true;
		}

		return false;
	}
}
