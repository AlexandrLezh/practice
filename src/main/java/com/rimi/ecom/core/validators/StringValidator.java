package com.rimi.ecom.core.validators;


import com.rimi.ecom.core.responces.CoreError;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
@Service
public class StringValidator {
    public List<CoreError> validate(String request) {
        List<CoreError> errors = new ArrayList<>();
        isStringNull(request).ifPresent(errors::add);
        isStringContainOnlyNumbersAndMathOperators(request).ifPresent(errors::add);
        return errors;
    }
    private Optional<CoreError> isStringNull(String request) {
        return (request == null)
                ? Optional.of(new CoreError("Input", "Received input is null"))
                : Optional.empty();
    }
    private Optional<CoreError> isStringContainOnlyNumbersAndMathOperators(String request) {
        // Define the regular expression pattern to match only numbers and math operands (+, -, *, /)
        String regex = "^[0-9+\\-*/()]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);
        return matcher.matches()
                ? Optional.empty()
                : Optional.of(new CoreError("Input", "Received input contain wrong symbols"));
    }
}
