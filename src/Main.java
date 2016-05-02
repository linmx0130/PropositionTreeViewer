import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/5/2.
 */
public class Main {
    public static void main(String args[]){
        Scanner cin = new Scanner(System.in);
        System.out.println("Input a proposition:");
        String str = cin.nextLine().trim();
        Element p = null;
        try {
            p = ParsingKt.parse(str);
        } catch(Exception e){
            System.out.println("Syntax Error!");
            return ;
        }
        Viewer viewer = new Viewer(p);
        viewer.setVisible(true);

    }
}
