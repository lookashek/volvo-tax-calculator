package com.volvo.taxcalculator.exception;

import org.springframework.http.HttpStatus;

public class CustomProblemException extends RuntimeException {

    private final Problem problem;

    public CustomProblemException(Problem problem, String msg) {
        super(msg);
        this.problem = problem;
    }

    public Problem getProblem() {
        return problem;
    }

    public enum Problem {
        YEAR_NOT_FOUND("bad_request.year_not_found", HttpStatus.BAD_REQUEST);

        private final String key;
        private final HttpStatus status;

        Problem(String key, HttpStatus status) {
            this.key = key;
            this.status = status;
        }

        public String getKey() {
            return key;
        }

        public HttpStatus getStatus() {
            return status;
        }

    }
}
