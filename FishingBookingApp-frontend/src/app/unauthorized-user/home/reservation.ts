export class Reservation {
    constructor(
        public date: string = "",
        public time: string = "",
        public days: number = 0,
        public guests: number = 0,
        public reservationEntitiesId: number = -1,
        public mailAddress: any = null,
        public additionalServicesId = new Array<number>(),
    ) { }
}