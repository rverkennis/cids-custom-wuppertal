/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.objecteditors.wunda_blau;

import com.vividsolutions.jts.geom.Geometry;

import java.awt.Component;
import java.awt.Frame;

import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;

import de.cismet.cids.custom.objectrenderer.utils.billing.BillingPopup;
import de.cismet.cids.custom.objectrenderer.utils.billing.ProductGroupAmount;
import de.cismet.cids.custom.objectrenderer.wunda_blau.BaulastenReportGenerator;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.downloadmanager.Download;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

import static de.cismet.cids.custom.objecteditors.wunda_blau.Alb_baulastblattEditor.log;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class Alb_baulastReportDialog extends javax.swing.JDialog {

    //~ Static fields/initializers ---------------------------------------------

    private static Alb_baulastReportDialog INSTANCE;

    //~ Instance fields --------------------------------------------------------

    private boolean createReport = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form Alb_baulastReportDialog.
     */
    private Alb_baulastReportDialog() {
        super((Frame)null, true);
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setTitle(org.openide.util.NbBundle.getMessage(Alb_baulastReportDialog.class, "Alb_baulastReportDialog.title")); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 123));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                Alb_baulastReportDialog.class,
                "Alb_baulastReportDialog.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel2,
            org.openide.util.NbBundle.getMessage(
                Alb_baulastReportDialog.class,
                "Alb_baulastReportDialog.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        jTextField1.setText(org.openide.util.NbBundle.getMessage(
                Alb_baulastReportDialog.class,
                "Alb_baulastReportDialog.jTextField1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jTextField1, gridBagConstraints);

        jTextField2.setText(org.openide.util.NbBundle.getMessage(
                Alb_baulastReportDialog.class,
                "Alb_baulastReportDialog.jTextField2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jTextField2, gridBagConstraints);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        org.openide.awt.Mnemonics.setLocalizedText(
            jButton2,
            org.openide.util.NbBundle.getMessage(
                Alb_baulastReportDialog.class,
                "Alb_baulastReportDialog.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });
        jPanel2.add(jButton2);

        org.openide.awt.Mnemonics.setLocalizedText(
            jButton1,
            org.openide.util.NbBundle.getMessage(
                Alb_baulastReportDialog.class,
                "Alb_baulastReportDialog.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
        jPanel2.add(jButton1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        createReport = true;
        setVisible(false);
    }                                                                            //GEN-LAST:event_jButton1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }                                                                            //GEN-LAST:event_jButton2ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Alb_baulastReportDialog getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Alb_baulastReportDialog();
        }
        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   type    DOCUMENT ME!
     * @param   beans   DOCUMENT ME!
     * @param   parent  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean showAndDoDownload(final BaulastenReportGenerator.Type type,
            final Collection<CidsBean> beans,
            final Component parent) {
        createReport = false;
        jTextField2.setText(new SimpleDateFormat("yy").format(new Date()) + "-");

        jTextField2.setCaretPosition(jTextField2.getDocument().getLength());
        jTextField2.requestFocusInWindow();

        StaticSwingTools.showDialog(parent, this, true);
        if (createReport) {
            return doDownload(type, jTextField1.getText(), jTextField2.getText(), beans);
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   type                DOCUMENT ME!
     * @param   projektbezeichnung  DOCUMENT ME!
     * @param   auftragsnummer      DOCUMENT ME!
     * @param   beans               DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean doDownload(final BaulastenReportGenerator.Type type,
            final String projektbezeichnung,
            final String auftragsnummer,
            final Collection<CidsBean> beans) {
        try {
            if (BillingPopup.doBilling(
                            "bla",
                            "no.yet",
                            (Geometry)null,
                            new ProductGroupAmount("ea_bla", beans.size()))) {
                if (DownloadManagerDialog.getInstance().showAskingForUserTitleDialog(this)) {
                    String projectname = projektbezeichnung;
                    if ((projectname == null) || (projectname.trim().length() == 0)) {
                        projectname = "";
                    }
                    final Download download = BaulastenReportGenerator.generateDownload(
                            type,
                            beans,
                            auftragsnummer,
                            projectname);
                    DownloadManager.instance().add(download);
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Error when trying to produce a alkis product", e);
        }
        return false;
    }
}