export class ReservationForUser {
    constructor(
        public ownerMailAddress: any,
        public reservationEntityId: any,
        public userMailAddress: any,
        public startDate: string,
        public startTime: string,
        public days: number,
        public maxPeople: string,
        public additionalServicesId = new Array<number>(),
        public price: string
    ) { }
}