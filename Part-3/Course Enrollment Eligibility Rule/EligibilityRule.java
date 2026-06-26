class EnrollmentDeniedException extends Exception {

    public EnrollmentDeniedException(String message) {
        super(message);
    }
}

@FunctionalInterface
interface EligibilityRule {

    boolean isEligible(
            String studentId,
            String courseId)
            throws EnrollmentDeniedException;
}