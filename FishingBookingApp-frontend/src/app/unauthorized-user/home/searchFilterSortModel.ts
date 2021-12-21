export class SearchFilterSortModel {
    constructor(
        public sort: string,
        public date: string = "",
        public time: string = "",
        public daysNumber: number = 0,
        public guestsNumber: number = 0,
        public types = new Array<string>(),
        public search = "",
        public mailAddress = "",
        public isOldReservation = true,
    ) { }
}