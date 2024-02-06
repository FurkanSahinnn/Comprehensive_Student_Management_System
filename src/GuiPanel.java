import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
public class GuiPanel extends JFrame implements ActionListener {
    private JFrame frame;
    private JButton add_btn;
    private JButton delete_btn;
    private JButton update_btn;
    private JButton reset_btn;
    private JButton delete_all;
    private JTextField user_id;
    private JTextField name;
    private JTextArea address;
    private JTextField register;
    private JLabel userIdLabel;
    private JLabel nameLabel;
    private JLabel registerLabel;
    private JLabel addressLabel;
    private JTable studentTable;
    private String[][] studentData;
    private Actions actions;
    private final String[] columnNames = {"ID", "Name", "Register Time", "Address"};
    private DefaultTableModel tableModel;
    public GuiPanel(Actions actions, String[][] studentData) {
        this.actions = actions;
        frame = new JFrame();

        add_btn = new JButton("Add");
        reset_btn = new JButton("Reset");
        delete_btn = new JButton("Delete");
        update_btn = new JButton("Update");
        delete_all = new JButton("Delete All Students");

        user_id = new JTextField();
        name = new JTextField();
        register = new JTextField();
        register.setEditable(false);
        address = new JTextArea();
        userIdLabel = new JLabel("User ID (Auto Increment):");
        nameLabel = new JLabel("User Name:");
        registerLabel = new JLabel("Register Time:");
        addressLabel = new JLabel("Address:");

        this.studentData = studentData;
        tableModel = new DefaultTableModel(studentData, columnNames) {
            // Return false to make cells not editable.
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(340, 90, 400, 300);
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String tableid = tableModel.getValueAt(studentTable.getSelectedRow(), 0).toString();
                String tableName = tableModel.getValueAt(studentTable.getSelectedRow(), 1).toString();
                String tableRegister = tableModel.getValueAt(studentTable.getSelectedRow(), 2).toString();
                String tableAddress = tableModel.getValueAt(studentTable.getSelectedRow(), 3).toString();

                // Set to textfield
                user_id.setText(tableid);
                name.setText(tableName);
                register.setText(tableRegister);
                address.setText(tableAddress);
            }
        });

        nameLabel.setBounds(50, 50, 200, 25);
        name.setBounds(50, 75, 200, 25);

        registerLabel.setBounds(50, 100, 200, 25);
        register.setBounds(50, 125, 200, 25);

        addressLabel.setBounds(50, 150, 200, 25);
        address.setBounds(50, 175, 200, 100);

        add_btn.setBounds(50, 330, 75, 25);
        add_btn.addActionListener(this);

        update_btn.setBounds(130, 330, 75, 25);
        update_btn.addActionListener(this);

        delete_btn.setBounds(50, 360, 75, 25);
        delete_btn.addActionListener(this);

        reset_btn.setBounds(130, 360, 75, 25);
        reset_btn.addActionListener(this);

        delete_all.setBounds(570, 460 , 145, 25);
        delete_all.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.add(scrollPane);
        frame.add(user_id);
        frame.add(userIdLabel);
        frame.add(nameLabel);
        frame.add(name);
        frame.add(registerLabel);
        frame.add(register);
        frame.add(addressLabel);
        frame.add(address);
        frame.add(add_btn);
        frame.add(reset_btn);
        frame.add(delete_btn);
        frame.add(update_btn);
        frame.add(delete_all);
        frame.setVisible(true);
    }

    private void reset() {
        user_id.setText("");
        name.setText("");
        register.setText("");
        address.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Reset
        if (e.getSource() == reset_btn) {
            reset();
        }

        // Add New User
        if (e.getSource() == add_btn) {
            String studentName = name.getText();
            String studentAddress = address.getText();
            if (tableModel.getRowCount() == 0) {
                actions.add(new Student(studentName, studentAddress));
                tableModel.addRow(actions.getLast());
            } else {
                if (actions.findID(studentName, studentAddress) == -1) {
                    // If not found, add the student
                    actions.add(new Student(studentName, studentAddress));
                    tableModel.addRow(actions.getLast());
                } else {
                    // If found, display a message or perform any other action as needed
                    JOptionPane.showMessageDialog(null, "A student with the same name and address already exists.");
                }
            }
            tableModel.fireTableDataChanged();
            reset();
        }

        // Delete User
        if (e.getSource() == delete_btn) {
            if (studentTable.getSelectedRowCount() == 1) {
                int id = Integer.parseInt(user_id.getText());
                tableModel.removeRow(studentTable.getSelectedRow());
                actions.delete(id);
            } else {
                if (studentTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Table is empty.");
                } else {
                    JOptionPane.showMessageDialog(this, "Select single row for delete.");
                }
            }
            tableModel.fireTableDataChanged();
            reset();
        }

        // Update User
        if (e.getSource() == update_btn) {
            if (studentTable.getSelectedRowCount() == 1) {
                String studentName = name.getText();
                String studentAddress = address.getText();
                int id = Integer.parseInt(user_id.getText());
                actions.update(id, 0, studentName);
                actions.update(id, 1, studentAddress);

                tableModel.setValueAt(studentName, studentTable.getSelectedRow(), 1);
                tableModel.setValueAt(studentAddress, studentTable.getSelectedRow(), 3);

                JOptionPane.showMessageDialog(this, "Update Successfully.");
            } else {
                if (studentTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "This is empty.");
                } else {
                    JOptionPane.showMessageDialog(this, "Select single row for update.");
                }
            }
            tableModel.fireTableDataChanged();
        }

        // Delete All Users
        if (e.getSource() == delete_all) {
            int result = JOptionPane.showConfirmDialog(null, "Are You Sure?", "", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                studentTable.selectAll();
                if (studentTable.getSelectedRowCount() > 0) {
                    tableModel.removeRow(studentTable.getSelectedRow());
                    for (int i = 0; i < studentTable.getSelectedRowCount(); i++) {
                        tableModel.removeRow(studentTable.getSelectedRow());
                    }
                }
                if (studentTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Table is empty.");
                }

                actions.deleteAll();
                actions.refreshTable();
            }
        }
    }
}