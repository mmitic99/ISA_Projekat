export class Cottage {
    constructor(
        public id: any,
        public name: string,
        public numberOfRooms: string,
        public bedsPerRoom: string,
        public price: string,
        public promotionalDescription: string,
        public rulesOfConduct: string,
        public street: string,
        public number: string,
        public city: string,
        public postalCode: string,
        public country: string,
        public longitude: number,
        public latitude: number,
        public cottageOwnerId: string,
        public cottageOwnerUsername: string,
        public addressId: any
    ) { }
}