package Backend.src.chess;
/**
 * Abstract subclass, inherits piece type,location, and color.
 * Have an individual logic of available moves for Pawn.
 * @author Matthew Yu, James Checca
 */
public class Pawn extends ChessPiece
{

	public Pawn(String type, String loc, Boolean isWhite)
	{
		super(type, loc, isWhite);
	}
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
		int rowDiff;
		if (this.isWhite)
		{
			rowDiff = src[0] - dest[0];
			// Check single space move and diagonal attack
			if (rowDiff == 1)
			{
				// Open space, non-diagonal
				if (src[1] == dest[1] && board[dest[0]][dest[1]] == null)
				{
					return true;
				}
				// Diagonal attack valid
				else if (Math.abs(src[1] - dest[1]) == 1 && Math.abs(src[0] - dest[0]) == 1 && board[dest[0]][dest[1]] != null &&  !board[dest[0]][dest[1]].isWhite)
				{
					return true;
				}
			}
			// Check double space move
			else if (rowDiff == 2)
			{
				if (src[1] == dest[1] && src[0] == 6 && board[src[0]-1][src[1]] == null && board[dest[0]][dest[1]] == null)
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			rowDiff = dest[0] - src[0];
			// Check single space move and diagonal attack
			if (rowDiff == 1)
			{
				// Open space, non-diagonal
				if (src[1] == dest[1] && board[dest[0]][dest[1]] == null)
				{
					return true;
				}
				// Diagonal attack valid
				else if (Math.abs(src[1] - dest[1]) == 1 && Math.abs(src[0] - dest[0]) == 1 && board[dest[0]][dest[1]] != null && board[dest[0]][dest[1]].isWhite)
				{
					return true;
				}
			}
			// Check double space move
			else if (rowDiff == 2)
			{
				if (src[1] == dest[1] && src[0] == 1 && board[src[0]+1][src[1]] == null && board[dest[0]][dest[1]] == null)
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
		return false;
	}
}
