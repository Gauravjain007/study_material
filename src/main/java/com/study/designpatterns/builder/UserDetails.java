package com.study.designpatterns.builder;

public class UserDetails {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final String occupation;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getOccupation() {
        return occupation;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }

    private UserDetails(UserDetailsBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
        this.occupation = builder.occupation;
    }

    public static class UserDetailsBuilder {
        private String firstName;
        private String lastName;
        private int age;
        private String email;
        private String phoneNumber;
        private String address;
        private String occupation;

        private UserDetailsBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public static UserDetailsBuilder addUser(String firstName, String lastName) {
            if (firstName == null || firstName.isEmpty()) {
                throw new IllegalArgumentException("First name cannot be null or empty");
            }
            if (lastName == null || lastName.isEmpty()) {
                throw new IllegalArgumentException("Last name cannot be null or empty");
            }
            return new UserDetailsBuilder(firstName, lastName);
        }

        public UserDetailsBuilder age(int age) {
            if (age < 0 || age > 120) {
                throw new IllegalArgumentException("Age must be between 0 and 120");
            }
            this.age = age;
            return this;
        }

        public UserDetailsBuilder email(String email) {
            if (email != null && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            this.email = email;
            return this;
        }

        public UserDetailsBuilder phoneNumber(String phoneNumber) {
            if (phoneNumber != null && !phoneNumber.matches("\\+?[\\d-\\s]*")) {
                throw new IllegalArgumentException("Invalid phone number format");
            }
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserDetailsBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserDetailsBuilder occupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(this);
        }
    }
}
