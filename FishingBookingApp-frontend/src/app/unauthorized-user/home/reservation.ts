export class Reservation {
    constructor(
        public date: string = "",
        public time: string = "",
        public daysNumber: number = 0,
        public guestsNumber: number = 0,
        public reservationEntitiesId: number = -1,
        public mailAddress: any = null
    ) { }
}