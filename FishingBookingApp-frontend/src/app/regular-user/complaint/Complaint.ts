export class Complaint {
    constructor(
        public entityIdString = "",
        public explain = "",
        public mailAddress: any,
        public entityId: number,
    ) { }
}