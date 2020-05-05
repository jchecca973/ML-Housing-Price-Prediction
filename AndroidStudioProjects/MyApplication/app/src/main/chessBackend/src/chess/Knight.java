package Backend.src.chess;
/**
 * Abstract subclass, inherits piece type,location, and color.
 * Have an individual logic of available moves for Knight.
 * @author Matthew Yu, James Checca
 */
public class Knight extends ChessPiece
{

	public Knight(String type, String loc, Boolean isWhite)
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
		int rowDiff = Math.abs(src[0] - dest[0]);
		int colDiff = Math.abs(src[1] - dest[1]);
		if (this.isWhite)
		{
			// Checks if valid knight move
			if ((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1))
			{
				if (board[dest[0]][dest[1]] == null)
				{
					return true;
				}
				else if (!board[dest[0]][dest[1]].isWhite)
				{
					return true;
				}
			}
		}
		else
		{
			// Checks if valid knight move
			if ((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1))
			{
				if (board[dest[0]][dest[1]] == null)
				{
					return true;
				}
				else if (board[dest[0]][dest[1]].isWhite)
				{
					return true;
				}
			}
		}
		return false;
	}
}
