import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameScreen extends JPanel{

	private static final long serialVersionUID = 1687185123900856843L;

	private JPanel gPnlGameBoard;
	private JButton gBtnResign;
	private JButton gBtnNotation;
	private JButton gBtnDraw;
	private JLabel[] gLblSquares;

	//Constructor - GUI Setup
	public GameScreen(JLabel[] squares){

		gLblSquares = new JLabel[64];
		int indx=0;
		int grn =0;
		int wht =1;
		for(int i = 0; i< squares.length; i++){

			gLblSquares[indx+grn]=new JLabel();
			gLblSquares[indx+grn].setPreferredSize(new Dimension(65, 65));
			gLblSquares[indx+grn].setOpaque(true);
			gLblSquares[indx+grn].setBackground(GUI.clrOffTiles);
			gLblSquares[indx+grn].setBorder(BorderFactory.createLineBorder(GUI.clrDisabledBorders));

			gLblSquares[indx+wht]=squares[i];
			gLblSquares[indx+wht].setPreferredSize(new Dimension(65, 65));
			gLblSquares[indx+wht].setOpaque(true);
			GUI.unhighlightSquare(gLblSquares[indx+wht]);
			indx+=2;

			if((i+1)%4==0){
				int tmp=grn;
				grn=wht;
				wht=tmp;
			}
		}

		//set layout
		this.setLayout(new GridBagLayout());

		//Instantiate variables
		gBtnResign = new JButton("   Resign   ");
		gBtnNotation = new JButton("View Notation");
		gBtnDraw = new JButton("Offer Draw");

		GridBagConstraints layout = new GridBagConstraints();
		//layout.fill=GridBagConstraints.HORIZONTAL;
		//layout.anchor=GridBagConstraints.WEST;
		layout.gridx=0;
		layout.gridy=0;
	//	this.add(gBtnNotation,layout);

		layout.gridx=1;
		layout.gridy=1;
		this.add(new JLabel("   "), layout);

		layout.gridx=6;
		layout.gridy=11;
		layout.gridwidth=3;
		this.add(gBtnResign, layout);

		layout.gridx=3;
		layout.gridy=11;
		layout.gridwidth=3;
		this.add(gBtnDraw, layout);

		layout.gridx=6;
		layout.gridy=10;
		layout.gridwidth=1;
		this.add(new JLabel("   "), layout);

		layout.gridwidth=1;
		layout.gridy=2;
		int yCounter=-1;
		for(int i = 0;i<gLblSquares.length;i++){
			layout.gridx=i%8+2;
			yCounter++;

			this.add(gLblSquares[i], layout);

			if(yCounter==7){
				yCounter=-1;
				layout.gridy++;
			}
		}
	}
	public void setpBtnResignAction(ActionListener a) { gBtnResign.addActionListener(a); }

	public void setpBtnDrawAction(ActionListener a) { gBtnDraw.addActionListener(a); }

	public void setpBtnNotationAction(ActionListener a) { gBtnNotation.addActionListener(a); }
	
	public void setBtnDrawAndResignEnabled(boolean b){
		gBtnDraw.setEnabled(b);
		gBtnResign.setEnabled(b);
	}

	public void setAllSquaresMouseListener(MouseAdapter mouseA) {
		boolean isEven = false;
		for (int i =0; i<64;i++){
			if(isEven && (i+1)%2!=0)
				gLblSquares[i].addMouseListener(mouseA);
			else if(!isEven && i%2!=0)
				gLblSquares[i].addMouseListener(mouseA);
			if((i+1)%8==0)
				isEven=!isEven;
		}
	}

	public void deselectAllSquares() {
		for(int i =0;i<gLblSquares.length;i++){
			if(gLblSquares[i].getBackground()==GUI.clrEnabledGreen){
				gLblSquares[i].setBackground(GUI.clrDisabledGreen);
				gLblSquares[i].setBorder(BorderFactory.createLineBorder(GUI.clrDisabledBorders));
			}
		}
	}
}
