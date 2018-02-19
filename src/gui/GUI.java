package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;

import mtg.WagicalMTGCard;
import newall.NewallFileLocator;
import newall.NewallFileUpdater;
import mtg.MTGPriceComparer;
import mtg.SetList;
import table.PriceDifferenceTable;
import table.PriceDifferenceTableModel;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private NewallFileLocator newallFileLocator;
	private NewallFileUpdater newallFileUpdater;
	private MTGPriceComparer priceComparer;
	
	private JPanel inputPanel;
	
	private JLabel promptMaxCommonPriceDiff;
	private JFormattedTextField inputMaxCommonPriceDiff;
	
	private JLabel promptMaxUncommonPriceDiff;
	private JFormattedTextField inputMaxUncommonPriceDiff;
	
	private JLabel promptMaxRarePriceDiff;
	private JFormattedTextField inputMaxRarePriceDiff;
	
	private JPanel outOfStockPanel;
	
	private JCheckBox checkOutOfStockCommons;
	private JCheckBox checkOutOfStockUncommons;
	private JCheckBox checkOutOfStockRares;
	
	private JComboBox <String> setComboBox;
	
	private JButton getPricesButton;
	private JButton updatePricesButton;
	
	private JTextField cardSearchField;
	
	private JScrollPane scrollPane;
	private PriceDifferenceTable priceDiffTable;
	private TableRowSorter <TableModel> sorter;
	private PriceDifferenceTableChangeListener tableChangeListener;
	
	private ArrayList<WagicalMTGCard> newPriceCards;
	
	public GUI(){
		super("Price Checker");
		init();
	}
	
	/**
	 * Initializes GUI
	 */
	public void init(){
		this.setPreferredSize(new Dimension(800, 500));
		this.setMinimumSize(new Dimension(600, 500));
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		try {
			newallFileLocator=new NewallFileLocator();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
        if(newallFileLocator.getNewallFile()==null){
        	openNewallFileChooser();
        }
		
        newallFileUpdater=new NewallFileUpdater(newallFileLocator.getNewallFile());
		priceComparer=new MTGPriceComparer(newallFileLocator.getNewallFile());
		
		inputPanel=new JPanel();
		inputPanel.setBorder(BorderFactory.createTitledBorder("Max Price Differences"));
		
		promptMaxCommonPriceDiff=new JLabel("Commons: $");
		inputMaxCommonPriceDiff=getPriceTextField();
	
		promptMaxUncommonPriceDiff=new JLabel("Uncommons: $");
		inputMaxUncommonPriceDiff=getPriceTextField();
		
		promptMaxRarePriceDiff=new JLabel("Rares: $");
		inputMaxRarePriceDiff=getPriceTextField();
		
		outOfStockPanel=new JPanel();
		outOfStockPanel.setBorder(BorderFactory.createTitledBorder("Include Out of Stock Cards"));
		
		checkOutOfStockCommons=new JCheckBox("Common", true);
		checkOutOfStockUncommons=new JCheckBox("Uncommon", true);
		checkOutOfStockRares=new JCheckBox("Rare", false);
		
		setComboBox=new JComboBox <String> (SetList.getSetNameList());
		
		getPricesButton=new JButton("Get Prices");
		getPricesButton.addActionListener(new GetPriceButtonListener());
		
		updatePricesButton=new JButton("Update Prices");
		updatePricesButton.addActionListener(new UpdatePricesButtonListener());
		updatePricesButton.setToolTipText("Caution: This feature still needs to be tested.");
		
		cardSearchField=new JTextField();
		cardSearchField.getDocument().addDocumentListener(new CardSearchFieldListener());
		
		priceDiffTable=new PriceDifferenceTable();
		
		tableChangeListener=new PriceDifferenceTableChangeListener();
		
		resizeTableToPreferredSize();
		
		scrollPane=new JScrollPane();
		scrollPane.setViewportView(priceDiffTable);
		
		sorter=new TableRowSorter<TableModel>(priceDiffTable.getModel());
		priceDiffTable.setRowSorter(sorter);
		
		newPriceCards=new ArrayList<WagicalMTGCard>();
		
		//Auto generated GUI.
		 javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
	        inputPanel.setLayout(inputPanelLayout);
	        inputPanelLayout.setHorizontalGroup(
	            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(inputPanelLayout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
	                        .addComponent(promptMaxCommonPriceDiff)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(inputMaxCommonPriceDiff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
	                        .addComponent(promptMaxUncommonPriceDiff)
	                        .addGap(10, 10, 10)
	                        .addComponent(inputMaxUncommonPriceDiff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
	                        .addComponent(promptMaxRarePriceDiff)
	                        .addGap(10, 10, 10)
	                        .addComponent(inputMaxRarePriceDiff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                .addContainerGap())
	        );
	        inputPanelLayout.setVerticalGroup(
	            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(inputPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(promptMaxCommonPriceDiff)
	                    .addComponent(inputMaxCommonPriceDiff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(promptMaxUncommonPriceDiff)
	                    .addComponent(inputMaxUncommonPriceDiff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(promptMaxRarePriceDiff)
	                    .addComponent(inputMaxRarePriceDiff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addContainerGap(14, Short.MAX_VALUE))
	        );

	        javax.swing.GroupLayout outOfStockPanelLayout = new javax.swing.GroupLayout(outOfStockPanel);
	        outOfStockPanel.setLayout(outOfStockPanelLayout);
	        outOfStockPanelLayout.setHorizontalGroup(
	            outOfStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(outOfStockPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(checkOutOfStockCommons)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, Short.MAX_VALUE)
	                .addComponent(checkOutOfStockUncommons)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(checkOutOfStockRares))
	        );
	        outOfStockPanelLayout.setVerticalGroup(
	            outOfStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outOfStockPanelLayout.createSequentialGroup()
	                .addGap(0, 0, Short.MAX_VALUE)
	                .addGroup(outOfStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(checkOutOfStockCommons)
	                    .addComponent(checkOutOfStockUncommons)
	                    .addComponent(checkOutOfStockRares)))
	        );

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(cardSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(updatePricesButton)
	                .addContainerGap())
	            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(setComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(outOfStockPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(getPricesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(11, 11, 11)
	                .addComponent(outOfStockPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(setComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(getPricesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addGap(5, 5, 5)
	                        .addComponent(cardSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addGap(64, 64, 64)
	                        .addComponent(updatePricesButton)))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
	        );

	        pack();
	        setVisible(true);
	    }                      

	
	
	/**
	 * Opens file explorer to allow the user to find the newall file. 
	 * The selected file is then checked to ensure the newall file is valid.
	 */
	private void openNewallFileChooser(){
	 	JFileChooser fileChooser=new JFileChooser();
    	fileChooser.setDialogTitle("Find Newall File");
    	fileChooser.setAcceptAllFileFilterUsed(false);
      	fileChooser.setFileFilter(new FileFilter(){
					@Override
					//Only showing file directories and files with the name "newall."
					public boolean accept(File pathname) {
						if(pathname.isDirectory() || pathname.getName().equals("newall")){
							return true;
						}
						
						return false;
					}

					@Override
					public String getDescription() {
						return "newall";
					}	
	        	});
        
    	
    	if(fileChooser.showOpenDialog(null)==JFileChooser.CANCEL_OPTION){
    		System.exit(0);
    	}
    	
    	try {
			newallFileLocator.setNewallFilePath(fileChooser.getSelectedFile().getAbsolutePath());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Gets the JFormattedTextField for the price text fields.
	 * @return JFormattedTextField
	 */
	public static JFormattedTextField getPriceTextField(){
		JFormattedTextField priceTextField=new JFormattedTextField(getPriceFormatter());
		priceTextField.setValue(0.0);
		priceTextField.setColumns(4);
		priceTextField.setEditable(true);
		
		return priceTextField;
	}
	
	/**
	 * Gets a NumberFormatter that represents the format of a price.
	 * @return NumberFormatter
	 */
	public static NumberFormatter getPriceFormatter(){
		NumberFormat format=DecimalFormat.getInstance();
		format.setMinimumFractionDigits(2);
		format.setMinimumIntegerDigits(1);
		
		NumberFormatter formatter=new NumberFormatter(format);
		formatter.setMinimum(0.0);
		formatter.setAllowsInvalid(true);
		formatter.setCommitsOnValidEdit(true);
		formatter.setOverwriteMode(false);

		return formatter;
	}
	
	/**
	 * Resizes the columns of priceDiffTable to their preferred width.
	 */
	public void resizeTableToPreferredSize(){
		TableColumnModel colModel=priceDiffTable.getColumnModel();
		
		colModel.getColumn(0).setPreferredWidth(50);
		colModel.getColumn(1).setPreferredWidth(450);
		colModel.getColumn(2).setPreferredWidth(50);
		colModel.getColumn(3).setPreferredWidth(125);
		colModel.getColumn(4).setPreferredWidth(125);
	}
	
	/**
	 * An ActionListener for the getPricesButton.
	 * @author Samuel Scheffler
	 *
	 */
	private class GetPriceButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==getPricesButton){
				try {
					newPriceCards.clear();
					cardSearchField.setText("");
					
					priceComparer.setMaxCommonPriceDiff((double)inputMaxCommonPriceDiff.getValue());
					priceComparer.setMaxUncommonPriceDiff((double)inputMaxUncommonPriceDiff.getValue());
					priceComparer.setMaxRarePriceDiff((double)inputMaxRarePriceDiff.getValue());
					
					priceComparer.setCheckOutOfStockCommons(checkOutOfStockCommons.isSelected());
					priceComparer.setCheckOutOfStockUncommons(checkOutOfStockUncommons.isSelected());
					priceComparer.setCheckOutOfStockRares(checkOutOfStockRares.isSelected());
					
					PriceDifferenceTableModel priceDiffModel=priceComparer.Compare((String)setComboBox.getSelectedItem());
					
					if(priceDiffModel==null){
						JOptionPane.showMessageDialog(null, "TCGPlayer URL for " + (String)setComboBox.getSelectedItem(), "URL not found", JOptionPane.ERROR_MESSAGE);
					}
					
					else{
						//Updating sorter with the new TableModel.
						sorter.setModel(priceDiffModel);
						//Adding tableChangeListener to the new model.
						priceDiffModel.addTableModelListener(tableChangeListener);
						priceDiffTable.setModel(priceDiffModel);
						//Setting updated to the JTable's rowSorter.
						priceDiffTable.setRowSorter(sorter);
						
						resizeTableToPreferredSize();
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	private class UpdatePricesButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==updatePricesButton){
				try {
					if(priceDiffTable.isEditing()){
						priceDiffTable.getCellEditor().stopCellEditing();
					}
					
					if(newPriceCards.size()>0){
						int selection=JOptionPane.showConfirmDialog(null, "Are you sure you would like to update the newall? This is a new feature and may have some bugs.", "Update Newall", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
						
						if(selection==JOptionPane.YES_OPTION){
							newallFileUpdater.update(setComboBox.getSelectedItem().toString(), newPriceCards);
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * A DocumentListener for the cardSearchField, that updates the sorter filter when the document is changed.
	 * @author Samuel Scheffler
	 *
	 */
	private class CardSearchFieldListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			if(e.getDocument()==cardSearchField.getDocument()){
				updateFilter();
			}
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if(e.getDocument()==cardSearchField.getDocument()){
				updateFilter();
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			if(e.getDocument()==cardSearchField.getDocument()){
				updateFilter();
			}
		}
		
		public void updateFilter(){
			String text=cardSearchField.getText();
			
			//If cardSearchField is not empty, update the row filter.
			if(text.length()>0){
				//Searching card names in the table for a match.
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
			}
			
			else{
				sorter.setRowFilter(null);
			}
		}
	}
	
	/**
	 * A TableModelListener for PriceDifferenceTableModel, that listens for updates made to the new price column.
	 * 
	 * @author Samuel Scheffler
	 *
	 */
	private class PriceDifferenceTableChangeListener implements TableModelListener{
		@Override
		public void tableChanged(TableModelEvent e) {
			if(e.getSource()==priceDiffTable.getModel()){
				newPriceCards.add(((PriceDifferenceTableModel)priceDiffTable.getModel()).getNewPriceCard(e.getLastRow()));
			}
		}
		
	}
}

