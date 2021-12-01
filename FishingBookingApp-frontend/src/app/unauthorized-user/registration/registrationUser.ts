export class RegistrationUser {
    constructor(
        public mailAddress: string,
        public password1: string,
        public password2: string,
        public name: string,
        public surname: string,
        public mobileNumber: string,
        public street: string,
        public number: string,
        public city: string,
        public postalCode: string,
        public country: string,
    ) { }
}