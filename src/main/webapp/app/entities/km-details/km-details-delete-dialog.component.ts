import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KmDetails } from './km-details.model';
import { KmDetailsPopupService } from './km-details-popup.service';
import { KmDetailsService } from './km-details.service';

@Component({
    selector: 'jhi-km-details-delete-dialog',
    templateUrl: './km-details-delete-dialog.component.html'
})
export class KmDetailsDeleteDialogComponent {

    kmDetails: KmDetails;

    constructor(
        private kmDetailsService: KmDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kmDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'kmDetailsListModification',
                content: 'Deleted an kmDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-km-details-delete-popup',
    template: ''
})
export class KmDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private kmDetailsPopupService: KmDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.kmDetailsPopupService
                .open(KmDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
