public class Main {
    public static void main(String []args) {
        Demo demo = new Demo();
        try {
            demo.init();
            demo.search();
            demo.showDetail(2);
            demo.addCart();
            demo.viewCart();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            demo.Quit();
        }
    }
}