/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author advai
 */
public class PromotionActionListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);; //To change body of generated methods, choose Tools | Templates.
        e.getActionCommand();
    }
    
}
