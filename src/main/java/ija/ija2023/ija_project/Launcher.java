/**
 * Launcher class that will "launch" main class, this is only for .jar file
 * created upon using `maven package`, by using shade plugin a maven will
 * create a single .jar file and main class of this is a Launcher.java instead
 * of Main.java.
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project;

public class Launcher {
    public static void main(String[] args)
    {
        Main.main(args);
    }
}
