
package OTP;

import java.util.Random;

public class FakeOtpGenerator implements IOtpGenerator {
    public FakeOtpGenerator() {
    }

    public int generateOtp() {
        return (new Random()).nextInt(9000) + 1000;
    }
}
