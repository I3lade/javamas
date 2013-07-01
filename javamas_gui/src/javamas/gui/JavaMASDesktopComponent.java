/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JavaMASDesktopComponent.java
 *
 * Created on 23 avr. 2013, 12:26:56
 */
package javamas.gui;

/**
 *
 * @author guillaume-monet
 */
public class JavaMASDesktopComponent extends javax.swing.JPanel {

	/** Creates new form JavaMASDesktopComponent */
	public JavaMASDesktopComponent() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bottom_split = new javax.swing.JSplitPane();
        tabbed_print = new javax.swing.JTabbedPane();
        sp_debug = new javax.swing.JScrollPane();
        debug_textarea = new javax.swing.JTextArea();
        sp_error = new javax.swing.JScrollPane();
        error_textarea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        upper_split = new javax.swing.JSplitPane();
        main_desktop = new javax.swing.JDesktopPane();
        sp_main_tree = new javax.swing.JScrollPane();
        main_tree = new javax.swing.JTree();
        sim_toolbar = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        bottom_split.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        bottom_split.setResizeWeight(0.85);

        debug_textarea.setColumns(20);
        debug_textarea.setRows(5);
        sp_debug.setViewportView(debug_textarea);

        tabbed_print.addTab("Debug", sp_debug);

        error_textarea.setColumns(20);
        error_textarea.setRows(5);
        sp_error.setViewportView(error_textarea);

        tabbed_print.addTab("Error", sp_error);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        tabbed_print.addTab("Running", jScrollPane1);

        bottom_split.setRightComponent(tabbed_print);

        upper_split.setResizeWeight(0.1);
        upper_split.setRightComponent(main_desktop);

        sp_main_tree.setViewportView(main_tree);

        upper_split.setLeftComponent(sp_main_tree);

        bottom_split.setLeftComponent(upper_split);

        sim_toolbar.setFloatable(false);
        sim_toolbar.setRollover(true);

        jButton1.setText("Start");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sim_toolbar.add(jButton1);

        jButton2.setText("Next Step");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sim_toolbar.add(jButton2);

        jButton4.setText("Pause");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sim_toolbar.add(jButton4);

        jButton3.setText("Stop");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sim_toolbar.add(jButton3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sim_toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bottom_split, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sim_toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottom_split, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane bottom_split;
    private javax.swing.JTextArea debug_textarea;
    private javax.swing.JTextArea error_textarea;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JDesktopPane main_desktop;
    private javax.swing.JTree main_tree;
    private javax.swing.JToolBar sim_toolbar;
    private javax.swing.JScrollPane sp_debug;
    private javax.swing.JScrollPane sp_error;
    private javax.swing.JScrollPane sp_main_tree;
    private javax.swing.JTabbedPane tabbed_print;
    private javax.swing.JSplitPane upper_split;
    // End of variables declaration//GEN-END:variables
}
