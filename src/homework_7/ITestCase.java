package homework_7;

/**
 * An interface of methods to define the test cases used for SortedStorage.
 *
 *  @author Andrew Serra
 *  @author Anindhya Kushagra
 */
public interface ITestCase {
    boolean isSuccessful() throws Exception;
    void run() throws Exception;
}
