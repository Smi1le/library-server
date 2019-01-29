import com.blade.Blade;
import com.blade.mvc.ui.RestResponse;

public class Main {
    public static void main(String[] args) {
        Blade.of()
                .get("/", ctx -> {
                    ctx.text("Hello world");
//                    System.out.println("Hello world");
                }).start();
    }
}
