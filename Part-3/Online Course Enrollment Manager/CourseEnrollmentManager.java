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

public class CourseEnrollmentManager {

    public static void enrollStudent(
            String studentId,
            String courseId,
            EligibilityRule rule) {

        System.out.println(
                "Attempting to enroll "
                        + studentId
                        + " in "
                        + courseId
                        + "...");

        try {

            if (rule.isEligible(studentId, courseId)) {
                System.out.println(
                        "Enrollment successful for "
                                + studentId
                                + " in "
                                + courseId
                                + "! Happy learning!");
            }
            else {
                System.out.println(
                        studentId
                                + " is not eligible for "
                                + courseId + ".");
            }

        }
        catch (EnrollmentDeniedException e) {
            System.out.println(
                    "Enrollment failed for "
                            + studentId
                            + ": "
                            + e.getMessage());
        }

        System.out.println();
    }

    public static void main(String[] args) {

        EligibilityRule rule =
                (studentId, courseId) -> {

                    if (studentId.equals("SKILL999")) {
                        throw new EnrollmentDeniedException(
                                "Student account suspended due to outstanding fees, Roshan!");
                    }

                    if (courseId.equals("JAVA101")
                            && !studentId.startsWith("SKILL")) {

                        throw new EnrollmentDeniedException(
                                "Invalid student ID format. Please use 'SKILL' prefix, Anisha!");
                    }

                    if (studentId.startsWith("SKILL")
                            && courseId.equals("JAVA101")) {

                        return true;
                    }

                    return false;
                };

        enrollStudent(
                "SKILL123",
                "JAVA101",
                rule);

        enrollStudent(
                "SKILL999",
                "JAVA101",
                rule);

        enrollStudent(
                "STUDENT001",
                "JAVA101",
                rule);

        enrollStudent(
                "SKILL123",
                "PYTHON202",
                rule);
    }
}