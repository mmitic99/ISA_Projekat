export class ReservationEntity {
    constructor(
        public id: string,
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
        public userId: any,
        public username: any,
        public addressId: any
    ) { }
}