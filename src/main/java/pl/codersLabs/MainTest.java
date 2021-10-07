package pl.codersLabs;

import org.apache.commons.lang3.StringUtils;

public class MainTest {
    
    public static void main(String[] args) {
        String s = StringUtils.deleteWhitespace(" dsdv ds   d");
        System.out.println(s);

        System.out.println(ConsoleColors.RED + " red ");

        System.out.println(ConsoleColors.BLUE + " blue ");
    }
}
