package io.sunshower.sdk.v1.core.security.builders;


import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;

/**
 * Created by haswell on 5/8/17.
 */
public class RegistrationBuilder {
    public static class UsernameStep {
        private final String username;


        public UsernameStep(String username) {
            this.username = username;
        }


        public EmailAddressStep withEmailAddress(String s) {
            return new EmailAddressStep(username, s);
        }
    }


    public static class EmailAddressStep {
        private final String username;
        private final String emailaddress;

        public EmailAddressStep(String username, String s) {
            this.emailaddress = s;
            this.username = username;
        }

        public PasswordStep withPassword(String password) {
            return new PasswordStep(username, emailaddress, password);
        }
    }


    public static class PasswordStep {
        final String username;
        final String password;
        final String emailAddress;

        public PasswordStep(String username, String email, String password) {
            this.username = username;
            this.password = password;
            this.emailAddress = email;
        }


        public FirstnameStep withFirstName(String fn) {
            return new FirstnameStep(username, emailAddress, password, fn);

        }
    }

    public static class FirstnameStep {
        private final String username;
        private final String password;
        private final String emailAddress;
        private final String firstName;


        public FirstnameStep(String username, final String email, String password, String firstName) {
            this.username = username;
            this.password = password;
            this.emailAddress = email;
            this.firstName = firstName;
        }

        public LastnameStep withLastName(String lastName) {
            return new LastnameStep(username, emailAddress, password, firstName, lastName);
        }
    }

    public static class LastnameStep {

        private final String email;
        private final String username;
        private final String password;
        private final String firstName;
        private final String lastName;


        public LastnameStep(String username, String email, String password, String firstName, String lastName) {
            this.email = email;
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public RegistrationRequestElement withPhoneNumber(final String phone) {
            final RegistrationRequestElement element = new RegistrationRequestElement();
            element.setUsername(username);
            element.setPassword(password);
            element.setEmailAddress(email);
            element.setFirstName(firstName);
            element.setLastName(lastName);
            element.setPhoneNumber(phone);
            return element;
        }
    }


}
