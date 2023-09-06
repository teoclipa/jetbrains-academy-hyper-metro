package metro;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error! Missing file path argument.");
            return;
        }
        String fileRoot = "C:\\Users\\teocl\\OneDrive\\Desktop\\intellij projects\\HyperMetro\\HyperMetro\\task\\";
        String filePath = fileRoot + args[0].replace("./", "").replace("/", "\\");
        MetroLogic metroLogic = new MetroLogic(filePath);
        metroLogic.run();
    }
}
