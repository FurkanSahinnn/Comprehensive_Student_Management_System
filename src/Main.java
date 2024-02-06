public class Main {
    public static void main(String[] args) {
        Actions action = new Actions();

        String[][] allStudent = action.fetchData();

        new GuiPanel(action, allStudent);
    }
}




