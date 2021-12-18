export class CancelReservation {
    constructor(
        public reservationId: number,
        public mailAddress: string = "",
    ) { }
}