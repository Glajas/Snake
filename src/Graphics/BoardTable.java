package Graphics;

import Listeners.LogicListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.EventObject;

public class BoardTable extends JTable {

    public BoardTable(int rowCount, int columnCount) {

        setModel(new DefaultTableModel(rowCount, columnCount));
        setMinimumSize(new Dimension(520,300));
        setMaximumSize(new Dimension(520,300));

        setCellSelectionEnabled(false);

        setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        setCellEditor(new NonEditableTableCellEditor());

        setRowHeight(20);
        for (int i = 0; i < columnCount; i++) {
            getColumnModel().getColumn(i).setPreferredWidth(20);
            getColumnModel().getColumn(i).setMinWidth(20);
            getColumnModel().getColumn(i).setMaxWidth(20);
        }

        getTableHeader().setVisible(false);

        setGridColor(Color.GRAY);

        getCellEditor().stopCellEditing();
    }
    static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            Component component = super.getTableCellRendererComponent(table, value, false, false, row, column);

            if (value == null) {
                setBackground(Color.WHITE);
                setText(""); // Ukryj tekst w komórce
            } else {
                setBackground((Color) value);
                setText(""); // Ukryj tekst w komórce
            }

            return component;
        }
    }

    static class NonEditableTableCellEditor extends DefaultCellEditor {
        public NonEditableTableCellEditor() {
            super(new JTextField());
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return false;
        }
    }

}
