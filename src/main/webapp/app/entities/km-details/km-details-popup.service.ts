import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { KmDetails } from './km-details.model';
import { KmDetailsService } from './km-details.service';

@Injectable()
export class KmDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private kmDetailsService: KmDetailsService

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
                this.kmDetailsService.find(id).subscribe((kmDetails) => {
                    kmDetails.dueDate = this.datePipe
                        .transform(kmDetails.dueDate, 'yyyy-MM-ddTHH:mm:ss');
                    kmDetails.kmDate = this.datePipe
                        .transform(kmDetails.kmDate, 'yyyy-MM-ddTHH:mm:ss');
                    kmDetails.kmFromDate = this.datePipe
                        .transform(kmDetails.kmFromDate, 'yyyy-MM-ddTHH:mm:ss');
                    kmDetails.kmToDate = this.datePipe
                        .transform(kmDetails.kmToDate, 'yyyy-MM-ddTHH:mm:ss');
                    kmDetails.eAgreementDate = this.datePipe
                        .transform(kmDetails.eAgreementDate, 'yyyy-MM-ddTHH:mm:ss');
                    kmDetails.bojaDate = this.datePipe
                        .transform(kmDetails.bojaDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.kmDetailsModalRef(component, kmDetails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.kmDetailsModalRef(component, new KmDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    kmDetailsModalRef(component: Component, kmDetails: KmDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.kmDetails = kmDetails;
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
