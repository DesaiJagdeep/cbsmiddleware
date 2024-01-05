import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KmMaster } from './km-master.model';
import { KmMasterPopupService } from './km-master-popup.service';
import { KmMasterService } from './km-master.service';

@Component({
    selector: 'jhi-km-master-delete-dialog',
    templateUrl: './km-master-delete-dialog.component.html'
})
export class KmMasterDeleteDialogComponent {

    kmMaster: KmMaster;

    constructor(
        private kmMasterService: KmMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kmMasterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kmMasterListModification',
                content: 'Deleted an kmMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-km-master-delete-popup',
    template: ''
})
export class KmMasterDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kmMasterPopupService: KmMasterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kmMasterPopupService
                .open(KmMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
