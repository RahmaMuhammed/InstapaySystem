

package OTP;

public class FakeOtpSender implements IOtpSender {
    private IOtpGenerator otpGenerator = new FakeOtpGenerator();

    public FakeOtpSender() {
    }

    public int sendOtp(String mobileNumber) {
        int generatedOtp = this.otpGenerator.generateOtp();
        return generatedOtp;
    }
}
