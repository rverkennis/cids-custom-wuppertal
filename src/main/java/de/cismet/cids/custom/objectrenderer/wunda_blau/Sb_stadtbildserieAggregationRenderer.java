/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wunda_blau;

import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.RequestsFullSizeComponent;

import com.guigarage.jgrid.JGrid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.openide.util.Exceptions;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.utils.Sb_stadtbildUtils;
import de.cismet.cids.custom.utils.TifferDownload;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanAggregationRenderer;

import de.cismet.cismap.commons.gui.printing.JasperReportDownload;
import de.cismet.cismap.commons.gui.printing.JasperReportExcelDownload;

import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.TitleComponentProvider;
import de.cismet.tools.gui.downloadmanager.Download;
import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;
import de.cismet.tools.gui.downloadmanager.MultipleDownload;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class Sb_stadtbildserieAggregationRenderer extends javax.swing.JPanel implements RequestsFullSizeComponent,
    CidsBeanAggregationRenderer,
    FooterComponentProvider,
    TitleComponentProvider,
    ListDataListener,
    Sb_stadtbildserieGridObjectListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            Sb_stadtbildserieGridRenderer.class);

    private static final Icon BIN_EMPTY = new javax.swing.ImageIcon(Sb_stadtbildserieAggregationRenderer.class
                    .getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/bin_empty.png"));
    private static final Icon BIN_FULL = new javax.swing.ImageIcon(Sb_stadtbildserieAggregationRenderer.class
                    .getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/bin.png"));
    private static final Icon BIN_RECYCLE = new javax.swing.ImageIcon(Sb_stadtbildserieAggregationRenderer.class
                    .getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/bin_recycle.png"));

    private static final String REPORT_STADTBILDSERIE_URL =
        "/de/cismet/cids/custom/reports/wunda_blau/Stadtbildbericht.jasper";
    private static final String REPORT_STADTBILDSERIE_EXCEL_URL =
        "/de/cismet/cids/custom/reports/wunda_blau/Stadtbildbericht_Excel.jasper";

    //~ Instance fields --------------------------------------------------------

    private boolean wasInfoPanelVisibleBeforeSwitch = true;

    private Collection<CidsBean> cidsBeans = null;
    private SwingWorker enableHighResDownloadWorker;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBin;
    private javax.swing.JButton btnBinRecycle;
    private javax.swing.JButton btnDownloadHighResImage;
    private javax.swing.JButton btnMoveSerienToWarenkorb;
    private javax.swing.JButton btnRemoveWarenkorb;
    private javax.swing.JButton btnReport;
    private javax.swing.Box.Filler filler1;
    private com.guigarage.jgrid.JGrid grdBin;
    private com.guigarage.jgrid.JGrid grdStadtbildserien;
    private com.guigarage.jgrid.JGrid grdWarenkorb;
    private de.cismet.cids.custom.objectrenderer.wunda_blau.Sb_stadtbildserieAggregationRendererInfoPanel
        infoNotAvailable;
    private de.cismet.cids.custom.objectrenderer.wunda_blau.Sb_stadtbildserieAggregationRendererInfoPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMiddle;
    private javax.swing.JLabel lblSwitchToBin;
    private javax.swing.JLabel lblSwitchToSerie;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panButtons;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panTitle;
    private javax.swing.JPanel panTitleString;
    private javax.swing.JPanel pnlInfoPanels;
    private javax.swing.JPanel pnlLeuchtkasten;
    private javax.swing.JPanel pnlSlider;
    private de.cismet.tools.gui.RoundedPanel roundedPanel1;
    private javax.swing.JSlider sldSize;
    private javax.swing.JToggleButton tbtnSlide;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form Sb_stadtbildserieAggregationRenderer.
     */
    public Sb_stadtbildserieAggregationRenderer() {
        initComponents();
        infoNotAvailable.previewImageNotAvailable();
        infoNotAvailable.setEnableTable(false);
        ((PictureSelectionJGrid)grdStadtbildserien).updateInfoPanel();

        sldSize.setValue(grdStadtbildserien.getFixedCellDimension());
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

        panFooter = new javax.swing.JPanel();
        panButtons = new javax.swing.JPanel();
        lblSwitchToSerie = new javax.swing.JLabel();
        lblMiddle = new javax.swing.JLabel();
        lblSwitchToBin = new javax.swing.JLabel();
        panTitle = new javax.swing.JPanel();
        panTitleString = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnDownloadHighResImage = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        tbtnSlide = new javax.swing.JToggleButton();
        roundedPanel1 = new de.cismet.tools.gui.RoundedPanel();
        btnBin = new javax.swing.JButton();
        btnRemoveWarenkorb = new javax.swing.JButton();
        btnBinRecycle = new javax.swing.JButton();
        pnlLeuchtkasten = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grdStadtbildserien = new PictureSelectionJGrid();
        jScrollPane3 = new javax.swing.JScrollPane();
        grdWarenkorb = new de.cismet.cids.custom.objectrenderer.wunda_blau.Sb_SingleStadtbildJGrid();
        jScrollPane2 = new javax.swing.JScrollPane();
        grdBin = new PictureSelectionJGrid();
        pnlSlider = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sldSize = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        btnMoveSerienToWarenkorb = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 0));
        pnlInfoPanels = new javax.swing.JPanel();
        infoPanel = new de.cismet.cids.custom.objectrenderer.wunda_blau.Sb_stadtbildserieAggregationRendererInfoPanel();
        infoNotAvailable =
            new de.cismet.cids.custom.objectrenderer.wunda_blau.Sb_stadtbildserieAggregationRendererInfoPanel();

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.GridBagLayout());

        panButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 6, 0));
        panButtons.setOpaque(false);
        panButtons.setLayout(new java.awt.GridBagLayout());

        lblSwitchToSerie.setFont(new java.awt.Font("Tahoma", 1, 14));                                       // NOI18N
        lblSwitchToSerie.setForeground(new java.awt.Color(255, 255, 255));
        lblSwitchToSerie.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblSwitchToSerie.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/magnifier.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            lblSwitchToSerie,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.lblSwitchToSerie.text"));                             // NOI18N
        lblSwitchToSerie.setEnabled(false);
        lblSwitchToSerie.setPreferredSize(new java.awt.Dimension(186, 17));
        lblSwitchToSerie.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblSwitchToSerieMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panButtons.add(lblSwitchToSerie, gridBagConstraints);

        lblMiddle.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblMiddle.setForeground(new java.awt.Color(255, 255, 255));
        lblMiddle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMiddle.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/basket_shopping.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            lblMiddle,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.lblMiddle.text"));                                          // NOI18N
        lblMiddle.setPreferredSize(new java.awt.Dimension(212, 24));
        lblMiddle.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblMiddleMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        panButtons.add(lblMiddle, gridBagConstraints);

        lblSwitchToBin.setFont(new java.awt.Font("Tahoma", 1, 14));                                         // NOI18N
        lblSwitchToBin.setForeground(new java.awt.Color(255, 255, 255));
        lblSwitchToBin.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/bin_empty.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            lblSwitchToBin,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.lblSwitchToBin.text"));                               // NOI18N
        lblSwitchToBin.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    lblSwitchToBinMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        panButtons.add(lblSwitchToBin, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panFooter.add(panButtons, gridBagConstraints);

        panTitle.setOpaque(false);
        panTitle.setLayout(new java.awt.BorderLayout());

        panTitleString.setOpaque(false);
        panTitleString.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18));           // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(
            lblTitle,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.lblTitle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTitleString.add(lblTitle, gridBagConstraints);

        panTitle.add(panTitleString, java.awt.BorderLayout.CENTER);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        btnDownloadHighResImage.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/download.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            btnDownloadHighResImage,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnDownloadHighResImage.text"));                     // NOI18N
        btnDownloadHighResImage.setToolTipText(org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnDownloadHighResImage.toolTipText"));              // NOI18N
        btnDownloadHighResImage.setBorder(null);
        btnDownloadHighResImage.setBorderPainted(false);
        btnDownloadHighResImage.setContentAreaFilled(false);
        btnDownloadHighResImage.setEnabled(false);
        btnDownloadHighResImage.setFocusPainted(false);
        btnDownloadHighResImage.setMaximumSize(new java.awt.Dimension(30, 30));
        btnDownloadHighResImage.setMinimumSize(new java.awt.Dimension(30, 30));
        btnDownloadHighResImage.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnDownloadHighResImageActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(btnDownloadHighResImage, gridBagConstraints);

        btnReport.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/icons/printer.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            btnReport,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnReport.text"));              // NOI18N
        btnReport.setToolTipText(org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnReport.toolTipText"));       // NOI18N
        btnReport.setBorderPainted(false);
        btnReport.setContentAreaFilled(false);
        btnReport.setEnabled(false);
        btnReport.setFocusPainted(false);
        btnReport.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnReportActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(btnReport, gridBagConstraints);

        tbtnSlide.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/arrow.png")));     // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            tbtnSlide,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.tbtnSlide.text"));                                    // NOI18N
        tbtnSlide.setBorderPainted(false);
        tbtnSlide.setContentAreaFilled(false);
        tbtnSlide.setFocusPainted(false);
        tbtnSlide.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/arrow-180.png"))); // NOI18N
        tbtnSlide.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    tbtnSlideActionPerformed(evt);
                }
            });
        jPanel1.add(tbtnSlide, new java.awt.GridBagConstraints());

        panTitle.add(jPanel1, java.awt.BorderLayout.EAST);

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        roundedPanel1.setMinimumSize(new java.awt.Dimension(300, 200));
        roundedPanel1.setPreferredSize(new java.awt.Dimension(300, 200));
        roundedPanel1.setLayout(new java.awt.GridBagLayout());

        btnBin.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/bin_empty.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            btnBin,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnBin.text"));                                       // NOI18N
        btnBin.setToolTipText(org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnBin.toolTipText"));                                // NOI18N
        btnBin.setBorderPainted(false);
        btnBin.setContentAreaFilled(false);
        btnBin.setFocusPainted(false);
        btnBin.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnBinActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        roundedPanel1.add(btnBin, gridBagConstraints);

        btnRemoveWarenkorb.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/basket_shopping-minus.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            btnRemoveWarenkorb,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnRemoveWarenkorb.text"));                                       // NOI18N
        btnRemoveWarenkorb.setToolTipText(org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnRemoveWarenkorb.toolTipText"));                                // NOI18N
        btnRemoveWarenkorb.setBorderPainted(false);
        btnRemoveWarenkorb.setContentAreaFilled(false);
        btnRemoveWarenkorb.setFocusPainted(false);
        btnRemoveWarenkorb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemoveWarenkorbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        roundedPanel1.add(btnRemoveWarenkorb, gridBagConstraints);
        btnRemoveWarenkorb.setVisible(false);

        btnBinRecycle.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/bin_recycle.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            btnBinRecycle,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnBinRecycle.text"));                                  // NOI18N
        btnBinRecycle.setToolTipText(org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnBinRecycle.toolTipText"));                           // NOI18N
        btnBinRecycle.setBorderPainted(false);
        btnBinRecycle.setContentAreaFilled(false);
        btnBinRecycle.setFocusPainted(false);
        btnBinRecycle.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnBinRecycleActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        roundedPanel1.add(btnBinRecycle, gridBagConstraints);
        btnBinRecycle.setVisible(false);

        pnlLeuchtkasten.setOpaque(false);
        pnlLeuchtkasten.setLayout(new java.awt.CardLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setOpaque(false);

        grdStadtbildserien.setOpaque(false);
        jScrollPane1.setViewportView(grdStadtbildserien);
        grdStadtbildserien.getModel().addListDataListener(this);

        pnlLeuchtkasten.add(jScrollPane1, "SERIEN");
        jScrollPane1.getViewport().setOpaque(false);

        jScrollPane3.setBorder(null);
        jScrollPane3.setOpaque(false);

        grdWarenkorb.setOpaque(false);
        jScrollPane3.setViewportView(grdWarenkorb);

        pnlLeuchtkasten.add(jScrollPane3, "WARENKORB");
        jScrollPane3.getViewport().setOpaque(false);

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setOpaque(false);

        grdBin.setOpaque(false);
        jScrollPane2.setViewportView(grdBin);

        pnlLeuchtkasten.add(jScrollPane2, "BIN");
        jScrollPane2.getViewport().setOpaque(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(pnlLeuchtkasten, gridBagConstraints);

        pnlSlider.setOpaque(false);
        pnlSlider.setLayout(new java.awt.GridBagLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/image_small.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.jLabel1.text"));                                        // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        pnlSlider.add(jLabel1, gridBagConstraints);

        sldSize.setMaximum(512);
        sldSize.setMinimum(64);
        sldSize.setMinimumSize(new java.awt.Dimension(100, 16));
        sldSize.setOpaque(false);
        sldSize.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    sldSizeStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        pnlSlider.add(sldSize, gridBagConstraints);

        jLabel2.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/image_big.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel2,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.jLabel2.text"));                                      // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        pnlSlider.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        roundedPanel1.add(pnlSlider, gridBagConstraints);

        btnMoveSerienToWarenkorb.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objectrenderer/wunda_blau/basket_shopping.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            btnMoveSerienToWarenkorb,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnMoveSerienToWarenkorb.text"));                           // NOI18N
        btnMoveSerienToWarenkorb.setToolTipText(org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieAggregationRenderer.class,
                "Sb_stadtbildserieAggregationRenderer.btnMoveSerienToWarenkorb.toolTipText"));                    // NOI18N
        btnMoveSerienToWarenkorb.setBorderPainted(false);
        btnMoveSerienToWarenkorb.setContentAreaFilled(false);
        btnMoveSerienToWarenkorb.setFocusPainted(false);
        btnMoveSerienToWarenkorb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMoveSerienToWarenkorbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        roundedPanel1.add(btnMoveSerienToWarenkorb, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        roundedPanel1.add(filler1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(roundedPanel1, gridBagConstraints);

        pnlInfoPanels.setOpaque(false);
        pnlInfoPanels.setLayout(new java.awt.CardLayout());

        infoPanel.setMinimumSize(new java.awt.Dimension(350, 0));
        infoPanel.setPreferredSize(new java.awt.Dimension(350, 0));
        pnlInfoPanels.add(infoPanel, "INFO");

        infoNotAvailable.setMinimumSize(new java.awt.Dimension(350, 0));
        infoNotAvailable.setPreferredSize(new java.awt.Dimension(350, 0));
        pnlInfoPanels.add(infoNotAvailable, "NO_INFO");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(pnlInfoPanels, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnBinActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnBinActionPerformed
        moveSelectedStadtbildserienToOtherGrid(grdStadtbildserien, grdBin);
    }                                                                          //GEN-LAST:event_btnBinActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnBinRecycleActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnBinRecycleActionPerformed
        moveSelectedStadtbildserienToOtherGrid(grdBin, grdStadtbildserien);
    }                                                                                 //GEN-LAST:event_btnBinRecycleActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void sldSizeStateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_sldSizeStateChanged
        grdStadtbildserien.setFixedCellDimension(sldSize.getValue());
        grdStadtbildserien.ensureIndexIsVisible(grdStadtbildserien.getSelectedIndex());
        grdBin.setFixedCellDimension(sldSize.getValue());
        grdBin.ensureIndexIsVisible(grdBin.getSelectedIndex());
        grdWarenkorb.setFixedCellDimension(sldSize.getValue());
        grdWarenkorb.ensureIndexIsVisible(grdWarenkorb.getSelectedIndex());
    }                                                                           //GEN-LAST:event_sldSizeStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void tbtnSlideActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_tbtnSlideActionPerformed
        showInfoPanel(!pnlInfoPanels.isVisible());
        wasInfoPanelVisibleBeforeSwitch = pnlInfoPanels.isVisible();
    }                                                                             //GEN-LAST:event_tbtnSlideActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  show  DOCUMENT ME!
     */
    private void showInfoPanel(final boolean show) {
        if (show == pnlInfoPanels.isVisible()) {
            return;
        }

        // hack to properly resize the grids
        grdStadtbildserien.setVisible(false);
        grdBin.setVisible(false);
        grdWarenkorb.setVisible(false);
        pnlInfoPanels.setVisible(show);

        SwingUtilities.invokeLater(
            new Runnable() {

                @Override
                public void run() {
                    grdStadtbildserien.setVisible(true);
                    grdBin.setVisible(true);
                    grdWarenkorb.setVisible(true);

                    grdStadtbildserien.ensureIndexIsVisible(grdStadtbildserien.getSelectedIndex());
                    grdBin.ensureIndexIsVisible(grdBin.getSelectedIndex());
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnReportActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnReportActionPerformed
        if (this.getSelectedStadtbilderAmount() <= 0) {
            JOptionPane.showMessageDialog(
                this,
                "<html>Der Bericht kann nicht erstellt werden, wenn keine Stadtbilder ausgewählt wurden.</html>",
                "Keine Stadtbilder ausgewählt",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        final JasperReportDownload.JasperReportDataSourceGenerator dataSourceGenerator =
            new JasperReportDownload.JasperReportDataSourceGenerator() {

                @Override
                public JRDataSource generateDataSource() {
                    final ArrayList<StadtbildReportBean> stadtbilderReportBeans = new ArrayList<StadtbildReportBean>();

                    final Enumeration<Sb_stadtbildserieGridObject> e = ((DefaultListModel)grdStadtbildserien.getModel())
                                .elements();
                    while (e.hasMoreElements()) {
                        final Sb_stadtbildserieGridObject gridObject = (Sb_stadtbildserieGridObject)e.nextElement();

                        final CidsBean stadtbildserie = gridObject.getCidsBean();
                        final Set<CidsBean> stadtbilder = gridObject.getSelectedBildnummernOfSerie();
                        final Boolean internalUsage = (Boolean)stadtbildserie.getProperty("interner_gebrauch");
                        for (final CidsBean stadtbild : stadtbilder) {
                            final String bildnummer = (String)stadtbild.getProperty("bildnummer");
                            Image image;
                            if (Boolean.TRUE.equals(internalUsage)) {
                                image = Sb_stadtbildUtils.ERROR_IMAGE;
                            } else {
                                try {
                                    image = Sb_stadtbildUtils.downloadImageForBildnummer(bildnummer);
                                } catch (Exception ex) {
                                    LOG.error("Image could not be fetched.", ex);
                                    image = Sb_stadtbildUtils.ERROR_IMAGE;
                                }
                            }
                            stadtbilderReportBeans.add(new StadtbildReportBean(stadtbildserie, stadtbild, image));
                        }
                    }

                    final JRBeanCollectionDataSource beanArray = new JRBeanCollectionDataSource(stadtbilderReportBeans);
                    return beanArray;
                }
            };

        if (DownloadManagerDialog.showAskingForUserTitle(ComponentRegistry.getRegistry().getMainWindow())) {
            final String jobname = DownloadManagerDialog.getJobname();
            final String filename = "leuchtkasten";
            final String downloadTitle = "Leuchtkasten";

            final ArrayList<Download> downloads = new ArrayList<Download>(2);

            String resourceName = REPORT_STADTBILDSERIE_URL;
            final JasperReportDownload download = new JasperReportDownload(
                    resourceName,
                    dataSourceGenerator,
                    jobname,
                    downloadTitle,
                    filename);
            downloads.add(download);

            resourceName = REPORT_STADTBILDSERIE_EXCEL_URL;
            final JasperReportExcelDownload excelDownload = new JasperReportExcelDownload(
                    resourceName,
                    dataSourceGenerator,
                    jobname,
                    downloadTitle,
                    filename);
            downloads.add(excelDownload);

            final MultipleDownload multipleDownload = new MultipleDownload(downloads, filename);
            DownloadManager.instance().add(multipleDownload);
        }
    } //GEN-LAST:event_btnReportActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblSwitchToSerieMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblSwitchToSerieMouseClicked
        switchToSerie();
    }                                                                                //GEN-LAST:event_lblSwitchToSerieMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblSwitchToBinMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblSwitchToBinMouseClicked
        switchToBin();
    }                                                                              //GEN-LAST:event_lblSwitchToBinMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void lblMiddleMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_lblMiddleMouseClicked
        switchToWarenkorb();
    }                                                                         //GEN-LAST:event_lblMiddleMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemoveWarenkorbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemoveWarenkorbActionPerformed
        ((Sb_SingleStadtbildJGrid)grdWarenkorb).unchoseStadtbilderSelectedInTheGrid();
        grdWarenkorb.getSelectionModel().clearSelection();
    }                                                                                      //GEN-LAST:event_btnRemoveWarenkorbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnDownloadHighResImageActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnDownloadHighResImageActionPerformed
        if (DownloadManagerDialog.showAskingForUserTitle(
                        this)) {
            final String jobname = DownloadManagerDialog.getJobname();

            final ArrayList<Download> downloads = new ArrayList<Download>();

            // iterate over the Sb_stadtbildserieGridObject then over the selected Stadtbilder of each GridObject
            final Enumeration<Sb_stadtbildserieGridObject> e = ((DefaultListModel)grdStadtbildserien.getModel())
                        .elements();
            while (e.hasMoreElements()) {
                final Sb_stadtbildserieGridObject gridObject = (Sb_stadtbildserieGridObject)e.nextElement();

                final CidsBean stadtbildserie = gridObject.getCidsBean();
                final Boolean internal_usage = (Boolean)stadtbildserie.getProperty("interner_gebrauch");
                if (!Boolean.TRUE.equals(internal_usage)) {
                    for (final CidsBean stadtbild : gridObject.getSelectedBildnummernOfSerie()) {
                        final String imageNumber = (String)stadtbild.getProperty("bildnummer");
                        downloads.add(new TifferDownload(
                                jobname,
                                "Stadtbild "
                                        + imageNumber,
                                "stadtbild_"
                                        + imageNumber,
                                stadtbild.toString(),
                                "1"));
                    }
                }
            }
            if (downloads.size() == 1) {
                DownloadManager.instance().add(downloads.get(0));
            } else if (downloads.size() > 1) {
                DownloadManager.instance().add(new MultipleDownload(downloads, "Ausgewählte Stadtbilder"));
            }
        }
    } //GEN-LAST:event_btnDownloadHighResImageActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMoveSerienToWarenkorbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMoveSerienToWarenkorbActionPerformed
        // select every Stadtbild of the selected Stadtbildserien
        final List<Sb_stadtbildserieGridObject> selectedStadtbildserien = grdStadtbildserien.getSelectedValuesList();
        for (final Sb_stadtbildserieGridObject stadtbildserie : selectedStadtbildserien) {
            stadtbildserie.selectAllStadtbilder();
        }
    } //GEN-LAST:event_btnMoveSerienToWarenkorbActionPerformed

    /**
     * DOCUMENT ME!
     */
    private void switchToSerie() {
        final CardLayout cardLayout = (CardLayout)pnlLeuchtkasten.getLayout();
        cardLayout.show(pnlLeuchtkasten, "SERIEN");

        lblSwitchToSerie.setEnabled(false);
        lblSwitchToBin.setEnabled(true);
        lblMiddle.setEnabled(true);
        ((PictureSelectionJGrid)grdStadtbildserien).updateInfoPanel();

        btnBin.setVisible(true);
        btnBinRecycle.setVisible(false);
        btnRemoveWarenkorb.setVisible(false);

        tbtnSlide.setEnabled(true);
        showInfoPanel(wasInfoPanelVisibleBeforeSwitch);

        btnMoveSerienToWarenkorb.setVisible(true);
    }

    /**
     * DOCUMENT ME!
     */
    private void switchToBin() {
        final CardLayout cardLayout = (CardLayout)pnlLeuchtkasten.getLayout();
        cardLayout.show(pnlLeuchtkasten, "BIN");

        lblSwitchToSerie.setEnabled(true);
        lblSwitchToBin.setEnabled(false);
        lblMiddle.setEnabled(true);
        ((PictureSelectionJGrid)grdBin).updateInfoPanel();

        btnBin.setVisible(false);
        btnBinRecycle.setVisible(true);
        btnRemoveWarenkorb.setVisible(false);

        tbtnSlide.setEnabled(true);
        showInfoPanel(wasInfoPanelVisibleBeforeSwitch);

        btnMoveSerienToWarenkorb.setVisible(false);
    }

    /**
     * DOCUMENT ME!
     */
    private void switchToWarenkorb() {
        final CardLayout cardLayout = (CardLayout)pnlLeuchtkasten.getLayout();
        cardLayout.show(pnlLeuchtkasten, "WARENKORB");

        lblSwitchToSerie.setEnabled(true);
        lblSwitchToBin.setEnabled(true);
        lblMiddle.setEnabled(false);
        ((PictureSelectionJGrid)grdBin).updateInfoPanel();

        btnBin.setVisible(false);
        btnBinRecycle.setVisible(false);
        btnRemoveWarenkorb.setVisible(true);

        tbtnSlide.setEnabled(false);
        wasInfoPanelVisibleBeforeSwitch = pnlInfoPanels.isVisible();
        showInfoPanel(false);

        btnMoveSerienToWarenkorb.setVisible(false);
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g); // To change body of generated methods, choose Tools | Templates.
    }

    /**
     * DOCUMENT ME!
     *
     * @param  from  DOCUMENT ME!
     * @param  to    DOCUMENT ME!
     */
    private void moveSelectedStadtbildserienToOtherGrid(final JGrid from, final JGrid to) {
        final List<Sb_stadtbildserieGridObject> gridObjectsToRemove = from.getSelectedValuesList();

        final boolean movedToBin = to == grdBin;

        for (final Sb_stadtbildserieGridObject gridObject : gridObjectsToRemove) {
            ((DefaultListModel)from.getModel()).removeElement(gridObject);
            ((DefaultListModel)to.getModel()).addElement(gridObject);
            gridObject.setModel((DefaultListModel)to.getModel());
            gridObject.setIsInBin(movedToBin);
        }
        from.getSelectionModel().clearSelection();
        updateFooterLabels();
    }

    @Override
    public Collection<CidsBean> getCidsBeans() {
        return cidsBeans;
    }

    @Override
    public void setCidsBeans(final Collection<CidsBean> beans) {
        this.cidsBeans = beans;
        if (beans != null) {
            infoPanel.setAggregationRenderer(this);
            final DefaultListModel model = (DefaultListModel)grdStadtbildserien.getModel();
            for (final CidsBean bean : beans) {
                final Sb_stadtbildserieGridObject gridObject = new Sb_stadtbildserieGridObject(model);
                gridObject.setCidsBean(bean);
                gridObject.addStadtbildChosenListener((Sb_stadtbildserieGridObjectListener)grdWarenkorb);
                gridObject.addStadtbildChosenListener(infoPanel);
                gridObject.addStadtbildChosenListener(this);
                model.addElement(gridObject);

                Sb_stadtbildUtils.cacheImagesForStadtbilder(bean.getBeanCollectionProperty("stadtbilder_arr"));
            }
            updateFooterLabels();
            setTitle("");
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public String getTitle() {
        return "Leuchtkasten";
    }

    @Override
    public void setTitle(String title) {
        title = "Leuchtkasten";
        if ((cidsBeans != null) && !cidsBeans.isEmpty()) {
            final int amountSerien = cidsBeans.size();
            int amountBilder = 0;
            for (final CidsBean stadtbildserie : cidsBeans) {
                amountBilder += stadtbildserie.getBeanCollectionProperty("stadtbilder_arr").size();
            }
            title += ": " + amountBilder + " Stadtbilder in " + amountSerien + " Stadtbildserien gefunden";
        }
        lblTitle.setText(title);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            final CidsBean[] beans = DevelopmentTools.createCidsBeansFromRMIConnectionOnLocalhost(
                    "WUNDA_BLAU",
                    "Administratoren",
                    "admin",
                    "kif",
                    "sb_stadtbildserie",
                    " id = 5 or id = 6 or id = 285195 or id = 8 or id = 9 or id = 10 or id = 11 or  id = 285198 or id = 151489 ",
                    10);

            DevelopmentTools.createAggregationRendererInFrameFromRMIConnectionOnLocalhost(Arrays.asList(beans),
                "Leuchtkasten",
                1024,
                800);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public JComponent getTitleComponent() {
        return panTitle;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * DOCUMENT ME!
     */
    private void updateFooterLabels() {
        final String stadtbildserien = "Vorauswahl (" + grdStadtbildserien.getModel().getSize() + " Serien)";
        lblSwitchToSerie.setText(stadtbildserien);

        final String warenkorbText = "Warenkorb (" + getSelectedStadtbilderAmount() + " Bilder)";
        lblMiddle.setText(warenkorbText);

        final int amountSerienInBin = grdBin.getModel().getSize();
        final String bin = "Papierkorb (" + amountSerienInBin + " Serien)";
        if (amountSerienInBin == 0) {
            btnBin.setIcon(BIN_EMPTY);
        } else {
            btnBin.setIcon(BIN_FULL);
        }
        lblSwitchToBin.setText(bin);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int getSelectedStadtbilderAmount() {
        return getSelectedStadtbilder().size();
    }

    /**
     * Gets the selected Stadtbilder from the Stadtbildserien which are shown in the Vorschau-grid (grdStadtbildserien).
     *
     * @return  DOCUMENT ME!
     */
    private Collection<CidsBean> getSelectedStadtbilder() {
        final Set<CidsBean> selectedStadtbilder = new HashSet<CidsBean>();

        final Enumeration<Sb_stadtbildserieGridObject> e = ((DefaultListModel)grdStadtbildserien.getModel()).elements();
        while (e.hasMoreElements()) {
            final Sb_stadtbildserieGridObject gridObject = (Sb_stadtbildserieGridObject)e.nextElement();

            final Set set = gridObject.getSelectedBildnummernOfSerie();
            selectedStadtbilder.addAll(set);
        }
        return selectedStadtbilder;
    }

    @Override
    public void intervalAdded(final ListDataEvent e) {
// do nothing
    }

    @Override
    public void intervalRemoved(final ListDataEvent e) {
        // do nothing
    }

    @Override
    public void contentsChanged(final ListDataEvent e) {
        updateFooterLabels();
    }

    @Override
    public void stadtbildChosen(final Sb_stadtbildserieGridObject source, final CidsBean stadtbild) {
        setEnableHighResDownload();
        btnReport.setEnabled(this.getSelectedStadtbilderAmount() > 0);
    }

    @Override
    public void stadtbildUnchosen(final Sb_stadtbildserieGridObject source, final CidsBean stadtbild) {
        setEnableHighResDownload();
        btnReport.setEnabled(this.getSelectedStadtbilderAmount() > 0);
    }

    @Override
    public void sb_stadtbildserieGridObjectMoveToBin(final Sb_stadtbildserieGridObject source) {
        setEnableHighResDownload();
        btnReport.setEnabled(this.getSelectedStadtbilderAmount() > 0);
    }

    @Override
    public void sb_stadtbildserieGridObjectRemovedFromBin(final Sb_stadtbildserieGridObject source) {
        setEnableHighResDownload();
        btnReport.setEnabled(this.getSelectedStadtbilderAmount() > 0);
    }

    /**
     * Only enable the HighResDownload button if at least one image is accessible. This means that a high-res picture
     * must exist and the image must not be intended for the internal usage.
     */
    private void setEnableHighResDownload() {
        // create an array with Sb_stadtbildserieGridObject of the current vorschau.
        final Sb_stadtbildserieGridObject[] gridObjectArr =
            new Sb_stadtbildserieGridObject[grdStadtbildserien.getModel().getSize()];
        ((DefaultListModel)grdStadtbildserien.getModel()).copyInto(gridObjectArr);

        btnDownloadHighResImage.setEnabled(false);

        if (enableHighResDownloadWorker != null) {
            enableHighResDownloadWorker.cancel(true);
        }

        enableHighResDownloadWorker = new SwingWorker<Boolean, Void>() {

                @Override
                protected Boolean doInBackground() throws Exception {
                    // iterate over the Sb_stadtbildserieGridObject then over the selected Stadtbilder of each
                    // GridObject
                    for (final Sb_stadtbildserieGridObject gridObject : gridObjectArr) {
                        final CidsBean stadtbildserie = gridObject.getCidsBean();
                        final Boolean internal_usage = (Boolean)stadtbildserie.getProperty("interner_gebrauch");
                        if (!Boolean.TRUE.equals(internal_usage)) {
                            for (final CidsBean stadtbild : gridObject.getSelectedBildnummernOfSerie()) {
                                final String imageNumber = (String)stadtbild.getProperty("bildnummer");
                                if (Sb_stadtbildUtils.getFormatOfHighResPicture(imageNumber) != null) {
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                }

                @Override
                protected void done() {
                    boolean enableHighResDownload = false;
                    try {
                        enableHighResDownload = this.get();
                    } catch (InterruptedException ex) {
                        LOG.warn(ex, ex);
                    } catch (ExecutionException ex) {
                        LOG.warn(ex, ex);
                    } catch (CancellationException ex) {
                        // do nothing - was probably canceled such that another work can run
                        return;
                    }
                    btnDownloadHighResImage.setEnabled(enableHighResDownload);
                }
            };
        enableHighResDownloadWorker.execute();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class PictureSelectionJGrid extends JGrid {

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PictureSelectionJGrid object.
         */
        public PictureSelectionJGrid() {
            init();
        }

        /**
         * Creates a new PictureSelectionJGrid object.
         *
         * @param   model  DOCUMENT ME!
         *
         * @throws  IllegalArgumentException  DOCUMENT ME!
         */
        public PictureSelectionJGrid(final ListModel model) throws IllegalArgumentException {
            super(model);
            init();
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         */
        private void init() {
            final DefaultListModel<Sb_stadtbildserieGridObject> gridModel =
                new DefaultListModel<Sb_stadtbildserieGridObject>();
            this.setModel(gridModel);
            this.getCellRendererManager()
                    .setDefaultRenderer(
                        new de.cismet.cids.custom.objectrenderer.wunda_blau.Sb_stadtbildserieGridRenderer());

            this.addMouseListener(new MouseAdapter() {

                    @Override
                    /**
                     * Select or unselect the Stadtbild under the marker
                     */
                    public void mouseClicked(final MouseEvent e) {
                        if (e.getClickCount() >= 2) {
                            final List<Sb_stadtbildserieGridObject> selectedSerien = PictureSelectionJGrid.this
                                        .getSelectedValuesList();
                            if (selectedSerien.size() == 1) {
                                final Sb_stadtbildserieGridObject gridObject = selectedSerien.get(0);
                                gridObject.selectOrDeselectStadtbild(gridObject.getStadtbildUnderMarker());
                            }
                        }
                    }
                });

            this.addMouseMotionListener(new MouseAdapter() {

                    int lastIndex = -1;

                    @Override
                    /**
                     * draw the marker
                     */
                    public void mouseMoved(final MouseEvent e) {
                        if ((lastIndex >= 0) && (lastIndex < PictureSelectionJGrid.this.getModel().getSize())) {
                            final Object o = PictureSelectionJGrid.this.getModel().getElementAt(lastIndex);
                            if (o instanceof Sb_stadtbildserieGridObject) {
                                final Rectangle r = PictureSelectionJGrid.this.getCellBounds(lastIndex);
                                if ((r != null) && !r.contains(e.getPoint())) {
                                    // remove the marker once
                                    if (((Sb_stadtbildserieGridObject)o).isMarker()) {
                                        ((Sb_stadtbildserieGridObject)o).setMarker(false);
                                        PictureSelectionJGrid.this.repaint(r);
                                    }
                                }
                            }
                        }

                        final int index = PictureSelectionJGrid.this.getCellAt(e.getPoint());
                        if (index >= 0) {
                            final Object o = PictureSelectionJGrid.this.getModel().getElementAt(index);
                            if (o instanceof Sb_stadtbildserieGridObject) {
                                if (((Sb_stadtbildserieGridObject)o).getAmountImages() > 1) {
                                    final Rectangle r = PictureSelectionJGrid.this.getCellBounds(index);
                                    if (r != null) {
                                        ((Sb_stadtbildserieGridObject)o).setFraction(
                                            ((float)e.getPoint().x - (float)r.x)
                                                    / (float)r.width);
                                        ((Sb_stadtbildserieGridObject)o).setMarker(true);
                                        lastIndex = index;
                                        PictureSelectionJGrid.this.repaint(r);
                                    }
                                }
                            }
                        }
                    }
                });
            this.addListSelectionListener(new ListSelectionListener() {

                    @Override
                    public void valueChanged(final ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            updateInfoPanel();
                        }
                    }
                });
        }

        /**
         * DOCUMENT ME!
         */
        public void updateInfoPanel() {
            int[] indexes = new int[0];
            final ListSelectionModel sm = PictureSelectionJGrid.this.getSelectionModel();
            final int iMin = sm.getMinSelectionIndex();
            final int iMax = sm.getMaxSelectionIndex();

            if ((iMin >= 0) && (iMin == iMax)) {
                indexes = new int[1];
                indexes[0] = iMin;
            }
            final CardLayout cardLayout = (CardLayout)pnlInfoPanels.getLayout();
            if (indexes.length == 1) {
                cardLayout.show(pnlInfoPanels, "INFO");
                final Sb_stadtbildserieGridObject gridObject = (Sb_stadtbildserieGridObject)PictureSelectionJGrid.this
                            .getModel().getElementAt(indexes[0]);
                infoPanel.setGridObject(gridObject);
            } else {
                cardLayout.show(pnlInfoPanels, "NO_INFO");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class StadtbildReportBean {

        //~ Instance fields ----------------------------------------------------

        CidsBean stadtbildserie;
        CidsBean stadtbild;
        Image image;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new StadtbildReportBean object.
         */
        public StadtbildReportBean() {
        }

        /**
         * Creates a new StadtbildReportBean object.
         *
         * @param  stadtbildserie  DOCUMENT ME!
         * @param  stadtbild       DOCUMENT ME!
         * @param  image           DOCUMENT ME!
         */
        public StadtbildReportBean(final CidsBean stadtbildserie, final CidsBean stadtbild, final Image image) {
            this.stadtbildserie = stadtbildserie;
            this.stadtbild = stadtbild;
            this.image = image;
        }

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public CidsBean getStadtbildserie() {
            return stadtbildserie;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  stadtbildserie  DOCUMENT ME!
         */
        public void setStadtbildserie(final CidsBean stadtbildserie) {
            this.stadtbildserie = stadtbildserie;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public CidsBean getStadtbild() {
            return stadtbild;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  stadtbild  DOCUMENT ME!
         */
        public void setStadtbild(final CidsBean stadtbild) {
            this.stadtbild = stadtbild;
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public Image getImage() {
            return image;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  image  DOCUMENT ME!
         */
        public void setImage(final Image image) {
            this.image = image;
        }
    }
}