import Menus.StartMenu;
import input_output.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Input.loadALL();
        StartMenu.mainMenu();
    }
}