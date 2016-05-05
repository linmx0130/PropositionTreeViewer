import be.ugent.caagt.jmathtex.TeXFormula;
import be.ugent.caagt.jmathtex.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2016/5/2.
 */
class ShowPanel extends JPanel{
    private Element proposition;
    private static final int ROW_DISTANCE = 25;
    public ShowPanel(Element p) {
        proposition = p;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public Dimension getPreferredSize() {
        TeXFormula f = new TeXFormula(proposition.tex());
        TeXIcon icon = f.createTeXIcon(0, 25.0F);
        return new Dimension(icon.getIconWidth()+10, (icon.getIconHeight() +ROW_DISTANCE) * proposition.getDepth()+10);
    }

    private int drawProposition(Graphics g, Element proposition, int x, int y, boolean isLeft , int parentWidth){
        TeXFormula f = new TeXFormula(proposition.tex());
        TeXIcon icon = f.createTeXIcon(0, 25.0F);
        if (isLeft){
            icon.paintIcon(this,g,x,y);
            int centreWidth = x+icon.getIconWidth()/2;
            if (parentWidth != -1) g.drawLine(centreWidth,y-1, x+parentWidth/2, y-ROW_DISTANCE);
        }else {
            icon.paintIcon(this, g, x - icon.getIconWidth(), y);
            int centreWidth = x - icon.getIconWidth()/2;
            g.drawLine(centreWidth,y-1, x-parentWidth/2, y-ROW_DISTANCE);
            x = x-icon.getIconWidth();
        }
        if (proposition.getDepth()!=1){
            Proposition p = (Proposition) proposition;
            int thisWidth = icon.getIconWidth();
            if (p.getLeftChild()!=null){
                drawProposition(g, p.getLeftChild(), x,y+icon.getIconHeight() + ROW_DISTANCE,true, thisWidth);
                drawProposition(g, p.getRightChild(), x + thisWidth,y+icon.getIconHeight() + ROW_DISTANCE,false,thisWidth);
            }else{
                drawProposition(g, p.getRightChild(), x,y+icon.getIconHeight() + ROW_DISTANCE,true, thisWidth);
            }
        }
        return icon.getIconWidth();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawProposition(g, proposition,5,5,true,-1);
    }
}
public class Viewer extends JDialog {
    public Viewer(Element proposition){
        JPanel panel = new ShowPanel(proposition);
        setContentPane(panel);
        setTitle("Proposition Tree Viewer");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
