package Authentication;

import OTP.FakeOtpSender;
import OTP.IOtpSender;

public class ValidationService {
    private IMobileNumberValidator mobileNumberValidator;
    private IOtpSender otpSender = new FakeOtpSender();
    public boolean validatedMobileNumber;
    public boolean confirmedOtp;

    public ValidationService(IMobileNumberValidator mobileNumberValidator) {
        this.mobileNumberValidator = mobileNumberValidator;
    }

    public boolean ConfirmOtp(int generatedOtp, int otp) {
        boolean equal = generatedOtp == otp;
        if (equal) {
            this.confirmedOtp = true;
        }
        return equal;
    }

    public boolean ValidateMobileNumber(String mobileNumber) {
        boolean valid = this.mobileNumberValidator.ValidateMobileNumber(mobileNumber);
        if (valid) {
            this.validatedMobileNumber = true;
        }

        return valid;
    }

    public int SendOtp(String mobileNumber) {
        return this.otpSender.sendOtp(mobileNumber);
    }
}
