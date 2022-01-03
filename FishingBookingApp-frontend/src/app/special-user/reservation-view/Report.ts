export class Report {
    constructor(
        public reservationId: string = "",
        public description: string = "",
        public type: string = "good",
        public customerAppeared: boolean = true,
        public requestForPenalty: boolean = false
    ) { }
}