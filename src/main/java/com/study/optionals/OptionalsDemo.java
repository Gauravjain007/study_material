package com.study.optionals;

import java.util.Optional;

public class OptionalsDemo {

    // User Class
    static class User {
        private String name;
        private String email;

        User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        String getName() {
            return name;
        }

        String getEmail() {
            return email;
        }
    }

    /**
     * This method simulates a database lookup by returning an Optional containing a
     * User object if user's unique identifier is present.. If the ID is not found,
     * an empty Optional is returned.
     *
     * @param id The unique identifier of the user.
     * @return An Optional containing the User object if found, otherwise an empty
     *         Optional.
     */
    private static Optional<User> findUserById(Long id) {
        // Simulate database lookup
        if (id == 123L) {
            return Optional.of(new User("Gaurav Jain", "gaurav.jain@example.com"));
        } else if (id == 246L) {
            return Optional.of(new User("John Doe", null));
        }
        return Optional.empty();
    }

    /**
     * Simulates sending a welcome email to the given user.
     * 
     * @param user the user to send the email to
     */
    private static void sendWelcomeEmail(User user) {
        System.out.println("Sending welcome email to " + user.getEmail());
    }

    /**
     * Process user information safely. If the user is present, it will
     * print the display name (or "Anonymous" if the name is empty),
     * email domain (or "unknown" if the email is empty or does not
     * contain "@"), and send a welcome email if the email is present. If the user
     * is not present, it will print "No user found".
     * 
     * @param user The Optional user to process.
     */
    public static void processUser(Optional<User> user) {
        // Process user information safely
        String displayName = user
                .map(User::getName)
                .filter(name -> !name.isEmpty())
                .orElse("Anonymous");

        System.out.println("Display name: " + displayName);

        // Get email domain or default
        String emailDomain = user
                .map(User::getEmail)
                .filter(email -> email.contains("@"))
                .map(email -> email.substring(email.indexOf("@") + 1))
                .orElse("unknown");

        System.out.println("Email domain: " + emailDomain);

        // Conditional execution
        user.filter(u -> u.getEmail() != null).ifPresent(u -> sendWelcomeEmail(u));

        // Java 9+ - or method
        Optional<User> backupUser = findUserById(456L);
        Optional<User> effectiveUser = user.or(() -> backupUser); // Try primary, then backup

        // Java 9+ - ifPresentOrElse method
        effectiveUser.ifPresentOrElse(
                u -> System.out.println("User found: " + u.getName()),
                () -> System.out.println("No user found"));
    }

    public static void main(String[] args) {
        // Simulate a database lookup
        Optional<User> user = findUserById(123L); // Non-empty Optional
        Optional<User> user2 = findUserById(101L); // Empty Optional
        Optional<User> user3 = findUserById(246L); // User with null email

        // Process user information
        System.out.println("\nProcessing User 1:");
        processUser(user);

        System.out.println("\nProcessing User 2:");
        processUser(user2);

        System.out.println("\nProcessing User 3:");
        processUser(user3);

    }

}
