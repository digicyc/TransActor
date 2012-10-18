package codeoptimus;

/**
 * Created with IntelliJ IDEA.
 * User: Aaron Allred
 */

public class FakeWork {
    public static Long fakeWork(Integer baseNum) {
        try {
            Long fakeTime = Math.round(Math.random() * baseNum) + 1000;
            Thread.sleep(fakeTime);

            return fakeTime;
        } catch (Exception e) {
        }
        return 0L;
    }

    public static Long fakeWork() {
        return fakeWork(4000);
    }
}
