import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KmCrops } from './km-crops.model';
import { KmCropsPopupService } from './km-crops-popup.service';
import { KmCropsService } from './km-crops.service';

@Component({
    selector: 'jhi-km-crops-delete-dialog',
    templateUrl: './km-crops-delete-dialog.component.html'
})
export class KmCropsDeleteDialogComponent {

    kmCrops: KmCrops;

    constructor(
        private kmCropsService: KmCropsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kmCropsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kmCropsListModification',
                content: 'Deleted an kmCrops'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-km-crops-delete-popup',
    template: ''
})
export class KmCropsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kmCropsPopupService: KmCropsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kmCropsPopupService
                .open(KmCropsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
