/*
 * TimeZoninator.java
 *
 * Created on August 16, 2007, 3:12 PM
 */

package timezoninator;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;
import javax.swing.DefaultListModel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.SpinnerDateModel;

import net.stoken.utils.AppInfo;

/**
 *
 * @author  k.langer
 */
public class TimeZoninator extends javax.swing.JFrame {
    
    /** Creates new form TimeZoninator */
    public TimeZoninator() {
        InputStream in = null;
        Properties props = new Properties();
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            
        } catch(Exception ex) {
            ex.printStackTrace();
        } // end-try-catch
        
        initComponents();
        
        try {
            in = getClass().getResourceAsStream("/appinfo.properties");
            props.load(in);
            in.close();
            AppInfo.init(props);
            
            this.setTitle(AppInfo.getPROGNAME() + " " + AppInfo.getFULLVERSION());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } // end-try-catch
             
        this.jSpinner1.setEditor(new JSpinner.DateEditor(this.jSpinner1, "yyyy-MM-dd HH:mm:ss Z z"));
        
        return;
    }
    
    private void writeTimezones(long c) {
        String formatstring = "%-44s|%-22s|%-6s|%5s %5s|%5s %5s";
        String formatdate   = "yyyy-MM-dd HH:mm:ss aa Z z";
        Calendar local = null;
        Calendar gmt = null;
        Calendar foreign = null;
        Calendar temp = null;
//        DateFormat dflocal   = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL);
//        DateFormat dfgmt     = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL);
//        DateFormat dfforeign = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL);
        DateFormat dflocal   = new SimpleDateFormat(formatdate);
        DateFormat dfgmt     = new SimpleDateFormat(formatdate);
        DateFormat dfforeign = new SimpleDateFormat(formatdate);
        
        if(this.jCheckBoxClearFirst.isSelected()) {
            this.clearAll();
        } // end-if
        
        this.addListItem(
            String.format(formatstring,
            "ZONE Date",
            "ID",
            "DST",
            "LFSET",
            "LZONE",
            "FFSET",
            "FZONE"
            ));
        
        local = new GregorianCalendar();
        local.setTimeInMillis(c);
        dflocal.setTimeZone(local.getTimeZone());
        
        gmt = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        gmt.setTimeInMillis(local.getTimeInMillis());
        dfgmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        this.jTextFieldLocalTime.setText(dflocal.format(local.getTime()));
        this.jTextFieldGMT.setText(dfgmt.format(gmt.getTime()));
        
        for(String zone : TimeZone.getAvailableIDs()) {
            if(zone.contains(this.jTextFieldZone.getText().trim())) {
                foreign = new GregorianCalendar(TimeZone.getTimeZone(zone));
                foreign.setTimeInMillis(local.getTimeInMillis());
                dfforeign.setTimeZone(TimeZone.getTimeZone(zone));
                
                this.addListItem(
                    String.format(formatstring,
                        dfforeign.format(foreign.getTime()),
                        foreign.getTimeZone().getID(),
                        foreign.getTimeZone().inDaylightTime(foreign.getTime()),
                        (local.get(Calendar.DST_OFFSET)/(60*60))/1000,
                        (local.get(Calendar.ZONE_OFFSET)/(60*60))/1000,
                        (foreign.get(Calendar.DST_OFFSET)/(60*60))/1000,
                        (foreign.get(Calendar.ZONE_OFFSET)/(60*60))/1000
                    ));
            } // end-if
        } // end-for
        
        return;
    }
    
    private void addListItem(String s) {
        DefaultListModel dlm = null;
        if(s.length() > 0) {
            dlm = (DefaultListModel) this.jList1.getModel();
            dlm.addElement(s);
        } // end-if
        
        return;
    }
    
    private void clearAll() {
        DefaultListModel dlm = (DefaultListModel) this.jList1.getModel();
        dlm.removeAllElements();
        return;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jSpinner1 = new javax.swing.JSpinner();
        jTextFieldZone = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JButton();
        jButtonDoIt = new javax.swing.JButton();
        jCheckBoxClearFirst = new javax.swing.JCheckBox();
        jTextFieldLocalTime = new javax.swing.JTextField();
        jTextFieldGMT = new javax.swing.JTextField();
        jLabelLocalTime = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jList1.setFont(new java.awt.Font("Courier New", 0, 11));
        jList1.setModel(new DefaultListModel());
        jList1.setCellRenderer(new StripeRenderer());
        jScrollPane1.setViewportView(jList1);

        jSpinner1.setModel(new SpinnerDateModel());

        jLabel1.setText("Zone");

        jLabel2.setText("Local Time");

        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonDoIt.setText("Do It");
        jButtonDoIt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDoItActionPerformed(evt);
            }
        });

        jCheckBoxClearFirst.setSelected(true);
        jCheckBoxClearFirst.setText("Clear First");
        jCheckBoxClearFirst.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxClearFirst.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jTextFieldLocalTime.setEditable(false);

        jTextFieldGMT.setEditable(false);

        jLabelLocalTime.setText("Local Time");

        jLabelGMT.setText("GMT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldZone, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonDoIt)
                    .addComponent(jButtonClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelGMT)
                    .addComponent(jLabelLocalTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldLocalTime)
                    .addComponent(jTextFieldGMT, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(jCheckBoxClearFirst))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButtonDoIt)
                    .addComponent(jCheckBoxClearFirst)
                    .addComponent(jLabelGMT)
                    .addComponent(jTextFieldGMT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClear)
                    .addComponent(jLabel1)
                    .addComponent(jLabelLocalTime)
                    .addComponent(jTextFieldLocalTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButtonDoItActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDoItActionPerformed
        SpinnerDateModel sdm = (SpinnerDateModel) this.jSpinner1.getModel();
        Calendar cal = GregorianCalendar.getInstance();
        
        cal.setTime(sdm.getDate());
        this.writeTimezones(cal.getTimeInMillis());
        return;
    }//GEN-LAST:event_jButtonDoItActionPerformed
    
    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        this.clearAll();
        return;
    }//GEN-LAST:event_jButtonClearActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TimeZoninator().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDoIt;
    private javax.swing.JCheckBox jCheckBoxClearFirst;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelLocalTime;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField jTextFieldGMT;
    private javax.swing.JTextField jTextFieldLocalTime;
    private javax.swing.JTextField jTextFieldZone;
    // End of variables declaration//GEN-END:variables
} // end-class
