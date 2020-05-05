package Backend.src.chess;
import  java.util.Scanner;
import  java.util.ArrayList;


/**
 * Chess class that initialized the board. Taking IO argument, and evaluating the move.
 * @author Matthew Yu, James Checca
 */
public class Chess
{

	/**
	 * Original Board
	 */
	public  ChessPiece[][] board = new ChessPiece[8][8];

	/**
	 * Temp board that move piece in advance. It is used to see if ones king will be checked after the move.
	 * It doesn't change the original board. It updates with the original board.
	 */
	public ChessPiece[][] temp_board = new ChessPiece[8][8];

	/**
	 * Initiate board and temp board.
	 */
	public  void populateBoard()
	{
		board[0][0] = new Rook("bR", "a8", false);
		board[0][1] = new Knight("bN", "b8", false);
		board[0][2] = new Bishop("bB", "c8", false);
		board[0][3] = new Queen("bQ", "d8", false);
		board[0][4] = new King("bK", "e8", false);
		board[0][5] = new Bishop("bB", "f8", false);
		board[0][6] = new Knight("bN", "g8", false);
		board[0][7] = new Rook("bR", "h8", false);
		temp_board[0][0] = new Rook("bR", "a8", false);
		temp_board[0][1] = new Knight("bN", "b8", false);
		temp_board[0][2] = new Bishop("bB", "c8", false);
		temp_board[0][3] = new Queen("bQ", "d8", false);
		temp_board[0][4] = new King("bK", "e8", false);
		temp_board[0][5] = new Bishop("bB", "f8", false);
		temp_board[0][6] = new Knight("bN", "g8", false);
		temp_board[0][7] = new Rook("bR", "h8", false);
		char file = 'a';
		String loc;
		for (int i = 0; i < 8; i++)
		{
			loc = file + "7";
			board[1][i] = new Pawn("bp", loc, false);
			temp_board[1][i] = new Pawn("bp", loc, false);
			file = (char)(file + 1);
		}

		file = 'a';
		for (int i = 0; i < 8; i++)
		{
			loc = file + "2";
			board[6][i] = new Pawn("wp", loc, true);
			temp_board[6][i] = new Pawn("wp", loc, true);

			file = (char)(file + 1);
		}
		board[7][0] = new Rook("wR", "a1", true);
		board[7][1] = new Knight("wN", "b1", true);
		board[7][2] = new Bishop("wB", "c1", true);
		board[7][3] = new Queen("wQ", "d1", true);
		board[7][4] = new King("wK", "e1", true);
		board[7][5] = new Bishop("wB", "f1", true);
		board[7][6] = new Knight("wN", "g1", true);
		board[7][7] = new Rook("wR", "h1", true);
		temp_board[7][0] = new Rook("wR", "a1", true);
		temp_board[7][1] = new Knight("wN", "b1", true);
		temp_board[7][2] = new Bishop("wB", "c1", true);
		temp_board[7][3] = new Queen("wQ", "d1", true);
		temp_board[7][4] = new King("wK", "e1", true);
		temp_board[7][5] = new Bishop("wB", "f1", true);
		temp_board[7][6] = new Knight("wN", "g1", true);
		temp_board[7][7] = new Rook("wR", "h1", true);
	}


	/**
	 * Print board
	 */
	public  void printBoard()
	{
		int i, j;
		for (i = 0; i < 8; i++)
		{
			for (j = 0; j < 8; j++)
			{
				if (board[i][j] != null)
				{
					System.out.print(board[i][j].type + " ");
				}
				else
				{
					if ((i%2 == 0 && j%2 == 0) || (i%2 == 1 && j%2 == 1))
					{
						System.out.print("   ");
					}
					else
					{
						System.out.print("## ");
					}
				}
			}
			System.out.println(8 - i);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
	}

	/**
	 * Print temp board for debugging usage.
	 */
	public  void printtempBoard()
	{
		int i, j;
		for (i = 0; i < 8; i++)
		{
			for (j = 0; j < 8; j++)
			{
				if (temp_board[i][j] != null)
				{
					System.out.print(temp_board[i][j].type + " ");
				}
				else
				{
					if ((i%2 == 0 && j%2 == 0) || (i%2 == 1 && j%2 == 1))
					{
						System.out.print("   ");
					}
					else
					{
						System.out.print("## ");
					}
				}
			}
			System.out.println(8 - i);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
	}


	/**
	 *  Converts filerank entries to tuples representing index in array
	 * @param fileRank index in string such as "a1"
	 * @return tuples or fileRank or [-1, -1] if filerank is invalid
	 */
	public  int[] fileRankToIndex(String fileRank)
	{
		int[] index = {-1, -1};
		char file = fileRank.charAt(0);
		char rank = fileRank.charAt(1);
		if (fileRank.length() != 2 || file < 'a' || file > 'h' || rank < '1' || rank > '8')
		{
			return index;
		}

		index[0] = 8 - Integer.parseInt(Character.toString(rank));
		index[1] = file - 97;

		return index;
	}

	/**
	 * Move board piece. At this stage, the move should all be valid.
	 * @param src source index in string such as "a1"
	 * @param dest destination index in string such as "a1"
	 */
	public void movePiece(String src, String dest){
		int [] srcIndex = fileRankToIndex(src);
		int [] destIndex = fileRankToIndex(dest);
		String type = board[srcIndex[0]][srcIndex[1]].type;
		board[destIndex[0]][destIndex[1]] = null;
		switch (type){
			case "bR": board[destIndex[0]][destIndex[1]] = new Rook("bR", dest, false);
				board[destIndex[0]][destIndex[1]].moved = true;
				break;
			case "wR": board[destIndex[0]][destIndex[1]] = new Rook("wR", dest, true);
				board[destIndex[0]][destIndex[1]].moved = true;
				break;
			case "bN": board[destIndex[0]][destIndex[1]] = new Knight("bN", dest, false);
				break;
			case "wN": board[destIndex[0]][destIndex[1]] = new Knight("wN", dest, true);
				break;
			case "bB": board[destIndex[0]][destIndex[1]] = new Bishop("bB", dest, false);
				break;
			case "wB": board[destIndex[0]][destIndex[1]] = new Bishop("wB", dest, true);
				break;
			case "bQ": board[destIndex[0]][destIndex[1]] = new Queen("bQ", dest, false);
				break;
			case "wQ": board[destIndex[0]][destIndex[1]] = new Queen("wQ", dest, true);
				break;
			case "bK": board[destIndex[0]][destIndex[1]] = new King("bK", dest, false);
				board[destIndex[0]][destIndex[1]].moved = true;
				break;
			case "wK": board[destIndex[0]][destIndex[1]] = new King("wK", dest, true);
				board[destIndex[0]][destIndex[1]].moved = true;
				break;
			case "bp": board[destIndex[0]][destIndex[1]] = new Pawn("bp", dest, false);
				break;
			case "wp":
				board[destIndex[0]][destIndex[1]] = new Pawn("wp", dest, true);
				break;
		}

		board[srcIndex[0]][srcIndex[1]] = null;
	}


	/**
	 * Revert the move of temp board by using original board.
	 * @param dest The place that want to be reverted.
	 */
	public void revertTempPiece(String dest) {

		int[] destIndex = fileRankToIndex(dest);
		String type = board[destIndex[0]][destIndex[1]].type;

		switch (type) {
			case "bR":
				temp_board[destIndex[0]][destIndex[1]] = new Rook("bR", dest, false);
				temp_board[destIndex[0]][destIndex[1]].moved = board[destIndex[0]][destIndex[1]].moved;
				break;
			case "wR":
				temp_board[destIndex[0]][destIndex[1]] = new Rook("wR", dest, true);
				temp_board[destIndex[0]][destIndex[1]].moved = board[destIndex[0]][destIndex[1]].moved;
				break;
			case "bN":
				temp_board[destIndex[0]][destIndex[1]] = new Knight("bN", dest, false);
				break;
			case "wN":
				temp_board[destIndex[0]][destIndex[1]] = new Knight("wN", dest, true);
				break;
			case "bB":
				temp_board[destIndex[0]][destIndex[1]] = new Bishop("bB", dest, false);
				break;
			case "wB":
				temp_board[destIndex[0]][destIndex[1]] = new Bishop("wB", dest, true);
				break;
			case "bQ":
				temp_board[destIndex[0]][destIndex[1]] = new Queen("bQ", dest, false);
				break;
			case "wQ":
				temp_board[destIndex[0]][destIndex[1]] = new Queen("wQ", dest, true);
				break;
			case "bK":
				temp_board[destIndex[0]][destIndex[1]] = new King("bK", dest, false);
				temp_board[destIndex[0]][destIndex[1]].moved = board[destIndex[0]][destIndex[1]].moved;
				break;
			case "wK":
				temp_board[destIndex[0]][destIndex[1]] = new King("wK", dest, true);
				temp_board[destIndex[0]][destIndex[1]].moved = board[destIndex[0]][destIndex[1]].moved;
				break;
			case "bp":
				temp_board[destIndex[0]][destIndex[1]] = new Pawn("bp", dest, false);
				break;
			case "wp":
				temp_board[destIndex[0]][destIndex[1]] = new Pawn("wp", dest, true);
				break;
		}
	}

	/**
	 * move temperate board from source to destination
	 * @param src source index as string such as "a1"
	 * @param dest destination index as string such as "a1"
	 */

	public void moveTempPiece(String src, String dest){
		int [] srcIndex = fileRankToIndex(src);
		int [] destIndex = fileRankToIndex(dest);
		String type = temp_board[srcIndex[0]][srcIndex[1]].type;
		temp_board[destIndex[0]][destIndex[1]] = null;
		switch (type){
			case "bR": temp_board[destIndex[0]][destIndex[1]] = new Rook("bR", dest, false);
				temp_board[destIndex[0]][destIndex[1]].moved = true;
				break;
			case "wR": temp_board[destIndex[0]][destIndex[1]] = new Rook("wR", dest, true);
				temp_board[destIndex[0]][destIndex[1]].moved = true;
				break;
			case "bN": temp_board[destIndex[0]][destIndex[1]] = new Knight("bN", dest, false);
				break;
			case "wN": temp_board[destIndex[0]][destIndex[1]] = new Knight("wN", dest, true);
				break;
			case "bB": temp_board[destIndex[0]][destIndex[1]] = new Bishop("bB", dest, false);
				break;
			case "wB": temp_board[destIndex[0]][destIndex[1]] = new Bishop("wB", dest, true);
				break;
			case "bQ": temp_board[destIndex[0]][destIndex[1]] = new Queen("bQ", dest, false);
				break;
			case "wQ": temp_board[destIndex[0]][destIndex[1]] = new Queen("wQ", dest, true);
				break;
			case "bK": temp_board[destIndex[0]][destIndex[1]] = new King("bK", dest, false);
				temp_board[destIndex[0]][destIndex[1]].moved = true;
				break;
			case "wK": temp_board[destIndex[0]][destIndex[1]] = new King("wK", dest, true);
				temp_board[destIndex[0]][destIndex[1]].moved = true;
				break;
			case "bp": temp_board[destIndex[0]][destIndex[1]] = new Pawn("bp", dest, false);
				break;
			case "wp":
				temp_board[destIndex[0]][destIndex[1]] = new Pawn("wp", dest, true);
				break;
		}

		temp_board[srcIndex[0]][srcIndex[1]] = null;
	}

	/**
	 * Inverse the color or input
	 * @param color The color that want to be inversed
	 * @return the inverse of the parameter.
	 */
	public static String inverse_the_color(String color){
		if (color.equals("White")){
			return "Black";
		}else{
			return "White";
		}
	}

	/**
	 * Going to evaluation phrase. Call on check_if_move_valid.
	 * @param source Source in string form such as "a1".
	 * @param destination Destination in string form such as "a1"
	 * @param whosTurn The color it is curretnly moving
	 * @param option_argument optional argument for pawn promotion
	 * @return true if nothing is wrong, false otherwise.
	 */
	public boolean evaluation (String source, String destination, String whosTurn, String option_argument){

		String movingColor = whosTurn;

		// Check if move are valid (check if source exist and is the color that is intended to move, check if destination is valid, if king is checked after this move)
		boolean ifValidMove = this.check_if_move_valid(source,destination,movingColor,option_argument);
		if(!ifValidMove){
			return false;
		}


		return true;
	}

	/**
	 * Check if this is a valid move. Promo pawn in this stage if necessarily.
	 * @param source Source in string form such as "a1".
	 * @param destination Destination in string form such as "a1"
	 * @param MovingColor The color it is curretnly moving
	 * @param option_argument optional argument for pawn promotion
	 * @return return true if the move is valid, false otherwsie
	 */
	public boolean check_if_move_valid(String source, String destination, String MovingColor, String option_argument){

		int [] srcIndex = fileRankToIndex(source);
		int [] destIndex = fileRankToIndex(destination);

		if (board[srcIndex[0]][srcIndex[1]] == null){
			return false;
		}


		String sourceColor;

		if (board[srcIndex[0]][srcIndex[1]].isWhite){
			sourceColor = "White";
		}else{
			sourceColor = "Black";
		}

		// if color doesn't match
		if(!sourceColor.equals(MovingColor)){
			//System.out.println("Source color not equal");
			return false;
		}



		// If source can't move to the destination by the movable set.
		boolean moveable = if_Moveable(source, destination);
		if(!moveable){
			return false;
		}


		moveTempPiece(source,destination);
		boolean checkStatus = if_this_color_in_temp_is_checked(MovingColor);

		if(checkStatus){
			//System.out.println("Put yourself in check.");
			temp_board[destIndex[0]][destIndex[1]] = null;
			revertTempPiece(source);
			if(board[destIndex[0]][destIndex[1]] != null){
				revertTempPiece(destination);
			}
			//printtempBoard();
			return false;
		}

		if (board[srcIndex[0]][srcIndex[1]].type == "wp") {
			if (destIndex[1] == 7) {
				board[srcIndex[0]][srcIndex[1]] = null;
				board[srcIndex[0]][srcIndex[1]] = null;
				switch (option_argument) {
					case "R":
						temp_board[srcIndex[0]][srcIndex[1]] = new Rook("wR", source, true);
						board[srcIndex[0]][srcIndex[1]] = new Rook("wR", source, true);
						break;

					case "N":
						temp_board[srcIndex[0]][srcIndex[1]] = new Knight("wN", source, true);
						board[srcIndex[0]][srcIndex[1]] = new Knight("wN", source, true);
						break;

					case "B":
						temp_board[srcIndex[0]][srcIndex[1]] = new Bishop("wB", source, true);
						board[srcIndex[0]][srcIndex[1]] = new Bishop("wB", source, true);
						break;

					case "Q":
						temp_board[srcIndex[0]][srcIndex[1]] = new Queen("wQ", source, true);
						board[srcIndex[0]][srcIndex[1]] = new Queen("wQ", source, true);
						break;

					case "K":
						temp_board[srcIndex[0]][srcIndex[1]] = new King("wK", source, true);
						board[srcIndex[0]][srcIndex[1]] = new King("wK", source, true);
						break;

					default:
						temp_board[srcIndex[0]][srcIndex[1]] = new Queen("wQ", source, true);
						board[srcIndex[0]][srcIndex[1]] = new Queen("wQ", source, true);
						break;
				}
			} else {
				if (board[srcIndex[0]][srcIndex[1]].type == "bp") {
					if (destIndex[1] == 0) {
						board[srcIndex[0]][srcIndex[1]] = null;
						board[srcIndex[0]][srcIndex[1]] = null;

						switch (option_argument) {
							case "R":
								temp_board[srcIndex[0]][srcIndex[1]] = new Rook("bR", source, true);
								board[srcIndex[0]][srcIndex[1]] = new Rook("bR", source, true);
								break;

							case "N":
								temp_board[srcIndex[0]][srcIndex[1]] = new Knight("bN", source, true);
								board[srcIndex[0]][srcIndex[1]] = new Knight("bN", source, true);
								break;

							case "B":
								temp_board[srcIndex[0]][srcIndex[1]] = new Bishop("bB", source, true);
								board[srcIndex[0]][srcIndex[1]] = new Bishop("bB", source, true);
								break;

							case "Q":
								temp_board[srcIndex[0]][srcIndex[1]] = new Queen("bQ", source, true);
								board[srcIndex[0]][srcIndex[1]] = new Queen("bQ", source, true);
								break;

							case "K":
								temp_board[srcIndex[0]][srcIndex[1]] = new King("bK", source, true);
								board[srcIndex[0]][srcIndex[1]] = new King("bK", source, true);
								break;

							default:
								temp_board[srcIndex[0]][srcIndex[1]] = new Queen("bQ", source, true);
								board[srcIndex[0]][srcIndex[1]] = new Queen("bQ", source, true);
								break;
						}
					}
				}
			}

		}

		return true;
	}


	/**
	 * Check if the piece in source location can move to destination location
	 * @param source Source in string form such as "a1"
	 * @param destination destination in string form such as "a1"
	 * @return true if it can be moved, false otherwise
	 */

	public  boolean if_Moveable(String source,String destination){

		int [] srcIndex = fileRankToIndex(source);

		if (board[srcIndex[0]][srcIndex[1]].canMove(destination,board,this)){
			return true;
		}else {
			return false;
		}
	}


	/**
	 * Check if the parameter color is being checked
	 * @param checkThisColor Color that want to be checked.
	 * @return true if parameter is checked,  false otherwise
	 */

	public boolean if_this_color_is_checked(String checkThisColor){

		String attackerColor = inverse_the_color(checkThisColor);

		String defenderKingPosition = "";
		String king;
		if( attackerColor.equals("White")){
			king = "bK";
		}else{
			king = "wK";
		}

		for(int i = 0; i < 8;i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (board[i][j].type.equals(king)) {
						defenderKingPosition = board[i][j].loc;
					}
				}
			}
		}

		if(attackerColor.equals("White")){
			for(int q = 0; q < 8;q++) {
				for (int p = 0; p < 8; p++) {
					if (board[q][p] != null) {
						if (board[q][p].isWhite) {
							if(board[q][p].canMove(defenderKingPosition,board,this)){
								return true;
							}
						}
					}
				}
			}
		}else{
			for(int q = 0; q < 8;q++) {
				for (int p = 0; p < 8; p++) {
					if (board[q][p] != null) {
						if (!board[q][p].isWhite) {
							if(board[q][p].canMove(defenderKingPosition,board,this)){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check the temp board is being checked
	 * @param checkThisColor Color that want to be checked.
	 * @return true if yes, false otherwise.
	 */

	public boolean if_this_color_in_temp_is_checked(String checkThisColor){
		String attackerColor = inverse_the_color(checkThisColor);
		String defenderKingPosition = "";
		String king;
		if( attackerColor.equals("White")){
			king = "bK";
		}else{
			king = "wK";
		}

		for(int i = 0; i < 8;i++) {
			for (int j = 0; j < 8; j++) {
				if (temp_board[i][j] != null) {
					if (temp_board[i][j].type.equals(king)) {
						defenderKingPosition = temp_board[i][j].loc;
					}
				}
			}
		}

		if(attackerColor.equals("White")){
			for(int q = 0; q < 8;q++) {
				for (int p = 0; p < 8; p++) {
					if (temp_board[q][p] != null) {
						if (temp_board[q][p].isWhite) {
							if(temp_board[q][p].canMove(defenderKingPosition,temp_board,this)){
								return true;
							}
						}
					}
				}
			}
		}else{
			for(int q = 0; q < 8;q++) {
				for (int p = 0; p < 8; p++) {
					if (temp_board[q][p] != null) {
						if (!temp_board[q][p].isWhite) {
							if(temp_board[q][p].canMove(defenderKingPosition,temp_board,this)){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Check if the paramter color is checkmated.
	 * It computes all one-step possible move to prevent it, or king can be esapced from it.
	 * @param checkThisColor Color that want to be checked.
	 * @return true if the parameter is checkmated, false otherwise
	 */
	public  boolean  if_this_color_is_checkmated( String checkThisColor){
		String attackerColor = inverse_the_color(checkThisColor);

		String king;
		if( attackerColor.equals("White")){
			king = "bK";
		}else{
			king = "wK";
		}

		// Check if the attacked can be block or taken .
		for(int k = 0; k < 8;k++){
			for(int l = 0; l < 8;l++){
				if (board[k][l] != null) {
					if (checkThisColor.equals("White")){
						if (temp_board[k][l].isWhite) {
							ArrayList<String> movableSet = new ArrayList<String>();
							String pieceLocation = temp_board[k][l].loc;
							for(int i = 0; i < 8;i++) {
								for (int j = 0; j < 8; j++) {
									if  (board[k][l] == null) {
										//System.out.println("Wut");
									}else{
										String index ="";
										switch(i){
											case 0: index = "a"; break;
											case 1: index = "b"; break;
											case 2: index = "c"; break;
											case 3: index = "d"; break;
											case 4: index = "e"; break;
											case 5: index = "f"; break;
											case 6: index = "g"; break;
											case 7: index = "h"; break;
										}
										String rank = Integer.toString(j);
										index = index + rank;
										//System.out.println(index);
										if ( board[k][l].canMove(index,board,this) ) {
											movableSet.add(index);
										}
									}

								}
							}


							for(int o = 0; o < movableSet.size();o++){
								String getDestination = movableSet.get(o);
								int [] destIndex = fileRankToIndex(getDestination);
								moveTempPiece(pieceLocation,getDestination);
								if (!if_this_color_in_temp_is_checked(checkThisColor)){
									temp_board[destIndex[0]][destIndex[1]] = null;
									revertTempPiece(pieceLocation);
									if(board[destIndex[0]][destIndex[1]] != null){
										revertTempPiece(getDestination);
									}
									return false;
								}else{
									temp_board[destIndex[0]][destIndex[1]] = null;
									revertTempPiece(pieceLocation);
									if(board[destIndex[0]][destIndex[1]] != null){
										revertTempPiece(getDestination);
									}
								}
							}
						}
					}else{
						if(!temp_board[k][l].isWhite) {
							ArrayList<String> movableSet = new ArrayList<String>();
							String pieceLocation = temp_board[k][l].loc;
							for(int i = 0; i < 8;i++) {
								for (int j = 0; j < 8; j++) {
									if  (board[k][l] == null) {
										//System.out.println("Wut");
									}else{
										String index ="";
										switch(i){
											case 0: index = "a"; break;
											case 1: index = "b"; break;
											case 2: index = "c"; break;
											case 3: index = "d"; break;
											case 4: index = "e"; break;
											case 5: index = "f"; break;
											case 6: index = "g"; break;
											case 7: index = "h"; break;
										}
										String rank = Integer.toString(j);
										index = index + rank;
										//System.out.println(index);
										if ( board[k][l].canMove(index,board,this) ) {
											movableSet.add(index);
										}
									}

								}
							}


							for(int o = 0; o < movableSet.size();o++){
								String getDestination = movableSet.get(o);
								int [] destIndex = fileRankToIndex(getDestination);
								moveTempPiece(pieceLocation,getDestination);
								if (!if_this_color_in_temp_is_checked(checkThisColor)){
									temp_board[destIndex[0]][destIndex[1]] = null;
									revertTempPiece(pieceLocation);
									if(board[destIndex[0]][destIndex[1]] != null){
										revertTempPiece(getDestination);
									}
									return false;
								}else{
									temp_board[destIndex[0]][destIndex[1]] = null;
									revertTempPiece(pieceLocation);
									if(board[destIndex[0]][destIndex[1]] != null){
										revertTempPiece(getDestination);
									}
								}
							}
						}
					}

				}
			}
		}




		ArrayList<String> kingMoveAbleSet = new ArrayList<String>();
		String kingsPostion = "";
		int KingPostion_i = 0;
		int KingPostion_j = 0;
		for(int i = 0; i < 8;i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (temp_board[i][j].type.equals(king)) {
						KingPostion_i = i;
						KingPostion_j = j;
						kingsPostion = board[i][j].loc;
					}
				}
			}
		}

		for(int i = 0; i < 8;i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if ( board[KingPostion_i][KingPostion_j].canMove(board[i][j].loc,board,this) ) {
						kingMoveAbleSet.add(board[i][j].loc);
					}
				}
			}
		}



		boolean checkStatus;
		int checkCount = 0;
		for(int count =0; count < kingMoveAbleSet.size();count++){

			String getDestination = kingMoveAbleSet.get(count);
			int [] destIndex = fileRankToIndex(getDestination);
			moveTempPiece(kingsPostion,getDestination);
			checkStatus = if_this_color_in_temp_is_checked(checkThisColor);
			if(checkStatus){
				checkCount++;
			}

			temp_board[destIndex[0]][destIndex[1]] = null;
			revertTempPiece(kingsPostion);
			if(board[destIndex[0]][destIndex[1]] != null){
				revertTempPiece(getDestination);
			}
		}



		if(checkCount == kingMoveAbleSet.size()){
			return true;
		}else {
			return false;
		}


	}

	/**
	 * If castle can be done with the paramter.
	 * @param side The color that want to be castle.
	 * @return true after doing castle, false otherwise.
	 */
	public boolean castle(char side)
	{
		if (side == 'w')
		{
			if (board[7][5] == null && board[7][6] == null && board[7][4].type.equals("wK") && board[7][7].type.equals("wR"))
			{


				temp_board[7][4] = null;
				temp_board[7][5] = new Rook("wR", "f8", true);
				temp_board[7][5].moved = true;
				temp_board[7][6] = new King("wK", "g8", true);
				temp_board[7][6].moved = true;
				temp_board[7][7] = null;

				if(if_this_color_in_temp_is_checked("Black")){
					temp_board[7][5] = null;
					temp_board[7][6] = null;
					temp_board[7][7] =null;
					return  false;
				}else{
					board[7][4] = null;
					board[7][5] = new Rook("wR", "f8", true);
					board[7][5].moved = true;
					board[7][6] = new King("wK", "g8", true);
					board[7][6].moved = true;
					board[7][7] = null;
					return true;
				}

			}
			else
			{
				return false;
			}
		}
		else if (side == 'b')
		{
			if (board[0][5] == null && board[0][6] == null && board[0][4].type.equals("wK") && board[0][7].type.equals("wR"))
			{


				temp_board[0][4] = null;
				temp_board[0][5] = new Rook("wR", "f1", true);
				temp_board[0][5].moved = true;
				temp_board[0][6] = new King("wK", "g1", true);
				temp_board[0][6].moved = true;
				temp_board[0][7] = null;


				if(if_this_color_in_temp_is_checked("White")){

					return  false;
				}else{
					board[0][4] = null;
					board[0][5] = new Rook("wR", "f1", true);
					board[0][5].moved = true;
					board[0][6] = new King("wK", "g1", true);
					board[0][6].moved = true;
					board[0][7] = null;
				}



				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}

	/**
	 * Create the game board.Taking IO arguments. Evaluate the moves.
	 * @param args
	 */
	public static void main(String[] args)
	{
		Chess game = new Chess();
		game.populateBoard();
		game.printBoard();
		System.out.print("White's move: ");

		String movingColor = "White";

		// Game Start
		Scanner scanner = new Scanner(System. in);
		String input_src = "";
		String input_dest = "";
		String third_arg = "";
		boolean drawRequest = false;
		boolean draw_wait_for_response = false;



		while(true){
			String inputString = scanner.nextLine();
			boolean validInput = true;
			String[] argList = inputString.split(" ");

			if(drawRequest == true){
				draw_wait_for_response = false;
			}

			switch (argList.length){
				case 0: validInput = false;
					break;
				case 1: if (argList[0].equals("resign")){
					System.out.println(inverse_the_color(movingColor) + " wins");
					System.exit(0);
				}else{
					if (argList[0].equals("draw")) {
						if (drawRequest){
							System.out.println("draw");
							System.exit(0);
						}else{
							validInput = false;
						}
					}else{
						validInput = false;
					}
				}
					break;
				case 2:
					int [] srcIndex = game.fileRankToIndex(argList[0]);
					int [] destIndex = game.fileRankToIndex(argList[1]);
					if ( (srcIndex[0] == -1) || (destIndex[0] == -1)){
						validInput = false;
					}else{
						input_src = argList[0];
						input_dest = argList[1];
						third_arg = "";
					}
					break;
				case 3:
					input_src = argList[0];
					input_dest = argList[1];
					srcIndex = game.fileRankToIndex(argList[0]);
					destIndex = game.fileRankToIndex(argList[1]);
					if ( (srcIndex[0] == -1) || (destIndex[0] == -1)){
						validInput = false;
					}else{
						input_src = argList[0];
						input_dest = argList[1];
					}

					if(argList[2].equals("draw?")){
						third_arg = "";
						drawRequest = true;
						draw_wait_for_response = true;
					}else{
						third_arg = argList[3];
					}
					break;
				default: validInput = false;
			}


			if (drawRequest == true && draw_wait_for_response == false){
				drawRequest = false;
			}


			if(validInput){
				//System.out.println(input_src + " " + input_dest);
				String opponent = inverse_the_color(movingColor);
				boolean evaluationResult ;
				boolean castled = false;
				evaluationResult = game.evaluation(input_src,input_dest,movingColor, third_arg);
				/*
				if (input_src.equals("e1"))
				{
					if (input_dest.equals("g1"))
					{
						castled = game.castle('w');
					}
				}
				else if (input_src.equals("e8"))
				{
					if (input_dest.equals("g8"))
					{
						castled = game.castle('b');
					}
				}

				if (castled){
					game.printBoard();
					System.out.print(movingColor + "'s move: ");
				}*/

				if (evaluationResult == true){
					game.movePiece(input_src,input_dest);
					//System.out.println("realcastle");
					game.printBoard();
					boolean checkFlag = false;
					if( game.if_this_color_is_checked(opponent)){
						if( game.if_this_color_is_checkmated(opponent)){
							System.out.println("Checkmate");
							System.out.println(movingColor + " wins");
							System.exit(0);
						}else{
							checkFlag = true;
						}
					}

					if(checkFlag){
						System.out.println("Check");
					}
					movingColor = inverse_the_color(movingColor);
					System.out.print(movingColor + "'s move: ");
				}else{
					//System.out.println("Evaluation is false, try again");
					System.out.println("Illegal move, try again");
					System.out.print(movingColor + "'s move: ");
				}
			}else{
				System.out.println("Illegal move, try again");
				System.out.print(movingColor + "'s move: ");
			}
		}

	}
}
