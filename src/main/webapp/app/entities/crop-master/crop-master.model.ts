import { BaseEntity } from './../../shared';

export class CropMaster implements BaseEntity {
    constructor(
        public id?: number,
        public cropCode?: string,
        public cropName?: string,
        public categoryCode?: string,
        public categoryName?: string,
        public vatapFromDay?: any,
        public vatapToMonth?: any,
        public lastToDay?: any,
        public lastToMonth?: any,
        public dueDay?: any,
        public dueMonth?: any,
        public cropPeriod?: string,
        public sanctionAmt?: number,
        public previousAmt?: number,
    ) {
    }
}
