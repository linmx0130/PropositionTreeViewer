import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/5/2.
 */
public class Main {
    public static void parseProposition(String str){
        Element p = null;
        try {
            p = ParsingKt.parse(str);
        } catch(Exception e){
            JOptionPane.showConfirmDialog(null, "语句 \""+str+"\"有语法错误！","解析错误",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            return ;
        }
        Viewer viewer = new Viewer(p);
        viewer.setModal(true);
        viewer.setLocationRelativeTo(null);
        viewer.setVisible(true);
    }
    public static void main(String args[]){
        if (args.length ==1){
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(args[0]) );
                String buf=null;
                do{
                    buf= reader.readLine();
                    if (buf ==null) System.exit(0);
                    parseProposition(buf);
                }while (buf!=null);
            } catch (IOException e) {
                JOptionPane.showConfirmDialog(null, "无法打开文件"+args[0]+"！","文件错误",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
        }else {
            Scanner cin = new Scanner(System.in);
            System.out.println("Input a proposition:");
            String str = cin.nextLine().trim();
            parseProposition(str);
            System.exit(0);
        }
    }
}
