import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CropMaster } from './crop-master.model';
import { CropMasterPopupService } from './crop-master-popup.service';
import { CropMasterService } from './crop-master.service';

@Component({
    selector: 'jhi-crop-master-delete-dialog',
    templateUrl: './crop-master-delete-dialog.component.html'
})
export class CropMasterDeleteDialogComponent {

    cropMaster: CropMaster;

    constructor(
        private cropMasterService: CropMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cropMasterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cropMasterListModification',
                content: 'Deleted an cropMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-crop-master-delete-popup',
    template: ''
})
export class CropMasterDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cropMasterPopupService: CropMasterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cropMasterPopupService
                .open(CropMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
