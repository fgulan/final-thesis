package hr.fer.zemris.gui.filter;

import hr.fer.zemris.filters.FiltersFactory;
import hr.fer.zemris.filters.IFilter;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class JFilterSelection extends JPanel {

    private DefaultListModel<IFilter> listModel;
    private JList list;
    private IFilter selectedFilter;

    public JFilterSelection() {
        super(new BorderLayout());
        this.listModel = new DefaultListModel<>();
        this.list = new JList(listModel);

        JScrollPane listScroller = new JScrollPane(list);

        final DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(FiltersFactory.getAvailableFilters().toArray());
        selectedFilter = (IFilter) comboBoxModel.getElementAt(0);
        JComboBox comboBox = new JComboBox(comboBoxModel);
        comboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox)e.getSource();
            selectedFilter = (IFilter)cb.getSelectedItem();
        });

        JButton addButton = new JButton("Add filter");
        addButton.addActionListener(e -> {
            listModel.addElement(selectedFilter);
        });

        JButton removeButton = new JButton("Remove filter");
        removeButton.addActionListener(e -> {
            listModel.removeElement(list.getSelectedValue());
        });

        add(listScroller, BorderLayout.CENTER);
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
        lowerPanel.add(comboBox);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(addButton);
        buttons.add(removeButton);
        lowerPanel.add(buttons);
        add(lowerPanel, BorderLayout.PAGE_END);

        add(Box.createRigidArea(new Dimension(5,0)), BorderLayout.LINE_END);
        add(Box.createRigidArea(new Dimension(5,0)), BorderLayout.PAGE_START);
        add(Box.createRigidArea(new Dimension(5,0)), BorderLayout.LINE_START);
    }

    public List<IFilter> getSelectedFilters() {
        return Collections.list(listModel.elements());
    }
}
