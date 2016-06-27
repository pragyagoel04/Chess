//backGround details

//import javax.swing.*;
/**	{
 * small= black
 * capital= white
 * 
 * p=pawn
 * r=rook
 * k=knight
 * b=bishop
 * q=queen
 * a=king
 * 
 */


public class AlphaBetaChess {
	static String chessBoard[][]={
			{"r","k","b","q","a","b","k","r"},
			{"p","p","p","p","p","p","p","p"},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{"P","P","P","P","P","P","P","P"},
			{"R","K","B","Q","A","B","K","R"}

	};

	static int kingPositionC = 60, kingPositionL = 4;

	public static void main(String[] args) {
		/*
		 * JFrame f=new JFrame("CHESS..!"); UserInterface ui=new
		 * UserInterface(); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * f.add(ui); f.setSize(500,500); f.setVisible(true);
		 */
		String moves = possibleMoves();
		System.out.println("Total : " + moves.length()/5 + " : " + moves);
	}

	public static String possibleMoves() {

		StringBuilder list = new StringBuilder();
		String temp="";

		for (int i = 0; i < 64; i++) {
			switch (chessBoard[i / 8][i % 8]) {
			case "P":
				list.append(possibleP(i));
				temp=(String)possibleP(i);
				if(temp.length()>0)
					System.out.println("P : " + temp.length()/5 + " : " + temp);
				break;
			case "R":
				list.append(possibleR(i));
				temp=(String)possibleR(i);
				if(temp.length()>0)
					System.out.println("R : " + temp.length()/5 + " : " + temp);
				break;
			case "K":
				list.append(possibleK(i));
				temp=(String)possibleK(i);
				if(temp.length()>0)
					System.out.println("K : " + temp.length()/5 + " : " + temp);
				break;
			case "B":
				list.append(possibleB(i));
				temp=(String)possibleB(i);
				if(temp.length()>0)
					System.out.println("B : " + temp.length()/5 + " : " + temp);
				break;
			case "Q":
				list.append(possibleQ(i));
				temp=(String)possibleQ(i);
				if(temp.length()>0)
					System.out.println("Q : " + temp.length()/5 + " : " + temp);
				break;
			case "A":
				list.append(possibleA(i));
				temp=(String)possibleA(i);
				if(temp.length()>0)
					System.out.println("A : " + temp.length()/5 + " : " + temp);
				break;
			}
		}
		return list.toString(); // x1,y1,x2,y2,captured piece
	}

	private static Object possibleP(int i) {
		StringBuilder list = new StringBuilder();
		String oldPiece;
		int r = i / 8, c = i % 8;

		//for diagonal moves
		for(int j=-1; j<=1;j++){
			if(j==0)
				continue;

			//check if its end of the board
			try{
				if(Character.isLowerCase(chessBoard[r-1][c+j].charAt(0))){
					oldPiece=chessBoard[r-1][c+j];
					chessBoard[r-1][c+j]=chessBoard[r][c];
					chessBoard[r][c]=" ";

					//if its a valid move, move it
					if(kingSafe()){
						list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r-1)
						+ String.valueOf(c+j) + String.valueOf(oldPiece));
					}

					//move back to check more possibilities
					chessBoard[r][c] = chessBoard[r-1][c+j];
					chessBoard[r-1][c+j] = oldPiece;
				}
			}
			catch(Exception e){}

		}

		//check for forward move
		Boolean failure=false;
		for(int j=-1;j>=-2 && !failure; j--){
			try{
				if(chessBoard[r+j][c]==" "){
					oldPiece=chessBoard[r+j][c];
					chessBoard[r+j][c]=chessBoard[r][c];
					chessBoard[r][c]=" ";

					//if its a valid move, move it
					if(kingSafe()){
						list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+j)
						+ String.valueOf(c) + String.valueOf(oldPiece));
					}
					
					//move back to check more possibilities
					chessBoard[r][c] = chessBoard[r+j][c];
					chessBoard[r+j][c] = oldPiece;
				}	

				//check for 
				else failure=true;
			}
			catch(Exception e){}

		}
		return list.toString();
	}

	private static Object possibleK(int i) {
		StringBuilder list = new StringBuilder();
		String oldPiece;
		int r = i / 8, c = i % 8;
		for(int j=-2; j<=2;j++){
			for(int k=-2;k<=2;k++){

				if(!(j!=0 && k!=0 && j!=k && j!=(-1)*k))
					continue;

				//check if its end of the board
				try{	
					if(" ".equals(chessBoard[r+j][c+k]) || Character.isLowerCase(chessBoard[r+j][c+k].charAt(0))){
						oldPiece=chessBoard[r+j][c+k];
						chessBoard[r+j][c+k]=chessBoard[r][c];
						chessBoard[r][c] = " ";

						//if its a valid move, move it
						if(kingSafe()){
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+j)
							+ String.valueOf(c+k) + String.valueOf(oldPiece));
						}

						//move back to check more possibilities
						chessBoard[r][c] = chessBoard[r+j][c+k];
						chessBoard[r+j][c+k] = oldPiece;			

					}


				}
				catch(Exception e){

				}
			}
		}
		return list.toString();
	}

	private static Object possibleR(int i) {
		StringBuilder list = new StringBuilder();
		String oldPiece;
		int r = i / 8, c = i % 8;
		int temp=1;
		for(int j=-1; j<=1;j++){
			for(int k=-1;k<=1;k++){

				if(!((j!=0 && k==0) || (j==0 && k!=0)))
					continue;

				try{
					//check if its end of the board
					while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
						oldPiece=chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k]=chessBoard[r][c];
						chessBoard[r][c] = " ";

						//if its a valid move, move it
						if(kingSafe()){
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+temp*j)
							+ String.valueOf(c+temp*k) + String.valueOf(oldPiece));
						}

						//move back to check more possibilities
						chessBoard[r][c] = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k] = oldPiece;

						temp++;
					}

					//if there is a black player
					if (Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){
						oldPiece = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k] = chessBoard[r][c];
						chessBoard[r][c] = " ";

						//if its a valid move, move it
						if(kingSafe()){
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+temp*j)
							+ String.valueOf(c+temp*k) + String.valueOf(oldPiece));


							//move back to check more possibilities
							chessBoard[r][c] = chessBoard[r+temp*j][c+temp*k];
							chessBoard[r+temp*j][c+temp*k] = oldPiece;

						}
					}

				}
				catch(Exception e){

				}
				temp=1;
			}
		}
		return list.toString();
	}

	private static Object possibleB(int i) {
		StringBuilder list = new StringBuilder();
		String oldPiece;
		int r = i / 8, c = i % 8;
		int temp=1;
		for(int j=-1; j<=1;j++){
			for(int k=-1;k<=1;k++){
				//so it only moves diagonally
				if(j==0 || k==0)
					continue;

				try{
					//check if its end of the board
					while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
						oldPiece=chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k]=chessBoard[r][c];
						chessBoard[r][c] = " ";

						//if its a valid move, move it
						if(kingSafe()){
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+temp*j)
							+ String.valueOf(c+temp*k) + String.valueOf(oldPiece));
						}

						//move back to check more possibilities
						chessBoard[r][c] = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k] = oldPiece;

						temp++;
					}

					//if there is a black player
					if (Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){
						oldPiece = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k] = chessBoard[r][c];
						chessBoard[r][c] = " ";

						//if its a valid move, move it
						if(kingSafe()){
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+temp*j)
							+ String.valueOf(c+temp*k) + String.valueOf(oldPiece));


							//move back to check more possibilities
							chessBoard[r][c] = chessBoard[r+temp*j][c+temp*k];
							chessBoard[r+temp*j][c+temp*k] = oldPiece;

						}
					}

				}
				catch(Exception e){

				}
				temp=1;
			}
		}
		return list.toString();
	}

	private static Object possibleQ(int i) {
		StringBuilder list = new StringBuilder();
		String oldPiece;
		int r = i / 8, c = i % 8;
		int temp=1;
		for(int j=-1; j<=1;j++){
			for(int k=-1;k<=1;k++){

				if(j==0 && k==0)
					continue;

				try{
					//check if its end of the board
					while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
						oldPiece=chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k]=chessBoard[r][c];
						chessBoard[r][c] = " ";

						//if its a valid move, move it
						if(kingSafe()){
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+temp*j)
							+ String.valueOf(c+temp*k) + String.valueOf(oldPiece));
						}

						//move back to check more possibilities
						chessBoard[r][c] = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k] = oldPiece;

						temp++;
					}

					//if there is a black player
					if (Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))){
						oldPiece = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k] = chessBoard[r][c];
						chessBoard[r][c] = " ";

						//if its a valid move, move it
						if(kingSafe()){
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+temp*j)
							+ String.valueOf(c+temp*k) + String.valueOf(oldPiece));


							//move back to check more possibilities
							chessBoard[r][c] = chessBoard[r+temp*j][c+temp*k];
							chessBoard[r+temp*j][c+temp*k] = oldPiece;

						}
					}

				}
				catch(Exception e){

				}
				temp=1;
			}
		}
		return list.toString();
	}

	private static String possibleA(int i) {
		StringBuilder list = new StringBuilder();
		String oldPiece;
		int r = i / 8, c = i % 8;
		for (int j = 0; j < 9; j++) {
			if (j != 4) {
				try {
					//check if its end of the board
					if (Character.isLowerCase(chessBoard[r - 1 + j / 3][c - 1 + j % 3].charAt(0))
							|| " ".equals(chessBoard[r - 1 + j / 3][c - 1 + j % 3])) {
						oldPiece = chessBoard[r - 1 + j / 3][c - 1 + j % 3];
						chessBoard[r - 1 + j / 3][c - 1 + j % 3] = chessBoard[r][c];
						chessBoard[r][c] = " ";
						int kingTemp = kingPositionC;
						kingPositionC = (r - 1 + j / 3) * 8 + c - 1 + j % 3;

						//if its a valid move, move it
						if (kingSafe()) {
							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r - 1 + j / 3)
							+ String.valueOf(c - 1 + j % 3) + String.valueOf(oldPiece));
						}

						//move back to check more possibilities
						chessBoard[r][c] = chessBoard[r - 1 + j / 3][c - 1 + j % 3];
						chessBoard[r - 1 + j / 3][c - 1 + j % 3] = oldPiece;
						kingPositionC = kingTemp;
					}

				} catch (Exception e) {
				}
			}
		}
		// TODO Castling...! hate you..! unfair...!
		return list.toString();
	}

	private static boolean kingSafe() {
		// TODO Auto-generated method stub
		return true;
	}
}
