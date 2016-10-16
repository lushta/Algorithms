package interview.questions.queues;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by alla.hranatova on 10/15/2016.
 * <p>
 * Java generics.
 * Explain why Java prohibits generic array creation.
 */
public class JavaGenerics {

    /**
     * Arrays (unlike generics) contain  information about its component type at runtime.
     * Each array in Java stores the component type (i.e. T.class) inside it,
     * therefore you need the class of T at runtime to create such an array.
     * So you must know the component type when you create the array.
     * Since you don't know what T is at runtime, you can't create the array.
     * <p>
     * For Generic containers the type parameter is an illusion and it is not possible to tell
     * at runtime whether a list is a list of String or list of Integer.
     */
    public static void main(String[] args) {
        String[] stringArray = new String[]{};
        Integer[] intArray = new Integer[]{};
        assertNotEquals(stringArray.getClass(), intArray.getClass());

        List<String> stringList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        assertEquals(stringList.getClass(), intList.getClass());
    }
}
