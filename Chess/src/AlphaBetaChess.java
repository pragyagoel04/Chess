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

	static int kingPositionC=60, kingPositionL=4;
	public static void main(String[] args) {
		/*JFrame f=new JFrame("CHESS..!");
		UserInterface ui=new UserInterface();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(ui);
		f.setSize(500,500);
		f.setVisible(true);
		 */
		System.out.println(possibleMoves());
	}

	public static String possibleMoves(){
		
		StringBuilder list=new StringBuilder();
		for(int i=0;i<64;i++){
			switch(chessBoard[i/8][i%8]){
			case "P":
				list.append(possibleP(i));
				break;
			case "R":
				list.append(possibleR(i));
				break;
			case "K":
				list.append(possibleK(i));
				break;
			case "B":
				list.append(possibleB(i));
				break;
			case "Q":
				list.append(possibleQ(i));
				break;
			case "A":
				list.append(possibleA(i));
				break;
			}
		}
		return list.toString(); //x1,y1,x2,y2,captured piece
	}

	private static Object possibleP(int i) {
		StringBuilder list= new StringBuilder();
		return list.toString();
	}

	private static Object possibleQ(int i) {
		StringBuilder list= new StringBuilder();
		return list.toString();
	}

	private static Object possibleB(int i) {
		StringBuilder list= new StringBuilder();
		return list.toString();
	}

	private static Object possibleK(int i) {
		StringBuilder list= new StringBuilder();
		return list.toString();
	}

	private static Object possibleR(int i) {
		StringBuilder list= new StringBuilder();
		return list.toString();
	}

	private static String possibleA(int i) {
		StringBuilder list= new StringBuilder();
		String oldPiece;
		int r=i/8, c=i%8;
		for(int j=0; j<9; j++){
			if(j!=4){
				try{
					if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])){
						oldPiece=chessBoard[r-1+j/3][c-1+j%3];
						chessBoard[r-1+j/3][c-1+j%3]=chessBoard[r][c];
						chessBoard[r][c]=" ";
						int kingTemp=kingPositionC;
						kingPositionC=(r-1+j/3)*8+c-1+j%3;
						if(kingSafe()){
							list.append(String.valueOf(r)+String.valueOf(c)+String.valueOf(r-1+j/3)+String.valueOf(c-1+j%3)+String.valueOf(oldPiece));
						}
						chessBoard[r][c]=chessBoard[r-1+j/3][c-1+j%3];
						chessBoard[r-1+j/3][c-1+j%3]=oldPiece;
						kingPositionC=kingTemp;
					}

				}catch(Exception e){}
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


