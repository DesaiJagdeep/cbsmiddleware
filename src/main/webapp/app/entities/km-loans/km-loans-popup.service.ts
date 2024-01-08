import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { KmLoans } from './km-loans.model';
import { KmLoansService } from './km-loans.service';

@Injectable()
export class KmLoansPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private kmLoansService: KmLoansService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.kmLoansService.find(id).subscribe((kmLoans) => {
                    kmLoans.loanDate = this.datePipe
                        .transform(kmLoans.loanDate, 'yyyy-MM-ddTHH:mm:ss');
                    kmLoans.dueDate = this.datePipe
                        .transform(kmLoans.dueDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.kmLoansModalRef(component, kmLoans);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.kmLoansModalRef(component, new KmLoans());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    kmLoansModalRef(component: Component, kmLoans: KmLoans): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.kmLoans = kmLoans;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
