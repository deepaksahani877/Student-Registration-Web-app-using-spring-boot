package com.st.studentregistration.models.constants;

public interface UserCredentialFlags{
    int WRONG_PASSWORD = -1;
    int USER_NOT_EXISTS = -2;
    int USER_CREDENTIAL_MATCHED = 0;
    int UNEXPECTED_ERROR = -3;
    int EMAIL_ALREADY_REGISTERED = -4;
}
