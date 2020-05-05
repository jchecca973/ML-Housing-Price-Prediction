package Backend.src.chess;
/**
 * Abstract subclass, inherits piece type,location, and color.
 * Have an individual logic of available moves for Rock.
 * @author Matthew Yu, James Checca
 */
public class Rook extends ChessPiece
{
	/**
	 * Rock can't castle after moved.
	 */
	public boolean moved = false;

	public Rook(String type, String loc, Boolean isWhite)
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

		if (rowDiff != 0 && colDiff != 0)
		{
			return false;
		}
		int i;
		if (colDiff == 0)
		{
			int col = src[1];
			i = src[0];
			while (i != dest[0])
			{
				if (board[i][col] != null && i != src[0])
				{
					return false;
				}
				if (src[0] < dest[0]) i++;
				else i--;
			}
		}
		else if (rowDiff == 0)
		{
			int row = src[0];
			i = src[1];
			while (i != dest[1])
			{
				if (board[row][i] != null && i != src[1])
				{
					return false;
				}
				if (src[1] < dest[1]) i++;
				else i--;
			}
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
