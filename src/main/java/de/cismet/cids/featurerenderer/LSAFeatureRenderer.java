/*
 * LSAFeatureRenderer.java
 *
 * Created on 1. Juni 2007, 10:15
 */

package de.cismet.cids.featurerenderer;

import de.cismet.cids.annotations.CidsAttribute;
import de.cismet.tools.BrowserLauncher;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Paint;
import java.net.URL;
import java.util.Properties;
import javax.swing.ImageIcon;

/**
 *
 * @author  hell
 */
public class LSAFeatureRenderer extends CustomCidsFeatureRenderer{
    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    
    @CidsAttribute("BILDNUMMER")
    public String nummer;
   
    ImageIcon errorimage=new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/tools/metaobjectrenderer/examples/error.png"));
   
    /**
     * Creates new form LSAFeatureRenderer
     */
    Properties properties = new Properties();
    public LSAFeatureRenderer() {
        log.debug("LSAFEatureRenderer init()");
        initComponents();
        setOpaque(false);
        setPreferredSize(new Dimension(150,150));
        try {
            properties.load(getClass().getResourceAsStream("/renderer.properties"));
        } catch(Exception e){
            log.warn("Fehler beim Laden der Properties",e);
        }
        
    }
    
    
    
    public void assign() {
        log.debug("ASSIGN");
        log.debug("Nummer="+nummer);
        //prbLoad.setVisible(true);
        Thread t=new Thread(new Runnable() {
            public void run() {
                try {
                    String url=properties.getProperty("luftbildschraegaufnahmenservicesmall");
                    ImageIcon i;
                    if (url==null) {
                        i=new ImageIcon(new URL("http://s10220:8098/luft/tiffer?bnr="+nummer+"&scale=0.15&format=JPG"));
                    } else{
                        String newUrl=url.replaceAll("<cismet::nummer>",nummer);
                        i=new ImageIcon(new URL(newUrl));
                    }
                    
                    final ImageIcon icon=i;
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            lblImagePreview.setIcon(icon);
                            LSAFeatureRenderer.this.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
                            LSAFeatureRenderer.this.setSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
                            revalidate();
                            //prbLoad.setVisible(false);
                        }
                    });
                } catch(Exception e) {
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            lblImagePreview.setIcon(errorimage);
                            //prbLoad.setVisible(false);
                        }
                    });
                }
                
            }
        });
        t.start();
//        //((TitledBorder)getBorder()).setTitle(title);
    }

    
    

    public float getTransparency() {
        return 0.9f;
    }

    public Paint getFillingStyle() {
        return new Color(100,100,100,50);
    }
    
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        lblImagePreview = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setLayout(new java.awt.BorderLayout());

        setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setPreferredSize(new java.awt.Dimension(100, 100));
        lblImagePreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagePreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/tools/metaobjectrenderer/examples/load.png")));
        lblImagePreview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImagePreviewMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblImagePreviewMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblImagePreviewMouseExited(evt);
            }
        });

        add(lblImagePreview, java.awt.BorderLayout.CENTER);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("H\u00f6here Aufl\u00f6sung durch Mausklick.");
        add(jLabel12, java.awt.BorderLayout.PAGE_END);

    }// </editor-fold>//GEN-END:initComponents

    private void lblImagePreviewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImagePreviewMouseExited
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_lblImagePreviewMouseExited

    private void lblImagePreviewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImagePreviewMouseEntered
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblImagePreviewMouseEntered

    private void lblImagePreviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImagePreviewMouseClicked
        try {
            String url=properties.getProperty("luftbildschraegaufnahmenservicefull");
            String newUrl=null;
            if (url==null) {
                newUrl="http://s10220:8098/luft/tiffer?bnr="+nummer+"&scale=1&format=JPG";
            } else{
                newUrl=url.replaceAll("<cismet::nummer>",nummer);
                
            }
            BrowserLauncher.openURL(newUrl);
        } catch (Exception e) {
            
        }
    }//GEN-LAST:event_lblImagePreviewMouseClicked
                    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblImagePreview;
    // End of variables declaration//GEN-END:variables
    
}