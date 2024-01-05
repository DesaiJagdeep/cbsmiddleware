import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CropMaster } from './crop-master.model';
import { CropMasterService } from './crop-master.service';

@Injectable()
export class CropMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private cropMasterService: CropMasterService

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
                this.cropMasterService.find(id).subscribe((cropMaster) => {
                    cropMaster.vatapFromDay = this.datePipe
                        .transform(cropMaster.vatapFromDay, 'yyyy-MM-ddTHH:mm:ss');
                    cropMaster.vatapToMonth = this.datePipe
                        .transform(cropMaster.vatapToMonth, 'yyyy-MM-ddTHH:mm:ss');
                    cropMaster.lastToDay = this.datePipe
                        .transform(cropMaster.lastToDay, 'yyyy-MM-ddTHH:mm:ss');
                    cropMaster.lastToMonth = this.datePipe
                        .transform(cropMaster.lastToMonth, 'yyyy-MM-ddTHH:mm:ss');
                    cropMaster.dueDay = this.datePipe
                        .transform(cropMaster.dueDay, 'yyyy-MM-ddTHH:mm:ss');
                    cropMaster.dueMonth = this.datePipe
                        .transform(cropMaster.dueMonth, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.cropMasterModalRef(component, cropMaster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.cropMasterModalRef(component, new CropMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    cropMasterModalRef(component: Component, cropMaster: CropMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cropMaster = cropMaster;
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
