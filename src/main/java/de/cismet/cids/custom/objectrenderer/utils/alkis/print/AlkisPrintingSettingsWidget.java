/*
 * PrintingSettingsWidget.java
 *
 * Created on 10. Juli 2006, 14:06
 */
package de.cismet.cids.custom.objectrenderer.utils.alkis.print;

import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import de.cismet.cids.custom.objectrenderer.utils.alkis.AlkisCommons;
import de.cismet.cids.custom.objectrenderer.utils.alkis.AlkisCommons.LiegenschaftskarteProdukt;
import de.cismet.cids.custom.objectrenderer.utils.alkis.AlkisCommons.ProduktLayout;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.util.FormatToRealWordCalculator;
import de.cismet.cismap.navigatorplugin.CidsFeature;
import de.cismet.tools.collections.TypeSafeCollections;
import de.cismet.tools.gui.StaticSwingTools;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author stefan
 */
public class AlkisPrintingSettingsWidget extends javax.swing.JDialog implements CidsBeanDropListener {

    private static final String ALKIS_LANDPARCEL_TABLE = "ALKIS_LANDPARCEL";
    private static final ProduktLayout[] LAYOUTS = ProduktLayout.values();
    private static final LiegenschaftskarteProdukt[] PRODUCTS = LiegenschaftskarteProdukt.values();
    private static final Integer[] MASSSTAEBE = new Integer[]{500, 1000, 2000, 5000};
    //
    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private final MappingComponent mappingComponent;
    private final DefaultListModel flurstueckListModel;
    private final AlkisPrintListener mapPrintListener;
    private Geometry allLandparcelGeometryUnion;
//    private String oldInteractionMode;

    /**
     * Creates new form PrintingSettingsWidget
     */
    public AlkisPrintingSettingsWidget(boolean modal, MappingComponent mappingComponent) {
        super(StaticSwingTools.getParentFrame(mappingComponent), modal);
        this.flurstueckListModel = new DefaultListModel();
        initComponents();
        getRootPane().setDefaultButton(cmdOk);
        this.panDesc.setBackground(new Color(216, 228, 248));
        this.mappingComponent = mappingComponent;
//        this.flurstueckListModel.addListDataListener(new FormatProposalListListener());
//        this.oldInteractionMode = "PAN";
        //enable D&D
        new CidsBeanDropTarget(this);
        //init PrintListener
        this.mapPrintListener = new AlkisPrintListener(mappingComponent, this);
        this.mappingComponent.addInputListener(MappingComponent.ALKIS_PRINT, mapPrintListener);
    }

    @Override
    public void setVisible(boolean b) {
        flurstueckListModel.clear();
        Collection<CidsBean> beansToPrint = getAlkisFlurstueckBeansInMap();
        if (beansToPrint.isEmpty()) {
            beansToPrint = getAlkisFlurstueckBeansFromTreeSelection();
        } else if (beansToPrint.size() > 1) {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Sollen alle Flurstückeobjekte der Karte gedruckt werden?", "Flurstücke in Druckauswahl übernehmen", JOptionPane.YES_NO_OPTION);
            if (JOptionPane.NO_OPTION == dialogResult) {
                beansToPrint.clear();
            }
        }

        for (CidsBean currentBean : beansToPrint) {
            flurstueckListModel.addElement(currentBean);
        }

        updateFormatProposal();
        syncOkButtonWithListStatus();
        super.setVisible(b);
    }

    private Collection<CidsBean> getAlkisFlurstueckBeansInMap() {
        final Collection<CidsBean> result = TypeSafeCollections.newArrayList();
        for (Feature feature : mappingComponent.getPFeatureHM().keySet()) {
            if (feature instanceof CidsFeature) {
                CidsFeature cidsFeature = (CidsFeature) feature;
                MetaObject metaObj = cidsFeature.getMetaObject();
                MetaClass mc = metaObj.getMetaClass();
                if (ALKIS_LANDPARCEL_TABLE.equalsIgnoreCase(mc.getTableName())) {
                    result.add(metaObj.getBean());
                }
            }
        }
        return result;
    }

    private Collection<CidsBean> getAlkisFlurstueckBeansFromTreeSelection() {
        final Collection<CidsBean> result = TypeSafeCollections.newArrayList();
        final Collection<?> nodes = ComponentRegistry.getRegistry().getActiveCatalogue().getSelectedNodes();
        if (nodes != null) {
            for (Object nodeObj : nodes) {
                if (nodeObj instanceof ObjectTreeNode) {
                    try {
                        ObjectTreeNode metaTreeNode = (ObjectTreeNode) nodeObj;
                        MetaClass mc = metaTreeNode.getMetaClass();
                        if (ALKIS_LANDPARCEL_TABLE.equalsIgnoreCase(mc.getTableName())) {
                            result.add(metaTreeNode.getMetaObject().getBean());
                        }
                    } catch (Exception ex) {
                        log.error(ex, ex);
                    }
                }
            }
        }
        return result;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panDesc = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        panSettings = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        btnRemove = new javax.swing.JButton();
        scpFlurstuecke = new javax.swing.JScrollPane();
        lstFlurstuecke = new javax.swing.JList();
        jLabel11 = new javax.swing.JLabel();
        scpAdditionalText = new javax.swing.JScrollPane();
        taAdditionalText = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        cbScales = new javax.swing.JComboBox();
        cbFormat = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        chkRotation = new javax.swing.JCheckBox();
        cbProduct = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cmdCancel = new javax.swing.JButton();
        cmdOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(750, 450));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        panDesc.setBackground(java.awt.SystemColor.inactiveCaptionText);
        panDesc.setMaximumSize(new java.awt.Dimension(150, 150));
        panDesc.setMinimumSize(new java.awt.Dimension(150, 150));
        panDesc.setPreferredSize(new java.awt.Dimension(150, 150));
        panDesc.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Schritte");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        panDesc.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 319;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panDesc.add(jSeparator2, gridBagConstraints);

        jLabel2.setText("1. Einstellungen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        panDesc.add(jLabel2, gridBagConstraints);

        jLabel3.setText("2. Druckbereich");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
        panDesc.add(jLabel3, gridBagConstraints);

        jLabel4.setText("3. Laden und Beschriften");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 0, 0);
        panDesc.add(jLabel4, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cismap/commons/gui/res/frameprint.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = -8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 7);
        panDesc.add(jLabel5, gridBagConstraints);

        jSeparator3.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 339;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        panDesc.add(jSeparator3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 127;
        gridBagConstraints.ipady = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panDesc, gridBagConstraints);

        panSettings.setMaximumSize(new java.awt.Dimension(425, 300));
        panSettings.setMinimumSize(new java.awt.Dimension(425, 300));
        panSettings.setPreferredSize(new java.awt.Dimension(425, 300));
        panSettings.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("1. Einstellungen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        panSettings.add(jLabel6, gridBagConstraints);

        jSeparator1.setMaximumSize(new java.awt.Dimension(0, 0));
        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 411;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 5, 0, 5);
        panSettings.add(jSeparator1, gridBagConstraints);

        jSeparator4.setMaximumSize(new java.awt.Dimension(0, 0));
        jSeparator4.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 421;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        panSettings.add(jSeparator4, gridBagConstraints);

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/editors/edit_remove_mini.png"))); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(btnRemove, gridBagConstraints);

        scpFlurstuecke.setMinimumSize(new java.awt.Dimension(250, 110));
        scpFlurstuecke.setPreferredSize(new java.awt.Dimension(250, 110));

        lstFlurstuecke.setModel(flurstueckListModel);
        scpFlurstuecke.setViewportView(lstFlurstuecke);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(scpFlurstuecke, gridBagConstraints);

        jLabel11.setText("Flurstücke");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(jLabel11, gridBagConstraints);

        scpAdditionalText.setMinimumSize(new java.awt.Dimension(250, 110));
        scpAdditionalText.setPreferredSize(new java.awt.Dimension(250, 110));

        taAdditionalText.setColumns(20);
        taAdditionalText.setRows(5);
        scpAdditionalText.setViewportView(taAdditionalText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(scpAdditionalText, gridBagConstraints);

        jLabel10.setText("Zusätzlicher Text");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(jLabel10, gridBagConstraints);

        cbScales.setModel(new DefaultComboBoxModel(MASSSTAEBE));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(cbScales, gridBagConstraints);

        cbFormat.setModel(new DefaultComboBoxModel(LAYOUTS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(cbFormat, gridBagConstraints);

        jLabel8.setText("Maßstab");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(jLabel8, gridBagConstraints);

        jLabel7.setText("Format");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(jLabel7, gridBagConstraints);

        chkRotation.setText("Drehwinkel vorschlagen");
        chkRotation.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkRotation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRotationActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(chkRotation, gridBagConstraints);

        cbProduct.setModel(new DefaultComboBoxModel(PRODUCTS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(cbProduct, gridBagConstraints);

        jLabel9.setText("Produkt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panSettings.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panSettings, gridBagConstraints);

        cmdCancel.setMnemonic('A');
        cmdCancel.setText("Abbrechen");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });
        jPanel1.add(cmdCancel);

        cmdOk.setMnemonic('O');
        cmdOk.setText("Ok");
        cmdOk.setEnabled(false);
        cmdOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOkActionPerformed(evt);
            }
        });
        jPanel1.add(cmdOk);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void cmdOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOkActionPerformed
        try {
            final double scale = (Integer) cbScales.getSelectedItem();
            final ProduktLayout layout = (ProduktLayout) cbFormat.getSelectedItem();
            mapPrintListener.init(scale, layout, allLandparcelGeometryUnion, chkRotation.isSelected());
            dispose();
        } catch (Exception e) {
            log.error("Fehler beim Verarbeiten der Druckeinstellungen", e);
        }
    }//GEN-LAST:event_cmdOkActionPerformed
    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        dispose();
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        final int[] sel = lstFlurstuecke.getSelectedIndices();
        for (int i = sel.length; --i >= 0;) {
            flurstueckListModel.removeElementAt(sel[i]);
        }
        updateFormatProposal();
        syncOkButtonWithListStatus();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void chkRotationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRotationActionPerformed
    }//GEN-LAST:event_chkRotationActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRemove;
    private javax.swing.JComboBox cbFormat;
    private javax.swing.JComboBox cbProduct;
    private javax.swing.JComboBox cbScales;
    private javax.swing.JCheckBox chkRotation;
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JList lstFlurstuecke;
    private javax.swing.JPanel panDesc;
    private javax.swing.JPanel panSettings;
    private javax.swing.JScrollPane scpAdditionalText;
    private javax.swing.JScrollPane scpFlurstuecke;
    private javax.swing.JTextArea taAdditionalText;
    // End of variables declaration//GEN-END:variables

    @Override
    public void beansDropped(ArrayList<CidsBean> beans) {
        if (beans != null) {
            for (CidsBean bean : beans) {
                if (ALKIS_LANDPARCEL_TABLE.equals(bean.getMetaObject().getMetaClass().getTableName())) {
                    if (!flurstueckListModel.contains(bean)) {
                        flurstueckListModel.addElement(bean);
                    }
                }
            }
            updateFormatProposal();
            syncOkButtonWithListStatus();
        }
    }

//    private boolean doCurrentLandparcelsFitNordedToSelectedFormatAndScale() {
//        BoundingBox allGeomBB = new BoundingBox(allLandparcelGeometryUnion);
//        return doesBoundingBoxFitIntoLayout(allGeomBB, (ProduktLayout) cbFormat.getSelectedItem(), (Integer) cbScales.getSelectedItem());
//    }
    private void updateFormatProposal() {
        this.allLandparcelGeometryUnion = unionAllLandparcelGeometries();
        if (allLandparcelGeometryUnion != null) {
            BoundingBox allGeomBB = new BoundingBox(allLandparcelGeometryUnion);
            //current: erst auf passendes format durchtesten, dann massstaebe
            for (int j = 0; j < MASSSTAEBE.length; ++j) {
                for (int i = 0; i < LAYOUTS.length; ++i) {
                    ProduktLayout currentLayout = LAYOUTS[i];
                    Integer currentMassstab = MASSSTAEBE[j];
                    if (doesBoundingBoxFitIntoLayout(allGeomBB, currentLayout, currentMassstab)) {
                        cbFormat.setSelectedIndex(i);
                        cbScales.setSelectedIndex(j);
                        chkRotation.setSelected(false);
                        return;
                    }
                }
            }
            chkRotation.setSelected(true);
            if (allGeomBB.getWidth() >= allGeomBB.getHeight()) {
                cbFormat.setSelectedItem(ProduktLayout.A3Hoch);
            } else {
                cbFormat.setSelectedItem(ProduktLayout.A3Quer);
            }
            cbScales.setSelectedIndex(cbScales.getModel().getSize() - 1);
        } else {
            chkRotation.setSelected(false);
            cbFormat.setSelectedIndex(0);
            cbScales.setSelectedIndex(0);
        }
    }

    public void createProduct(Point center, double rotationAngle) {
        if (flurstueckListModel.size() > 0) {
            final String landParcelCode = AlkisCommons.getLandparcelCodeFromParcelBeanObject(flurstueckListModel.get(0));
            final int massstab = (Integer) cbScales.getSelectedItem();
            final ProduktLayout layout = (ProduktLayout) cbFormat.getSelectedItem();
            final LiegenschaftskarteProdukt product = (LiegenschaftskarteProdukt) cbProduct.getSelectedItem();
            AlkisCommons.Produkte.productKarte(landParcelCode, product, layout, massstab, toInt(rotationAngle), toInt(center.getX()), toInt(center.getY()), taAdditionalText.getText(), false);
        }
    }

    private int toInt(double input) {
        return Double.valueOf(input).intValue();
    }

    private Geometry unionAllLandparcelGeometries() {
        Geometry allGeomUnion = null;
        for (int i = flurstueckListModel.size(); --i >= 0;) {
            Object currentLandparcelObj = flurstueckListModel.get(i);
            if (currentLandparcelObj instanceof CidsBean) {
                CidsBean currentLandparcelBean = (CidsBean) currentLandparcelObj;
                Object currentGeomObj = currentLandparcelBean.getProperty("geometrie.geo_field");
                if (currentGeomObj instanceof Geometry) {
                    Geometry currentGeom = (Geometry) currentGeomObj;
                    if (allGeomUnion == null) {
                        allGeomUnion = currentGeom;
                    } else {
                        allGeomUnion = currentGeom.union(allGeomUnion);
                    }
                }
            }
        }
//        StyledFeature debug = new DefaultStyledFeature();
//        debug.setGeometry(allGeomUnion.getEnvelope());
//        debug.setFillingPaint(Color.RED);
//        mappingComponent.getFeatureCollection().addFeature(debug);
        return allGeomUnion;
    }

    private boolean doesBoundingBoxFitIntoLayout(BoundingBox box, ProduktLayout layout, double massstab) {
        double realWorldLayoutWidth = FormatToRealWordCalculator.toRealWorldValue(layout.width, massstab);
        double realWorldLayoutHeigth = FormatToRealWordCalculator.toRealWorldValue(layout.height, massstab);
        return realWorldLayoutWidth >= box.getWidth() && realWorldLayoutHeigth >= box.getHeight();
    }

    private final void syncOkButtonWithListStatus() {
        cmdOk.setEnabled(flurstueckListModel.size() > 0);
    }
//    final class FormatProposalListListener implements ListDataListener {
//
//        @Override
//        public void intervalAdded(ListDataEvent e) {
//            updateFormatProposal();
//            syncOkButtonWithListStatus();
//        }
//
//        @Override
//        public void intervalRemoved(ListDataEvent e) {
//            intervalAdded(e);
//        }
//
//        @Override
//        public void contentsChanged(ListDataEvent e) {
//            //NOP
//        }
//    }
//    static final class ProductDescription {
//
//        public ProductDescription(String dinFormat, String dateiFormat, String massstab) {
//            this.dinFormat = dinFormat;
//            this.dateiFormat = dateiFormat;
//            this.massstab = massstab;
//        }
//        final String dinFormat;
//        final String dateiFormat;
//        final String massstab;
//
//        @Override
//        public boolean equals(Object obj) {
//            if (obj instanceof ProductDescription) {
//                ProductDescription other = (ProductDescription) obj;
//                return dinFormat.equals(other.dinFormat)
//                        && dateiFormat.equals(other.dateiFormat)
//                        && massstab.equals(other.massstab);
//            }
//            return false;
//        }
//
//        @Override
//        public int hashCode() {
//            int hash = 3;
//            hash = 59 * hash + (this.dinFormat != null ? this.dinFormat.hashCode() : 0);
//            hash = 59 * hash + (this.dateiFormat != null ? this.dateiFormat.hashCode() : 0);
//            hash = 59 * hash + (this.massstab != null ? this.massstab.hashCode() : 0);
//            return hash;
//        }
//    }
}