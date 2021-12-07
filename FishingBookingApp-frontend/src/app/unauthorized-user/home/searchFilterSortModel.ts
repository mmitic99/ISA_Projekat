export class SearchFilterSortModel {
    constructor(
        public sort: string,
        public types = new Array<string>(),
        public search = "",
    ) { }
}