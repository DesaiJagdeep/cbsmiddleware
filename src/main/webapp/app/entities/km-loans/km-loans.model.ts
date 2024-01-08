import { BaseEntity } from './../../shared';

export class KmLoans implements BaseEntity {
    constructor(
        public id?: number,
        public cropName?: string,
        public cropNameMr?: string,
        public loanDate?: any,
        public loanAmount?: number,
        public loanAmountMr?: string,
        public are?: number,
        public areMr?: string,
        public receivableAmt?: number,
        public receivableAmtMr?: string,
        public dueAmt?: number,
        public dueAmtMr?: string,
        public dueDate?: any,
        public kmDetails?: BaseEntity,
    ) {
    }
}
