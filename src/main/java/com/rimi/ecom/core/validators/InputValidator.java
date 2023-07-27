package com.rimi.ecom.core.validators;

import com.rimi.ecom.core.request.CalculatorRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
@Service
public class InputValidator {
    public List<CoreError> validate(CalculatorRequest request) {
        List<CoreError> errors = new ArrayList<>();

        if (request == null || request.getInput() == null) {
            errors.add(new CoreError("Input", "Received input is null"));
            return errors;
        }
        isStringEmpty(request).ifPresent(errors::add);
        isStringContainOnlyNumbersAndMathOperators(request).ifPresent(errors::add);
        isContainsOnlyMathOperators(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> isStringEmpty(CalculatorRequest request) {
        return (request.getInput().isEmpty())
                ? Optional.of(new CoreError("Input", "Received input is Empty"))
                : Optional.empty();
    }
    private Optional<CoreError> isStringContainOnlyNumbersAndMathOperators(CalculatorRequest request) {
        // Define the regular expression pattern to match only numbers and math operands ( (, ), +, -, *, /)
        String regex = "^[0-9+\\-*/()]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getInput());
        return matcher.matches()
                ? Optional.empty()
                : Optional.of(new CoreError("Input", "Received input contain wrong symbols"));
    }
    private Optional<CoreError> isContainsOnlyMathOperators(CalculatorRequest request) {
        String regex = "^[+\\-*/()]+$"; // Define the regular expression pattern to math operands ( (, ), +, -, *, /)
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getInput());
        return matcher.matches()
                ? Optional.of(new CoreError("Input", "Received input contains only math operators"))
                : Optional.empty();
    }
}
