package table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class PriceDifferenceTable extends JTable{
	private static final long serialVersionUID = 1L;
	
	public PriceDifferenceTable(){
		this(new PriceDifferenceTableModel());
	}
	
	public PriceDifferenceTable(PriceDifferenceTableModel priceDiffModel){
		super(priceDiffModel);
		setAutoCreateRowSorter(true);
		
		//Disallowing user from dragging columns.
		getTableHeader().setReorderingAllowed(false);
	}
	
	@Override
	public void setModel(TableModel dataModel){
		//Checking whether dataModel is an instance of PriceDifferenceTableModel.
		if(dataModel instanceof PriceDifferenceTableModel){
			super.setModel(dataModel);
		} 
		
		else{
			System.err.println("dataModel must be an instance of PriceDifferenceTableModel.");
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		//Only allowing the last column in a row to be edited.
		if(column<getModel().getColumnCount()-1){
			return false;
		}
		
		else{
			return true;
		}
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
		Component c=super.prepareRenderer(renderer, row, column);
		if(column==getModel().getColumnCount()-1){
			JComponent jc=(JComponent)c;
			
			jc.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, Color.BLACK));
		}
		
		return c;
	}
}


