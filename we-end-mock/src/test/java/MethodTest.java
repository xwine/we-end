import com.github.xwine.end.mock.MethodMock;

public class MethodTest {
    public Mantou fetch() {
        return new Mantou();
    }
    public static void main(String[] args) {
        System.out.println(MethodMock.getObject(MethodTest.class.getName(),"fetch"));
    }
}
