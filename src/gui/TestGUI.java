package gui;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import mtg.MTGCard;
import mtg.WagicalMTGCard;
import table.PriceDifferenceTable;
import table.PriceDifferenceTableModel;

public class TestGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private PriceDifferenceTableModel priceDiffModel;
	private PriceDifferenceTable priceDiffTable;
	private JScrollPane scrollPane;
	
	public TestGUI(){
		super("Test");
		setSize(500, 600);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new FlowLayout());
		
		priceDiffModel=new PriceDifferenceTableModel();
		priceDiffTable=new PriceDifferenceTable(priceDiffModel);
		scrollPane=new JScrollPane(priceDiffTable);
		
		for(int i=1; i<=15; i++){
			priceDiffModel.addRow(new WagicalMTGCard("Test" + i, 'C', i, 0), new MTGCard("Test" + i, 'C', i+1));
		}
		
		add(scrollPane);
		
		
		setVisible(true);
	}
}
