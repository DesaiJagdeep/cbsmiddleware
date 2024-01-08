import { BaseEntity } from './../../shared';

export class KmCrops implements BaseEntity {
    constructor(
        public id?: number,
        public cropName?: string,
        public cropNameMr?: string,
        public hector?: number,
        public hectorMr?: string,
        public are?: number,
        public areMr?: string,
        public prviousAmt?: number,
        public previousAmtMr?: string,
        public demand?: number,
        public demandMr?: string,
        public society?: string,
        public societyMr?: string,
        public bankAmt?: number,
        public bankAmtMr?: string,
        public noOfTree?: number,
        public noOfTreeMr?: string,
        public kmDetails?: BaseEntity,
    ) {
    }
}
