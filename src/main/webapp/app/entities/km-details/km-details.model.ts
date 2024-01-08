import { BaseEntity } from './../../shared';

export class KmDetails implements BaseEntity {
    constructor(
        public id?: number,
        public shares?: number,
        public sharesMr?: string,
        public sugarShares?: number,
        public sugarSharesMr?: string,
        public deposit?: number,
        public depositMr?: string,
        public dueLoan?: number,
        public dueLoanMr?: string,
        public dueAmount?: number,
        public dueAmountMr?: string,
        public dueDate?: any,
        public kmDate?: any,
        public kmFromDate?: any,
        public kmToDate?: any,
        public bagayatHector?: number,
        public bagayatHectorMr?: string,
        public bagayatAre?: number,
        public bagayatAreMr?: string,
        public jirayatHector?: number,
        public jirayatHectorMr?: string,
        public jirayatAre?: number,
        public jirayatAreMr?: string,
        public landValue?: number,
        public landValueMr?: string,
        public eAggrementAmt?: number,
        public eAgreementAmt?: string,
        public eAgreementDate?: any,
        public bojaAmount?: number,
        public bojaAmountMr?: string,
        public bojaDate?: any,
        public kmMaster?: BaseEntity,
    ) {
    }
}
