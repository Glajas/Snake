package Graphics;

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
        setCellEditor(new CustomCellEditor());

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
                setBackground(Color.GRAY);
                setText(null);
            } else {
                setBackground((Color) value);
                setText(null);
            }

            return component;
        }
    }

    static class CustomCellEditor extends DefaultCellEditor {
        public CustomCellEditor() {
            super(new JTextField());
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return false;
        }
    }

}
