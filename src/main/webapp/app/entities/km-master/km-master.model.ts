import { BaseEntity } from './../../shared';

export class KmMaster implements BaseEntity {
    constructor(
        public id?: number,
        public branchCode?: string,
        public farmerName?: string,
        public farmerNameMr?: string,
        public farmerAddress?: string,
        public farmerAddressMr?: string,
        public gender?: string,
        public caste?: string,
        public casteMr?: string,
        public pacsNumber?: string,
        public areaHect?: string,
        public aadharNo?: number,
        public aadharNoMr?: string,
        public panNo?: number,
        public panNoMr?: string,
        public mobileNo?: string,
        public mobileNoMr?: string,
        public kccNo?: string,
        public kccNoMr?: string,
        public savingNo?: string,
        public savingNoMr?: string,
        public entryFlag?: string,
    ) {
    }
}
