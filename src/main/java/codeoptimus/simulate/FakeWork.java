package codeoptimus.simulate;

/**
 * Created with IntelliJ IDEA.
 * User: Aaron Allred
 */

public class FakeWork {
    private static final Integer addNum = 1000;

    public static Long fakeWork(Integer baseNum) {
        try {
            Long fakeTime = Math.round(Math.random() * baseNum) + addNum;
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
