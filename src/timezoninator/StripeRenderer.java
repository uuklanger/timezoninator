/*
 * StripeRenderer.java
 *
 * Created on August 16, 2007, 3:49 PM
 */

package timezoninator;

import javax.swing.*;
import java.awt.*;

public class StripeRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList list, 
        Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
            list,
            value,
            index,
            isSelected,
            cellHasFocus
            );
        
        if(index%2 == 0) {
            if(! list.isSelectedIndex(index)) {
                label.setBackground(new Color(230,255,230));
            } else {
                label.setBackground(new Color(255,255,200));                
            } // end-if
        } // end-if
        
        return label;
    }
} // end-class
