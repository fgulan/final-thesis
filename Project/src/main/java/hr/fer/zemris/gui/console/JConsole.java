package hr.fer.zemris.gui.console;

import javax.swing.*;
import java.awt.*;

/**
 * @author Filip Gulan
 */
public class JConsole extends JPanel {

    private DefaultListModel<String> listModel;
    private JList list;

    public JConsole() {
        super(new BorderLayout());
        this.listModel = new DefaultListModel<>();
        this.list = new JList(listModel);

        list.setBackground(Color.black);
        list.setForeground(Color.green);

        JScrollPane listScroller = new JScrollPane(list);
        add(listScroller, BorderLayout.CENTER);
    }

    public void addLog(String log) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    listModel.addElement(log);
                }
            });
        } else {
            listModel.addElement(log);
        }
    }

    public void clear() {
        listModel.clear();
    }
}
