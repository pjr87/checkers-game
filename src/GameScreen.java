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
	private JLabel[][] gLblSpaces;


	//Constructor - GUI Setup
	public GameScreen(JLabel[][] spaces){

		gLblSpaces = spaces;
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
		for(int i = 0; i < spaces.length;i++){
			layout.gridx=i+2;
			for(int j = 0;j<spaces[i].length;j++){
				layout.gridy=j+2;
				if(layout.gridx%2 != layout.gridy%2){
					spaces[i][j] = new JLabel();
					spaces[i][j].setBackground(new Color(160, 255, 170));
				}
				else{
					spaces[i][j] = new JLabel();
					spaces[i][j].setBackground(new Color(238, 238, 224));
				}
				spaces[i][j].setPreferredSize(new Dimension(65, 65));
				spaces[i][j].setOpaque(true);
				spaces[i][j].setBorder(BorderFactory.createLineBorder(Color.black));

				this.add(spaces[i][j], layout);
			}
		}
	}

	public void setpBtnResignAction(ActionListener a) { gBtnResign.addActionListener(a); }

	public void setpBtnDrawAction(ActionListener a) { gBtnDraw.addActionListener(a); }

	public void setpBtnNotationAction(ActionListener a) { gBtnNotation.addActionListener(a); }

	public void setAllSpacesMouseListener(MouseAdapter mouseA) {
		for (JLabel[] jLabels : gLblSpaces) {
			for (JLabel jLabel : jLabels){
				jLabel.addMouseListener(mouseA);
			}
		}
	}
}
