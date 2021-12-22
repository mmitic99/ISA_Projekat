export class SpecialReservation {
    constructor(
        public mailAddress: any,
        public reservationEntityId: any,
        public startDate: string,
        public startTime: string,
        public days: number,
        public maxPeople: string,
        public additionalServicesId = new Array<number>(),
        public price: string,
        public validFromDate: string,
        public validFromTime: string,
        public validToDate: string,
        public validToTime: string
    ) { }
}