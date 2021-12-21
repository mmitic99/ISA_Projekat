export class Review {
    constructor(
        public reservationId: number = -1,
        public mailAddress: any = null,
        public explain: string = "",
        public mark: number = 0
    ) { }
}