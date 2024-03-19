export interface IIsCalculateTemp {
  id: number;
  serialNo?: number | null;
  financialYear?: string | null;
  issFileParserId?: number | null;
  branchCode?: string | null;
  pacsNumber?: string | null;
  loanAccountNumberKcc?: string | null;
  farmerName?: string | null;
  gender?: string | null;
  aadharNumber?: string | null;
  mobileNo?: string | null;
  farmerType?: string | null;
  socialCategory?: string | null;
  accountNumber?: string | null;
  loanSanctionDate?: string | null;
  loanSanctionAmount?: string | null;
  disbursementDate?: string | null;
  disburseAmount?: string | null;
  maturityLoanDate?: string | null;
  bankDate?: string | null;
  cropName?: string | null;
  recoveryAmount?: string | null;
  recoveryInterest?: string | null;
  recoveryDate?: string | null;
  balanceAmount?: string | null;
  prevDays?: number | null;
  presDays?: number | null;
  actualDays?: number | null;
  nProd?: number | null;
  productAmount?: string | null;
  productBank?: string | null;
  productAbh3Lakh?: string | null;
  interestFirst15?: number | null;
  interestFirst25?: number | null;
  interestSecond15?: number | null;
  interestSecond25?: number | null;
  interestStateFirst3?: number | null;
  interestStateSecond3?: number | null;
  interestFirstAbh3?: number | null;
  interestSecondAbh3?: number | null;
  interestAbove3Lakh?: number | null;
  panjabraoInt3?: number | null;
  isRecover?: number | null;
  abh3LakhAmt?: number | null;
  upto50000?: number | null;
}

export type NewIsCalculateTemp = Omit<IIsCalculateTemp, 'id'> & { id: null };
