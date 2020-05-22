public class mainclass {

    public static void main(String[] args){
        test();
    }

    public static void test(String... menu){
        if (menu.length > 0) {
            System.out.println(menu[0]);
        } else {
            System.out.println("null");
        }
    }
}
