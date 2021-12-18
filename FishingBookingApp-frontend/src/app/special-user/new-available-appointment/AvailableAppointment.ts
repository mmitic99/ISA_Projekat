export class AvailableAppointment {
    constructor(
        public id: any,
        public entityId: string = "",
        public startDateString: string = "",
        public startTimeString: string = "",
        public endDateString: string = "",
        public endTimeString: string = ""
    ) { }
}