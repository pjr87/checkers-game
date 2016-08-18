import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

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

	final static private Color clrDisabledGreen = new Color(160, 255, 170);
	final static private Color clrEnabledGreen = new Color(0, 255, 127);
	final static private Color clrOffTiles = new Color(238, 238, 224);
	final static private Color clrDisableBorders = new Color(205, 205, 193);
	final static private Color clrEnableBorders = new Color(139, 136, 120);


	//Constructor - GUI Setup
	public GameScreen(JLabel[] squares){

		gLblSquares = new JLabel[64];
		int indx=0;
		for(int i = 0; i< squares.length; i++){
			gLblSquares[indx++]=new JLabel();
			gLblSquares[indx++]=squares[i];
		}

		gLblSquares = squares;
		//set layout
		this.setLayout(new GridBagLayout());

		JLabel padding = new JLabel("   ");
		//Instantiate variables
		gBtnResign = new JButton("   Resign   ");
		gBtnNotation = new JButton("View Notation");
		gBtnDraw = new JButton("Offer Draw");

		GridBagConstraints layout = new GridBagConstraints();
		//layout.fill=GridBagConstraints.HORIZONTAL;
		//layout.anchor=GridBagConstraints.WEST;
		layout.gridx=0;
		layout.gridy=0;
		this.add(gBtnNotation,layout);

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
		int yCounter=0;
		for(int i = 0;i<squares.length;i++){
			layout.gridx=i%8+2;
			if(yCounter==7){
				yCounter=0;
				layout.gridy++;
			}

			if(layout.gridx%2 != layout.gridy%2)
				squares[i].setBackground(clrDisabledGreen);

			else
				squares[i].setBackground(clrOffTiles);

			squares[i].setPreferredSize(new Dimension(65, 65));
			squares[i].setOpaque(true);
			squares[i].setBorder(BorderFactory.createLineBorder(clrDisableBorders));

			this.add(squares[i], layout);
			yCounter++;
		}


		squares[3].setBackground(clrEnabledGreen);
		squares[3].setBorder(BorderFactory.createLineBorder(clrEnableBorders));

	}
	public void setpBtnResignAction(ActionListener a) { gBtnResign.addActionListener(a); }

	public void setpBtnDrawAction(ActionListener a) { gBtnDraw.addActionListener(a); }

	public void setpBtnNotationAction(ActionListener a) { gBtnNotation.addActionListener(a); }

	public void setAllSpacesMouseListener(MouseAdapter mouseA) {
		for (int i =0; i<gLblSquares.length;i++){
			if(i%2==0)
				gLblSquares[i].addMouseListener(mouseA);
		}
	}
}
