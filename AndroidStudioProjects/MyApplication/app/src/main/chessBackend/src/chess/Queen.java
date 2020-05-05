package Backend.src.chess;
/**
 * Abstract subclass, inherits piece type,location, and color.
 * Have an individual logic of available moves for Queen.
 * @author Matthew Yu, James Checca
 */
public class Queen extends ChessPiece
{
	public Queen(String type, String loc, Boolean isWhite)
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
	public boolean rookLogic(String dst, ChessPiece[][] board, Chess game)
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
			return true;
		}
		else if ((board[dest[0]][dest[1]].isWhite && !this.isWhite) || (!board[dest[0]][dest[1]].isWhite && this.isWhite))
		{
			return true;
		}
		
		return false;
		
	}
	
	public boolean bishopLogic(String dst, ChessPiece[][] board, Chess game)
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
		
		if (rowDiff != colDiff) return false;
		
		int rowIncr = (src[0] < dest[0]) ? 1 : -1;
		int colIncr = (src[1] < dest[1]) ? 1 : -1;
		
		int i = src[0] + rowIncr;
		int j = src[1] + colIncr;
		
		while (i != dest[0] && j != dest[1])
		{
			if (board[i][j] != null)
			{
				return false;
			}
			i += rowIncr;
			j += colIncr;
		}
		
		if (board[dest[0]][dest[1]] == null)
		{
			return true;
		}
		else if ((board[dest[0]][dest[1]].isWhite && !this.isWhite) || (!board[dest[0]][dest[1]].isWhite && this.isWhite))
		{
			return true;
		}
		
		return false;
	}
	public boolean canMove(String dst, ChessPiece[][] board, Chess game)
	{
		if (rookLogic(dst, board, game) || bishopLogic(dst, board, game))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
