import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*;
//backGround details

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

	static String chessBoardCopy[][] = new String[8][8];

	private static void printChessBoard(){
		for(int i=0;i<8;i++){
			System.out.println(Arrays.toString(chessBoard[i]));
		}
	}

	private static boolean checkForChessBoardChange(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(!chessBoard[i][j].equals(chessBoardCopy[i][j])){
					return true;
				}
			}
		}
		return false;
	}

	static int kingPositionC = 0, kingPositionL = 0;
	static int globalDepth=4;

	public static void main(String[] args) throws Exception {

		//set king positions
		while(!chessBoard[kingPositionC/8][kingPositionC%8].equals("A"))
			kingPositionC++;
		while(!chessBoard[kingPositionL/8][kingPositionL%8].equals("a"))
			kingPositionL++;

		// initialize duplicate chessboard
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chessBoardCopy[i][j] = chessBoard[i][j];
			}
		}
		printChessBoard();
		//String moves = possibleMoves();
		System.out.println(possibleMoves());
		
		makeMove(alphaBeta(globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0));
		//makeMove(7655 );
		
		//System.out.println("Total : " + moves.length()/5 + " : " + moves);

		//		JFrame f=new JFrame("CHESS..!"); UserInterface ui=new UserInterface(); 
		//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		f.add(ui);
		//		f.setSize(500,500); 
		//		f.setVisible(true);



	}

	public static int rating(){
		//		System.out.print("Whats the score: ");
		//		Scanner sc=new Scanner(System.in);
		//		return sc.nextInt();

		return 0;
	}

	public static String alphaBeta(int depth, int beta, int alpha, String move, int player) throws Exception{
		// return move and score eg. 1234b#####
		String list=possibleMoves();
		//String list="1";
		if(depth==0 || list.length()==0){
			return move+(rating()*(player*2-1));
			//return move+rating();
		}
		//		list="";
		//		System.out.println("How many moves are there: ");
		//		Scanner sc=new Scanner(System.in);
		//		int temp=sc.nextInt();
		//		for(int i=0;i<temp;i++){
		//			list+="1111b";
		//		}

		// sort later
		player=1-player;
		for(int i=0;i<list.length();i+=5){
			makeMove(list.substring(i,i+5));
			flipBoard();
			String returnString=alphaBeta(depth-1, beta, alpha, list.substring(i,i+5), player);
			int value=Integer.valueOf(returnString.substring(5));
			flipBoard();
			undoMove(list.substring(i,i+5));
			if(player==0){
				if(value<=beta){
					beta=value;
					if(depth==globalDepth){
						move=returnString.substring(0, 5);
					}
				}	
			} else{
				if(value>alpha){
					alpha=value;
					if(depth==globalDepth){
						move=returnString.substring(0,5);
					}
				}
			}
			if(alpha>=beta){
				if(player == 0)
					return move+beta;
				else
					return move+alpha;
			}
		}

		if(player == 0)
			return move+beta;
		else
			return move+alpha;

	}

	public static void flipBoard(){
		// swaps capitols with small and vice versa
		String temp;
		for(int i=0;i<32;i++){
			int r=i/8, c=i%8;
			if(Character.isUpperCase(chessBoard[r][c].charAt(0))){
				temp=chessBoard[r][c].toLowerCase();
			} else{
				temp=chessBoard[r][c].toUpperCase();
			}
			if(Character.isUpperCase(chessBoard[7-r][7-c].charAt(0))){
				chessBoard[r][c]=chessBoard[7-r][7-c].toLowerCase();
			} else{
				chessBoard[r][c]=chessBoard[7-r][7-c].toUpperCase();
			}
			chessBoard[7-r][7-c]=temp;
		}
		int kingTemp=kingPositionC;
		kingPositionC=63-kingPositionL;
		kingPositionL=63-kingTemp;
	}

	public static void makeMove(String move){

		if(!(move.endsWith("P"))){
			// example move = "6040 "
			// non promotion move
			//r1,c1,r2,c2,oldPiece
			chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
			chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";

			if("A".equals(chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])){
				kingPositionC=8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
			}
		}

		else{
			// promotional move
			//c1,c2,oldpiece,newpiece,P

			chessBoard[1][Character.getNumericValue(move.charAt(0))]=" ";
			chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
		}
	}


	public static void undoMove(String move){
		if(!(move.endsWith("P"))){
			// example move = "6040 "
			// non promotion move
			//r1,c1,r2,c2,oldPiece

			chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
			chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = String.valueOf(move.charAt(4));

			if("A".equals(chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])){
				kingPositionC=8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
			}
		}
		else{
			// promotional move
			//c1,c2,oldpiece,newpiece,P
			chessBoard[1][Character.getNumericValue(move.charAt(0))]="P";
			chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));
		}

	}

	public static String possibleMoves() throws Exception {

		StringBuilder list = new StringBuilder();
		String temp="";

		for (int i = 0; i < 64; i++) {
			switch (chessBoard[i / 8][i % 8]) {
			case "P":
				list.append(possibleP(i));
				temp=(String)possibleP(i);
				break;
			case "R":
				list.append(possibleR(i));
				temp=(String)possibleR(i);
				break;
			case "K":
				list.append(possibleK(i));
				temp=(String)possibleK(i);
				break;
			case "B":
				list.append(possibleB(i));
				temp=(String)possibleB(i);
				break;
			case "A":
				list.append(possibleA(i));
				temp=(String)possibleA(i);				
				break;
			case "Q":
				list.append(possibleQ(i));
				temp=(String)possibleQ(i);
				break;
			default:
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
				if(r>1){
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
				} else {
					//check for promotions with capture
					if(Character.isLowerCase(chessBoard[r-1][c+j].charAt(0))){
						String[] temp={"Q","B","R","K"};
						for(int k=0;k<4;k++){
							oldPiece=chessBoard[r-1][c+j];
							chessBoard[r-1][c+j]=temp[k];
							chessBoard[r][c]=" ";
							//if its a valid move, move it
							if(kingSafe()){
								list.append(String.valueOf(c) + String.valueOf(c+j) 
								+ String.valueOf(oldPiece) + String.valueOf(temp[k]) + "P");
							}
							//move back to check more possibilities
							chessBoard[r][c] = "P";
							chessBoard[r-1][c+j] = oldPiece;
						}
					}
				}
			}
			catch(Exception e){}
		}

		//check for forward move
		Boolean failure=false;
		for(int j=-1;j>=-2 && !failure; j--){
			try{
				if(r>1){
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

						if(r!=6)
							failure=true;
					}	
					else 
						failure=true;
				}
				else {
					//check for promotion with forward move

					if(chessBoard[r+j][c]==" "){
						String[] temp={"Q","B","R","K"};
						for(int k=0;k<4;k++){
							oldPiece=chessBoard[r+j][c];
							chessBoard[r+j][c]=temp[k];
							chessBoard[r][c]=" ";
							//if its a valid move, move it
							if(kingSafe()){
								//c,c,oldpiece,newpiece,P
								list.append(String.valueOf(c) + String.valueOf(c) 
								+ String.valueOf(oldPiece) + String.valueOf(temp[k]) + "P");
							}

							//move back to check more possibilities
							chessBoard[r][c] = "P";
							chessBoard[r+j][c] = oldPiece;

							if(r!=6)
								failure=true;
						}
					}	
					else 
						failure=true;
				}
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

	private static Object possibleB(int i) throws Exception {
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
					temp=1;
					//check if its end of the board
					while(" ".equals(chessBoard[r+temp*j][c+temp*k])){
						oldPiece=chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k]=chessBoard[r][c];
						chessBoard[r][c] = " ";

						//if its a valid move, move it
						//						if(kingSafe()){
						//							list.append(String.valueOf(r) + String.valueOf(c) + String.valueOf(r+temp*j)
						//							+ String.valueOf(c+temp*k) + String.valueOf(oldPiece));
						//						}

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
						}


						//move back to check more possibilities
						chessBoard[r][c] = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k] = oldPiece;
					}

				}
				catch(ArrayIndexOutOfBoundsException e){ }
				//				catch(Exception e){
				//					e.printStackTrace();
				//				}
			}
		}
		return list.toString();
	}

	private static String possibleQ(int i) {
		StringBuilder list = new StringBuilder();
		String oldPiece;
		int r = i / 8, c = i % 8;
		int temp=1;

		for(int j=-1; j<=1;j++){
			for(int k=-1;k<=1;k++){

				if(j==0 && k==0)
					continue;
				//check if its end of the board
				try{
					temp=1;
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
						}


						//move back to check more possibilities
						chessBoard[r][c] = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r+temp*j][c+temp*k] = oldPiece;
					}

				}
				catch(Exception e){

				}
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
				//check if its end of the board
				try {
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

		// checking for bishop and queen
		int r = kingPositionC/8, c = kingPositionC%8;

		int temp = 1;
		// for bishop or rook or queen
		for (int j = -1; j <= 1; j++) {
			for (int k = -1; k <= 1; k++) {

				// check if its end of the board
				try {
					while (" ".equals(chessBoard[r + temp * j][c + temp * k]))
						temp++;

					// rook or queen
					if ((j != 0 && k == 0) || (j == 0 && k != 0)) {
						if("r".equals(chessBoard[r + temp * j][c + temp * k]) || 
								"q".equals(chessBoard[r + temp * j][c + temp * k]))
							return false;
					}

					// bishop or queen
					if (!(j == 0 || k == 0)) {
						if("b".equals(chessBoard[r + temp * j][c + temp * k]) || 
								"q".equals(chessBoard[r + temp * j][c + temp * k]))
							return false;
					}
				} catch (Exception e) { }
				temp = 1;
			}

		}		
		// for knight
		for(int j=-2; j<=2;j++){
			for(int k=-2;k<=2;k++){
				try{
					if(j!=0 && k!=0 && j!=k && j!=(-1)*k){
						if("k".equals(chessBoard[r+j][c+k]))
							return false;
					}
				}
				catch(Exception e){
				}
			}
		}

		// for pawn
		for(int j=-1; j<=1;j+=2){
			try{
				if("p".equals(chessBoard[r-1][c+j]))
					return false;
			}
			catch(Exception e){}
		}

		//for king
		for(int j=-1; j<=1;j++){
			for(int k=-1;k<=1;k++){
				temp=1;
				try{
					if(!(j==0 && k==0)){
						if("a".equals(chessBoard[r+temp*j][c+temp*k]))
							return false;
					}
				}
				catch(Exception e){}
			}
		}
		return true;
	}
}
