import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 *
 */
@Data
public class Mantou {
    private String name;
    private int age;
    private boolean pass;
    private long hireNumber;
    private Date created;
    private Set<String> children;
}
