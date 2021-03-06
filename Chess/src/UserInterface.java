import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	//mouseX, mouseY is where we click down
	//newMouseX, newMouseY is where we release
	static int mouseX, mouseY, newMouseY, newMouseX;
	static int squareSize=32;	//to change the size of chess board when we change size of window
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.yellow);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for(int i=0;i<64;i+=2){				//i+=2 because we are adding pairs of white-black
			g.setColor(new Color(255,200,100));
			g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
			g.setColor(new Color(150,50,30));
			g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);

		}
		Image chessPieceImage;
		chessPieceImage = new ImageIcon("ChessPieces.png").getImage();
		//g.drawImage(chessPieceImage, x, 0, x+100, 100, x, 0, x+100, 100, this);
		for(int i=0;i<64;i++){
			int j=-1,k=-1;		//coordinates for getting the chessBoard piece image 
			switch (AlphaBetaChess.chessBoard[i / 8][i % 8]) {
			case "P":
				j = 5;
				k = 0;
				break;
			case "p":
				j = 5;
				k = 1;
				break;
			case "R":
				j = 2;
				k = 0;
				break;
			case "r":
				j = 2;
				k = 1;
				break;
			case "K":
				j = 4;
				k = 0;
				break;
			case "k":
				j = 4;
				k = 1;
				break;
			case "B":
				j = 3;
				k = 0;
				break;
			case "b":
				j = 3;
				k = 1;
				break;
			case "Q":
				j = 1;
				k = 0;
				break;
			case "q":
				j = 1;
				k = 1;
				break;
			case "A":
				j = 0;
				k = 0;
				break;
			case "a":
				j = 0;
				k = 1;
				break;
			}
			
			//if its an empty spot
			if(j!=-1 && k!=-1){		
				g.drawImage(chessPieceImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*64, k*64, (j+1)*64, (k+1)*64, this);

			}
		}

//		g.setColor(Color.BLUE);
//		g.fillRect(x - 20, y - 20, 40, 40);
//		g.setColor(Color.CYAN);
//		g.fillRect(40, 20, 80, 50);
//		g.drawString("Pragya", x, y);
		

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getX()<8*squareSize && e.getY()<8*squareSize){
			// if inside the board, not yellow area
			mouseX=e.getX();
			mouseY=e.getY();
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getX()<8*squareSize && e.getY()<8*squareSize){
			// if inside the board, not yellow area
			newMouseX=e.getX();
			newMouseY=e.getY();
			if(e.getButton()==MouseEvent.BUTTON1){
				String dragMove;
				if(newMouseY/squareSize==0 && mouseY/squareSize==1 && "P".equals(AlphaBetaChess.chessBoard[mouseY/squareSize][mouseX/squareSize])){
					//pawn promotion...
					 dragMove=""+mouseX/squareSize+newMouseX/squareSize+AlphaBetaChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";

				}else{
					// regular move...
					 dragMove=""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+AlphaBetaChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize];

				}
				String userPossibilities=AlphaBetaChess.possibleMoves();
				if(userPossibilities.contains(dragMove)){
					//if valid move
					AlphaBetaChess.makeMove(dragMove);
					AlphaBetaChess.flipBoard();
					AlphaBetaChess.makeMove(AlphaBetaChess.alphaBeta(AlphaBetaChess.globalDepth, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0));
					AlphaBetaChess.flipBoard();
					repaint();
				}
			}			
		}
	}

	@Override 
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}
}
