export class ChangePassword {
    constructor(
        public mailAddress : any,
        public oldPassword = "",
        public newPassword1 = "",
        public newPassword2 = "",
    ) { }
}