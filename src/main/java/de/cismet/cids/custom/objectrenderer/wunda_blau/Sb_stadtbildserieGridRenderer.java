/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wunda_blau;

import com.guigarage.jgrid.JGrid;
import com.guigarage.jgrid.renderer.GridCellRenderer;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import de.cismet.cids.custom.utils.Sb_stadtbildUtils;

/**
 * DOCUMENT ME!
 *
 * @author   Gilles Baatz
 * @version  $Revision$, $Date$
 */
public class Sb_stadtbildserieGridRenderer extends javax.swing.JPanel implements GridCellRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            Sb_stadtbildserieGridRenderer.class);

    //~ Instance fields --------------------------------------------------------

    private Image image;

    private boolean paintMarker = false;

    private float markerFraction;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblIcon;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form Sb_stadtbildserieGridPanel.
     */
    public Sb_stadtbildserieGridRenderer() {
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

        jLayeredPane1 = new javax.swing.JLayeredPane();
        lblIcon = new javax.swing.JLabel();
        lblAmount = new JLabel() {

                @Override
                protected void paintComponent(final Graphics g) {
                    final Graphics2D g2d = (Graphics2D)g.create();
                    g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setComposite(AlphaComposite.getInstance(
                            AlphaComposite.SRC_OVER,
                            0.75f));
                    g2d.setColor(getBackground());
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    super.paintComponent(g);
                }
            };
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));

        setPreferredSize(new java.awt.Dimension(64, 64));
        setLayout(new java.awt.GridBagLayout());

        jLayeredPane1.setLayout(new java.awt.GridBagLayout());

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(
            lblIcon,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieGridRenderer.class,
                "Sb_stadtbildserieGridRenderer.lblIcon.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jLayeredPane1.add(lblIcon, gridBagConstraints);
        jLayeredPane1.setLayer(lblIcon, 0);

        lblAmount.setBackground(new java.awt.Color(190, 187, 182));
        lblAmount.setForeground(new java.awt.Color(0, 0, 0));
        lblAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(
            lblAmount,
            org.openide.util.NbBundle.getMessage(
                Sb_stadtbildserieGridRenderer.class,
                "Sb_stadtbildserieGridRenderer.lblAmount.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jLayeredPane1.add(lblAmount, gridBagConstraints);
        jLayeredPane1.setLayer(lblAmount, 1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        jLayeredPane1.add(filler1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jLayeredPane1, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public Component getGridCellRendererComponent(final JGrid grid,
            final Object value,
            final int index,
            final boolean isSelected,
            final boolean cellHasFocus) {
        image = null;
        paintMarker = false;
        if (value instanceof Sb_stadtbildserieGridObject) {
            image = ((Sb_stadtbildserieGridObject)value).getImage(grid.getFixedCellDimension(), false);
            markerFraction = ((Sb_stadtbildserieGridObject)value).getFraction();
            paintMarker = ((Sb_stadtbildserieGridObject)value).isMarker();
            final int amountImages = ((Sb_stadtbildserieGridObject)value).getAmountImages();
            final int amountSelectedImages = ((Sb_stadtbildserieGridObject)value).getAmountSelectedImages();
            lblAmount.setText(amountSelectedImages + " von " + amountImages);
        }

        if (image != null) {
            lblIcon.setIcon(new ImageIcon(image));
        } else {
            final Image scaledErrorImage = Sb_stadtbildUtils.scaleImage(
                    Sb_stadtbildUtils.ERROR_IMAGE,
                    grid.getFixedCellDimension(),
                    false);
            lblIcon.setIcon(new ImageIcon(scaledErrorImage));
        }

        return this;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER));

        if (image != null) {
            if (paintMarker) {
                final int x = (int)(getWidth() * markerFraction);
                g2.setStroke(new BasicStroke(3.5f));
                g2.setColor(new Color(50, 50, 50));
                g2.drawLine(x, 0, x, getHeight());

                g2.setStroke(new BasicStroke(2.5f));
                g2.setColor(new Color(248, 211, 80));
                g2.drawLine(x, 0, x, getHeight());
            }
            g2.dispose();
        }
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        final Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER));
        if (image != null) {
            if (paintMarker) {
                final int x = (int)(getWidth() * markerFraction);
                g2.setStroke(new BasicStroke(3.5f));
                g2.setColor(new Color(50, 50, 50));
                g2.drawLine(x, 0, x, getHeight());

                g2.setStroke(new BasicStroke(2.5f));
                g2.setColor(new Color(248, 211, 80));
                g2.drawLine(x, 0, x, getHeight());
            }
            g2.dispose();
        }
    }
}